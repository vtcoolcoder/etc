<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.Set, java.util.Map, java.util.TreeSet" %>
<%@ page import="java.io.PrintWriter, java.io.IOException" %>
<%@ page import="java.util.Optional" %>
<%@ page import="myjdbc.MyNotesForWeb, myjdbc.Note" %>
<%@ page import="modes.Modes" %>

<%
    class IdAndContent {
        private int ID;
        private String CONTENT;
        
        public IdAndContent(int ID, String CONTENT) {
            this.ID = ID;
            this.CONTENT = CONTENT;
        }
        
        public int ID() { return ID; }
        public String CONTENT() { return CONTENT; }
    }
    
    
    class LmbHelper {
        private Note note;
        private String fragment;
        
        public LmbHelper(Note note, String fragment) {
            this.note = note;
            this.fragment = fragment;
        }
        
        public Note note() { return note; }
        public String fragment() { return fragment; }
    }
%>

<% 
    final int SUBSTRLIMIT = 69;
  
    final String TEMPLATEFMT = "\n<p>Вы не %%s %%%%s%s.<br>Пожалуйста, %%s %%%%s%s.</p>\n"; 
    final String NOTEFMT = TEMPLATEFMT.formatted(" заметки", " заметки"); 
    final String UNSELECTEDSUBJFMT = NOTEFMT.formatted("выбрали", "выберите");
    final String ERRCREATEFMT = NOTEFMT.formatted("задали", "задайте");
    final String FRAGMENTFMT = TEMPLATEFMT.formatted("", "")
                                          .formatted("выбрали", "выберите");
    
    final String CHECKBOXFMT = "\t\t<input type=\"checkbox\" name=\"%s\" %s> %s<br>\n";
    final String RECORD_FORMAT = 
            "\n<h3><b>Тема:</b> <i><u>%s</u></i> <b>| Заметка:</b></h3>\n<div>\n%s\n</div><hr>\n";
    final String OPTFMT = "\t<option value=\"%s\">%s</option>\n";
    final String RADIOFMT = 
            "<h3><input type=\"radio\" name=\"selectedNote\" value=\"%d\" %s><br>Фрагмент:</h3>\n" +
            "<div><b>%s</b></div> .......<hr>\n";
    final String HIDDENSUBJFMT = "<input type=\"hidden\" name=\"subject\" value=\"%s\">\n";
    final String SELECTEDSUBJFMT = "<h2><b>Выбранная тема: </b><i><u>%s</u></i></h2>\n";
    final String HIDDENSELECTEDNOTEFMT = "<input type=\"hidden\" name=\"selectedNote\" value=\"%s\">\n";
    final String SELECTEDFRAGMENTFMT = "<h2><b>Выбранный фрагмент:</b><br><i><u>%s</u></i> .......</h2>\n";
%>    

