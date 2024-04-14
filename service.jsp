<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.Set, java.util.Map, java.util.TreeSet" %>
<%@ page import="java.io.PrintWriter, java.io.IOException" %>
<%@ page import="java.util.Optional" %>
<%@ page import="myjdbc.MyNotesForWeb, myjdbc.Note" %>
<%@ page import="modes.Modes" %>

<% 
    final int SUBSTRLIMIT = 69;
  
    final String TEMPLATEFMT = "<p>Вы не %%s %%%s%s.<br>Пожалуйста, %%s %%%s%s.</p>"; 
    final String NOTEFMT = TEMPLATEFMT.fotmatted("заметки", "заметки"); 
    final String UNSELECTEDSUBJFMT = NOTEFMT.formatted("выбрали", "выберите");
    final String ERRCREATEFMT = NOTEFMT.formatted("задали", "задайте");
    final String FRAGMENTFMT = TEMPLATEFMT.formatted("")
                                          .formatted("выбрали", "выберите");
    
    final String CHECKBOXFMT = "<input type=\"checkbox\" name=\"%s\" %s> %s<br>";
    final String RECORD_FORMAT = 
            "<h3><b>Тема:</b> <i><u>%s</u></i> <b>| Заметка:</b></h3><div>%s</div><hr>";
    final String OPTFMT = "<option value=\"%s\">%s</option>";
    final String RADIOFMT = 
            "<h3><input type=\"radio\" name=\"selectedNote\" value=\"%d\" %s><br>Фрагмент:</h3>" +
            "<code><b>%s</b></code> .......<hr>";
    final String HIDDENSUBJFMT = "<input type=\"hidden\" name=\"subject\" value=\"%s\">";
    final String SELECTEDSUBJFMT = "<h2><b>Выбранная тема: </b><i><u>%s</u></i></h2>";
    final String HIDDENSELECTEDNOTEFMT = "<input type=\"hidden\" name=\"selectedNote\" value=\"%s\">";
    final String SELECTEDFRAGMENTFMT = "<h2><b>Выбранный фрагмент:</b><br><i><u>%s</u></i> .......</h2>";
    
    
    class Service {
               
        private String MODE = request.getParameter("mode");
        private String SUBJECT = request.getParameter("subject");
        private String CREATED_NOTE = request.getParameter("createdNote");
        private String SELECTED_NOTE = request.getParameter("selectedNote");
        private String EDITED_NOTE = request.getParameter("editedNote");
        private String DELETED_NOTE = SELECTED_NOTE;
        
        private Map<String, String[]> PARAMS = request.getParameterMap(); 
        private Set<String> AVAILABLE_SUBJECTS = MyNotesForWeb.getSubjectSet(); 
        private Set<String> SELECTED_SUBJECTS = getSelectedSubjects();
        
        
        private Modes mode;
        private boolean isUnselectedSubject = false;
        private boolean isUnselectedNote = false;
        private PrintWriter out = response.getWriter();
        
        
        public Service() throws IOException { 
            switch (MODE != null ? MODE : "") {
                case Modes.Consts.SUBJECT:
                    this.mode = Modes.SUBJECT;
                    break;
                case Modes.Consts.NOTE:
                    this.mode = Modes.NOTE;
                    break;
                case Modes.Consts.EDIT:
                    this.mode = Modes.EDIT;
                    break;
                case Modes.Consts.DELETE:
                    this.mode = Modes.DELETE;
                    break;
                case Modes.Consts.CREATE:
                    this.mode = Modes.CREATE;
                    break;
                case Modes.Consts.BYDEFAULT:
                    this.mode = Modes.BYDEFAULT;
                    break;
            }
        }
        
        public String getMode() { return MODE; }
        public String getSubject() { return SUBJECT; }
        public String getSelectedNote() { return SELECTED_NOTE; }
        public String getEditedNote() { return EDITED_NOTE; }
        public String getDeletedNote() { return DELETED_NOTE; }
        
        
        public String showCheckboxesWithSubjects() {
            StringBuilder sb = new StringBuilder();
            
            for (String subject : AVAILABLE_SUBJECTS) {   
                String checked = SELECTED_SUBJECTS.contains(subject) ? "checked" : "";
                sb.append(CHECKBOXFMT.formatted(subject, checked, subject));
            }
            
            return sb.toString();
        }
        
        
        public String showNotesBySelectedSubjects() {
            StringBuilder sb = new StringBuilder();
        
            if (Modes.SUBJECT.equals(mode) && SELECTED_SUBJECTS.isEmpty()) {
                sb.append(UNSELECTEDSUBJFMT.formatted("тему", "тему"));
                //isUnselectedSubject = true;
            } else {
               sb.append("<h2>Заметки по выбранным темам:</h2>");
                
                Map<String, Set<Note>> availableRecords = 
                        MyNotesForWeb.getNotesBySelectedSubjects(SELECTED_SUBJECTS);
                        
                for (Map.Entry<String, Set<Note>> entry : availableRecords.entrySet()) {
                    Set<Note> notes = entry.getValue();
                    
                    for (Note note : notes) {
                        sb.append(RECORD_FORMAT.formatted(
                                note.subject(), note.note()));
                    }
                    
                    sb.append("<hr>");
                }   
            }
        
            return sb.toString();
        }
        
        
        public String showDefault() {
            StringBuilder sb = new StringBuilder();
            
            Set<String> availableSubjects = MyNotesForWeb.getSubjectSet();  
            
            for (String subject : availableSubjects) {
                sb.append(OPTFMT.formatted(subject, subject));
            }
            
            return sb.toString();
        }
        
        
        public String showChangeSubject() {
            StringBuilder sb = new StringBuilder();
            
            if (Modes.SUBJECT.equals(mode)) {
                   if (SUBJECT != null) {         
                    sb.append(HIDDENSUBJFMT.formatted(SUBJECT));         
                    sb.append(SELECTEDSUBJFMT.formatted(SUBJECT));
                } else {
                    sb.append(UNSELECTEDSUBJFMT.formatted("тему", "тему"));
                    isUnselectedSubject = true;
                }
            }
            
            return sb.toString();   
        }
        
        
        public String showChangeNote() {
            StringBuilder sb = new StringBuilder();
            
            if (Modes.NOTE.equals(mode)) {
                if (SELECTED_NOTE != null && !isUnselectedNote) {        
                    sb.append(HIDDENSELECTEDNOTEFMT.formatted(SELECTED_NOTE));
                            
                    int ID = -1;
                    try { ID = Integer.parseInt(SELECTED_NOTE); }
                    catch (NumberFormatException ex) {}
                
                    String FULLCONTENT = (ID != -1) ? MyNotesForWeb.getNoteContentById(ID) : ""; 
                    int length = FULLCONTENT.length(); 
                    String FRAGMENT = FULLCONTENT.substring(0, (length >= SUBSTRLIMIT) ? SUBSTRLIMIT : length); 
                                  
                    sb.append(SELECTEDFRAGMENTFMT.formatted(FRAGMENT));
                            
                } else {
                    sb.append(FRAGMENTFMT.formatted("фрагмент", "фрагмент"));
                    isUnselectedNote = true;
                }
              
            } else {
                Map<String, Set<Note>> availableRecords = 
                    MyNotesForWeb.getNotesBySelectedSubjects(SUBJECT != null ? SUBJECT : "");
                        
                sb.append(iterate(availableRecords, RADIOFMT));              
                
                if (Modes.NOTE.equals(mode) || SUBJECT != null) {
                    sb.append("<input type=\"submit\" name=\"mode\" value=\"Выбрать заметку\">");
                }
                
            }   
            
            return sb.toString();   
        }
        
        
        public Optional<String> tryCreateNote() {
            StringBuilder sb = new StringBuilder();
            String result = null;
            
            if (isCreateMode()) {
                if (isValidCreatedNote()) { createNote(); } 
                else { 
                    generateErrorMessages(sb, SUBJECT, CREATED_NOTE); 
                    result = sb.toString();
                }
            }
            
            return Optional.ofNullable(result);
        }
        
        
        public String showUpdateNote() {
            StringBuilder sb = new StringBuilder();
            
            int ID = -1;  
            try { ID = Integer.parseInt(SELECTED_NOTE); } 
            catch (NumberFormatException ex) {}
            final String CONTENT = (ID != -1) ? MyNotesForWeb.getNoteContentById(ID) : "";
            
            if (Modes.EDIT.equals(mode)) {          
                MyNotesForWeb.updateNote(
                        EDITED_NOTE != null ? EDITED_NOTE : "", ID);
            } else {
                if ((!isUnselectedSubject ^ SELECTED_NOTE != null ^ !isUnselectedNote) 
                        && Modes.NOTE.equals(mode)) 
                {
                    sb.append("<textarea name=\"editedNote\" cols=\"92\" rows=\"23\" wrap=\"hard\">");
                    sb.append(CONTENT); 
                    sb.append("</textarea><br><br>");
                    sb.append("<input type=\"submit\" name=\"mode\" value=\"Редактировать заметку\">");
                }
            }
            
            return sb.toString();
        }
        
        
        public String showDeleteNote() {
            StringBuilder sb = new StringBuilder();
            
            int ID = -1;  
            try { ID = Integer.parseInt(SELECTED_NOTE); } 
            catch (NumberFormatException ex) {}
            final String CONTENT = (ID != -1) ? MyNotesForWeb.getNoteContentById(ID) : "";
            
            if (Modes.DELETE.equals(mode)) {          
                MyNotesForWeb.deleteNote(
                        DELETED_NOTE != null ? DELETED_NOTE : "", ID);
            } else {
                if ((!isUnselectedSubject ^ SELECTED_NOTE != null ^ !isUnselectedNote) 
                        && Modes.NOTE.equals(mode)) 
                {
                    sb.append("<div>");
                    sb.append(CONTENT); 
                    sb.append("</div><br><br>");
                    sb.append("<input type=\"submit\" name=\"mode\" value=\"Удалить заметку\">");
                }
            }
            
            return sb.toString();
        }
        
        
        private String iterate(Map<String, Set<Note>> availableRecords, 
                               String RADIOFMT) 
        {
            StringBuilder sb = new StringBuilder();
            int counter = 0;
        
            for (Map.Entry<String, Set<Note>> entry : availableRecords.entrySet()) {
                Set<Note> notes = entry.getValue();
                
                for (Note note : notes) {
                    final String fullNote = note.note();
                    final int length = fullNote.length();
                    
                    String beginNoteFragment = fullNote.substring(0,
                            length >= SUBSTRLIMIT ? SUBSTRLIMIT : length);
                            
                    sb.append(RADIOFMT.formatted(
                            note.id(), 
                            counter++ == 0 ? "checked" : "", 
                            beginNoteFragment));
                }
            }   
            
            return sb.toString();
        }
        
        
        private boolean isCreateMode() { return Modes.CREATE.equals(mode); }
    
    
        private boolean isValidCreatedNote() {
            return isValidCreatedItem(SUBJECT) && isValidCreatedItem(CREATED_NOTE);
        }
        
        
        private boolean isValidCreatedItem(final String content) {
            return ! (content == null || content.isEmpty() || content.isBlank());
        }
        
        
        private void createNote() { MyNotesForWeb.addNote(new Note(SUBJECT, CREATED_NOTE)); }
        
        
        private void generateErrorMessages(final StringBuilder sb, final String... items) {
            for (String item : items) {
                if (! isValidCreatedItem(item)) { 
                    sb.append(getErrorMsg(getItemFormattingWord(item))); 
                }
            }
        }
        
        
        private String getErrorMsg(final String item) { return ERRCREATEFMT.formatted(item, item); }
        
        
        private String getItemFormattingWord(final String item) {
            switch (item) {
                case SUBJECT:
                    return "тему";
                case CREATED_NOTE:
                    return "содержимое";
            }
        }
        
        
        private Set<String> getSelectedSubjects() {
    			      Set<String> result = new TreeSet<>();
    			      
    			      for (Map.Entry<String, String[]> entry : PARAMS.entrySet()) {
        	        String subject = entry.getKey(); 
                String[] wrapper = entry.getValue();
            
                String checkboxStatus = wrapper.length > 0 ? wrapper[0] : "";
                boolean isCheckboxOn = "on".equals(checkboxStatus);
                boolean isValidSubjectName = AVAILABLE_SUBJECTS.contains(subject);
        
                if (isCheckboxOn && isValidSubjectName) { result.add(subject); }
        			  }
    			      
    			      return result;
    			  }
    }
    
    
    final Service SERVICE = new Service();
%>