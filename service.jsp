<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.Set, java.util.Map, java.io.PrintWriter, java.io.IOException" %>
<%@ page import="myjdbc.MyNotesForWeb, myjdbc.Note" %>
<%@ page import="modes.Modes" %>

<% 
    final int SUBSTRLIMIT = 69;
    
    class Service {
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
                case Modes.Consts.BYDEFAULT:
                    this.mode = Modes.BYDEFAULT;
            }
        }
        
        private String MODE = request.getParameter("mode");
        private String SUBJECT = request.getParameter("subject");
        private String SELECTED_NOTE = request.getParameter("selectedNote");
        private String EDITED_NOTE = request.getParameter("editedNote");
        private String DELETED_NOTE = SELECTED_NOTE;
        
        private Modes mode;
        private boolean isUnselectedSubject = false;
        private boolean isUnselectedNote = false;
        private PrintWriter out = response.getWriter();
        
        public String getMode() { return MODE; }
        public String getSubject() { return SUBJECT; }
        public String getSelectedNote() { return SELECTED_NOTE; }
        public String getEditedNote() { return EDITED_NOTE; }
        public String getDeletedNote() { return DELETED_NOTE;}
        
        
        public String showDefault() {
            StringBuilder sb = new StringBuilder();
            
            Set<String> availableSubjects = MyNotesForWeb.getSubjectSet();  
            final String OPTFMT = "<option value=\"%s\">%s</option>";
            
            for (String subject : availableSubjects) {
                sb.append(OPTFMT.formatted(subject, subject));
            }
            
            return sb.toString();
        }
        
        
        public String showChangeSubject() {
            StringBuilder sb = new StringBuilder();
            
            if (Modes.SUBJECT.equals(mode)) {
                   if (SUBJECT != null) {         
                    sb.append("<input type=\"hidden\" name=\"subject\" value=\"%s\">"
                            .formatted(SUBJECT));
                            
                    sb.append("<h2><b>Выбранная тема: </b><i><u>%s</u></i></h2>"
                            .formatted(SUBJECT));
                } else {
                    sb.append("<p>Вы не выбрали тему.<br>");
                    sb.append("Пожалуйста, выберите тему.</p>");
                    isUnselectedSubject = true;
                }
            }
            
            return sb.toString();   
        }
        
        
        public String showChangeNote() {
            StringBuilder sb = new StringBuilder();
            
            if (Modes.NOTE.equals(mode)) {
                if (SELECTED_NOTE != null && !isUnselectedNote) {        
                    sb.append("<input type=\"hidden\" name=\"selectedNote\" value=\"%s\">"
                            .formatted(SELECTED_NOTE));
                            
                    int ID = -1;
                    try { ID = Integer.parseInt(SELECTED_NOTE); }
                    catch (NumberFormatException ex) {}
                
                    String FULLCONTENT = (ID != -1) ? MyNotesForWeb.getNoteContentById(ID) : ""; 
                    int length = FULLCONTENT.length(); 
                    String FRAGMENT = FULLCONTENT.substring(0, (length >= SUBSTRLIMIT) ? SUBSTRLIMIT : length); 
                                  
                    sb.append("<h2><b>Выбранный фрагмент:</b><br><i><u>%s</u></i> .......</h2>"
                            .formatted(FRAGMENT));
                            
                } else {
                    sb.append("<p>Вы не выбрали фрагмент.<br>");
                    sb.append("Пожалуйста, выберите фрагмент.</p>");
                    isUnselectedNote = true;
                }
              
            } else {
                Map<String, Set<Note>> availableRecords = 
                    MyNotesForWeb.getNotesBySelectedSubjects(SUBJECT != null ? SUBJECT : "");
                    
                final String RADIOFMT = 
                    "<h3><input type=\"radio\" name=\"selectedNote\" value=\"%d\" %s><br>Фрагмент:</h3>" +
                    "<code><b>%s</b></code> .......<hr>";
                    
                sb.append(iterate(availableRecords, RADIOFMT));              
                
                if (Modes.NOTE.equals(mode) || SUBJECT != null) {
                    sb.append("<input type=\"submit\" name=\"mode\" value=\"Выбрать заметку\">");
                }
                
            }   
            
            return sb.toString();   
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
    }
    
    
    final Service SERVICE = new Service();
%>