<%    
    class Service {
               
        private String MODE = request.getParameter("mode");
        private String SUBJECT = request.getParameter("subject");
        private String CREATED_NOTE = request.getParameter("createdNote");
        private String SELECTED_NOTE = request.getParameter("selectedNote");
        private String EDITED_NOTE = request.getParameter("editedNote");
        private String DELETED_NOTE = SELECTED_NOTE;
         
        private Modes mode;
        private boolean isUnselectedSubject = false;
        private boolean isUnselectedNote = false;
        private int counter = 0;
        private PrintWriter out = response.getWriter();
        
        
        public Service() throws IOException { 
            initMode();    
        }
        
        public String getMode() { return MODE; }
        public String getSubject() { return SUBJECT; }
        public String getCreatedNote() { return CREATED_NOTE; }
        public String getSelectedNote() { return SELECTED_NOTE; }
        public String getEditedNote() { return EDITED_NOTE; }
        public String getDeletedNote() { return DELETED_NOTE; }
        
        
        public String showCheckboxesWithSubjects() {
            StringBuilder sb = new StringBuilder();
            
            getAvailableSubjects().stream().forEach(subject -> { 
                    String checked = getSelectedSubjects().contains(subject) 
                            ? "checked" : "";
                    sb.append(CHECKBOXFMT.formatted(subject, checked, subject));
            });
            
            
            //
            //for (String subject : getAvailableSubjects()) {   
            //    String checked = getSelectedSubjects().contains(subject) ? "checked" : "";
            //    sb.append(CHECKBOXFMT.formatted(subject, checked, subject));
            //}
            //
            
            
            return sb.toString();
        }
        
        
        public String showNotesBySelectedSubjects() {
            StringBuilder sb = new StringBuilder();
        
            if (Modes.SUBJECT.equals(mode)) {
                if (isFillNotesBySelectedSubjectsError()) {
                    fillNotesBySelectedSubjectsError(sb);
                } else {
                    fillNotesBySelectedSubjects(sb);        
                }
            }
            
            return sb.toString();
        }
        
        
        private boolean isFillNotesBySelectedSubjectsError() {
            return getSelectedSubjects().isEmpty();
        }
        
        
        private void fillNotesBySelectedSubjectsError(StringBuilder sb) {
            sb.append(UNSELECTEDSUBJFMT.formatted("тему", "тему"));
        }
        
        
        private void fillNotesBySelectedSubjects(StringBuilder sb) {
            sb.append("<h2>Заметки по выбранным темам:</h2>");
                    
            Map<String, Set<Note>> availableRecords = 
                MyNotesForWeb.getNotesBySelectedSubjects(getSelectedSubjects());
                
            availableRecords.forEach((key, value) -> {
                    value.stream()
                         .map(el -> RECORD_FORMAT.formatted(
                                            el.subject(),
                                            el.note()))              
                         .forEach(sb::append);
                         
                    sb.append("<hr>");
            });
            
            
            //    
            //        for (Map.Entry<String, Set<Note>> entry : availableRecords.entrySet()) {
            //            Set<Note> notes = entry.getValue();
            //            
            //            for (Note note : notes) {
             //               sb.append(RECORD_FORMAT.formatted(
              //                      note.subject(), note.note()));
              //          }
               //         
                //        sb.append("<hr>");
                 //   }
                  //  */   
        }
        
        
        public String showDefault() {
            StringBuilder sb = new StringBuilder();
            
            Set<String> availableSubjects = MyNotesForWeb.getSubjectSet();  
            
            availableSubjects.stream()
                             .map(el -> OPTFMT.formatted(el, el))
                             .forEach(sb::append);
            
            //
            //for (String subject : availableSubjects) {
            //    sb.append(OPTFMT.formatted(subject, subject));
            //}
            //
            
            return sb.toString();
        }
        
        
        public String showChangeSubject() {
            StringBuilder sb = new StringBuilder();
            
            if (Modes.SUBJECT.equals(mode)) {
                if (isFillChangeSubject()) {         
                    fillChangeSubject(sb);
                } else {
                    fillChangeSubjectError(sb);
                }
            }
            
            return sb.toString();   
        }
        
        
        private boolean isFillChangeSubject() {
            return (SUBJECT != null);
        }
        
        
        private void fillChangeSubject(StringBuilder sb) {
            sb.append(HIDDENSUBJFMT.formatted(SUBJECT));         
            sb.append(SELECTEDSUBJFMT.formatted(SUBJECT));
        }
        
        
        private void fillChangeSubjectError(StringBuilder sb) {
            sb.append(UNSELECTEDSUBJFMT.formatted("тему", "тему"));
            isUnselectedSubject = true;
        }
        
        
        public String showChangeNote() {
            StringBuilder sb = new StringBuilder();
            
            if (Modes.NOTE.equals(mode)) {
                if (isFillChangeNote()) {        
                    fillChangeNote(sb);
                } else {
                    fillChangeNoteError(sb);
                }
              
            } else {
                fillChangeNoteDefault(sb);           
            }   
            
            return sb.toString();   
        }
        
        
        private boolean isFillChangeNote() {
            return (SELECTED_NOTE != null) && !isUnselectedNote;
        }
        
        
        private void fillChangeNote(StringBuilder sb) {
            sb.append(HIDDENSELECTEDNOTEFMT.formatted(SELECTED_NOTE));
                    
            IdAndContent idAndContent = getIdAndContent();
            
            int length = idAndContent.CONTENT().length(); 
            String FRAGMENT = idAndContent.CONTENT()
                                          .substring(0, (length >= SUBSTRLIMIT) ? SUBSTRLIMIT : length); 
                          
            sb.append(SELECTEDFRAGMENTFMT.formatted(FRAGMENT));
        }
        
        
        private void fillChangeNoteError(StringBuilder sb) {
            sb.append(FRAGMENTFMT.formatted("фрагмент", "фрагмент"));
            isUnselectedNote = true;
        }
        
        
        private void fillChangeNoteDefault(StringBuilder sb) {
            Map<String, Set<Note>> availableRecords = 
                MyNotesForWeb.getNotesBySelectedSubjects(
                        (SUBJECT != null) ? SUBJECT : "");
                    
            sb.append(iterate(availableRecords, RADIOFMT));              
            
            if (isAddingSubmitButton()) {
                sb.append("<input type=\"submit\" name=\"mode\" value=\"Выбрать заметку\">");
            }
        }
        
        
        private boolean isAddingSubmitButton() {
            return Modes.NOTE.equals(mode) || (SUBJECT != null);
        }
        
        
        public Optional<String> tryCreateNote() {
            StringBuilder sb = new StringBuilder();
            String result = null;
            
            if (isCreateMode()) {
                if (isValidCreatedNote()) { 
                    createNote(); 
                } else { 
                    generateErrorMessages(sb, "тему", "содержимое"); 
                    result = sb.toString();
                }
            }
            
            return Optional.ofNullable(result);
        }
        
        
        public String showUpdateNote() {
            StringBuilder sb = new StringBuilder();
            
            IdAndContent idAndContent = getIdAndContent();
            
            if (Modes.EDIT.equals(mode)) {          
                MyNotesForWeb.updateNote(
                        (EDITED_NOTE != null) ? EDITED_NOTE : "", 
                        idAndContent.ID());
            } else if (isFillContent()) {
                fillUpdateContent(sb, idAndContent.CONTENT());
            } 
         
            return sb.toString();
        }
        
        
        public String showDeleteNote() {
            StringBuilder sb = new StringBuilder();
            
            IdAndContent idAndContent = getIdAndContent();
            
            if (Modes.DELETE.equals(mode)) { 
                MyNotesForWeb.deleteNote(idAndContent.ID()); 
            } else if (isFillContent()) { 
                fillDeleteContent(sb, idAndContent.CONTENT()); 
            } 
             
            return sb.toString();
        }
        
        
        private IdAndContent getIdAndContent() {
            int ID = -1;  
            
            try { 
                ID = Integer.parseInt(SELECTED_NOTE); 
            } catch (NumberFormatException ex) {}
            
            final String CONTENT = (ID != -1) ? MyNotesForWeb.getNoteContentById(ID) : "";
            
            return new IdAndContent(ID, CONTENT);
        }
        
        
        private boolean isFillContent() {
            return (!isUnselectedSubject 
                    ^ (SELECTED_NOTE != null) 
                    ^ !isUnselectedNote) 
                            && Modes.NOTE.equals(mode);
        }
        
        
        private void fillUpdateContent(StringBuilder sb, String CONTENT) {
            sb.append("<textarea name=\"editedNote\" cols=\"92\" rows=\"23\" wrap=\"hard\">");
            sb.append(CONTENT); 
            sb.append("</textarea><br><br>");
            sb.append("<input type=\"submit\" name=\"mode\" value=\"Редактировать заметку\">");
        }
        
        
        private void fillDeleteContent(StringBuilder sb, String CONTENT) {
            sb.append("<div>");
            sb.append(CONTENT); 
            sb.append("</div><br><br>");
            sb.append("<input type=\"submit\" name=\"mode\" value=\"Удалить заметку\">");
        }
        
        
        private void initMode() {
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
        
        
        private Set<String> getAvailableSubjects() { 
            return MyNotesForWeb.getSubjectSet(); 
        }
        
        
        private String iterate(Map<String, Set<Note>> availableRecords, 
                               String RADIOFMT) 
        {
            StringBuilder sb = new StringBuilder();
            //int counter = 0;
            
            availableRecords.forEach((key, value) -> value.stream()  
                    .map(el -> {
                            String fullNote = el.note();
                            int length = fullNote.length();
                    
                            String beginNoteFragment = 
                                    fullNote.substring(0,
                                            (length >= SUBSTRLIMIT) 
                                                    ? SUBSTRLIMIT 
                                                    : length);
                                                    
                            return new LmbHelper(el, beginNoteFragment);
                    })
                    .map(el -> RADIOFMT.formatted(
                                    el.note().id(), 
                                    (counter++ == 0) ? "checked" : "",
                                    el.fragment()))
                    .forEach(sb::append);                       
            });
        
        ///*
          //  for (Map.Entry<String, Set<Note>> entry : availableRecords.entrySet()) {
            //    Set<Note> notes = entry.getValue();
              //  
                //for (Note note : notes) {
                //    final String fullNote = note.note();
                //    final int length = fullNote.length();
                //    
                //    String beginNoteFragment = fullNote.substring(0,
                //            length >= SUBSTRLIMIT ? SUBSTRLIMIT : length);
                //            
                //    sb.append(RADIOFMT.formatted(
                //            note.id(), 
                //            counter++ == 0 ? "checked" : "", 
                //            beginNoteFragment));
                //}
            //}  
            //*/ 
            
            return sb.toString();
        }
        
        
        private boolean isCreateMode() { 
            return Modes.CREATE.equals(mode); 
        }
    
    
        private boolean isValidCreatedNote() {
            return isValidCreatedItem(SUBJECT) 
                    && isValidCreatedItem(CREATED_NOTE);
        }
        
        
        private boolean isValidCreatedItem(final String content) {
            return ! (content == null 
                    || content.isEmpty() 
                    || content.isBlank());
        }
        
        
        private void createNote() { 
            MyNotesForWeb.addNote(new Note(SUBJECT, CREATED_NOTE)); 
        }
        
        
        private void generateErrorMessages(final StringBuilder sb, final String... items) {
            for (String item : items) {
                if (! isValidCreatedItem(item)) { 
                    sb.append(getErrorMsg(item)); 
                }
            }
        }
        
        
        private String getErrorMsg(final String item) { 
            return ERRCREATEFMT.formatted(item, item); 
        }
        
        
        private Set<String> getSelectedSubjects() {
    			      Set<String> result = new TreeSet<>();
    			      
    			      request.getParameterMap()
    			             .forEach((key, value) -> {
    			                     String subject = key; 
                           String[] wrapper = value;
                        
                           String checkboxStatus = wrapper.length > 0 ? wrapper[0] : "";
                           boolean isCheckboxOn = "on".equals(checkboxStatus);
                           boolean isValidSubjectName = 
                                   getAvailableSubjects().contains(subject);
                    
                           if (isCheckboxOn && isValidSubjectName) { 
                               result.add(subject); 
                           }
    			             });
    			      
    			      ///*
    			      //for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
        	    //    String subject = entry.getKey(); 
            //    String[] wrapper = entry.getValue();
            //
            //    String checkboxStatus = wrapper.length > 0 ? wrapper[0] : "";
            //    boolean isCheckboxOn = "on".equals(checkboxStatus);
            //    boolean isValidSubjectName = getAvailableSubjects().contains(subject);
            //
            //    if (isCheckboxOn && isValidSubjectName) { result.add(subject); }
        			  //}
        			  //*/
    			      
    			      return result;
    			  }
    }
    
    
    final Service SERVICE = new Service();
%>