<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.Set" %>
<%@ page import="myjdbc.MyNotesForWeb, myjdbc.Note" %>

<%@ page import="java.util.Map, java.util.Set, java.util.TreeSet" %>
<%   
    Map<String, String[]> params = request.getParameterMap(); 
    
    Set<String> availableSubjects = MyNotesForWeb.getSubjectSet();   
			  Set<String> selectedSubjects = new TreeSet<>();

			  for (Map.Entry<String, String[]> entry : params.entrySet()) {
    	    String subject = entry.getKey(); 
        String[] wrapper = entry.getValue();
        
        String checkboxStatus = wrapper.length > 0 ? wrapper[0] : "";
        boolean isCheckboxOn = "on".equals(checkboxStatus);
        boolean isValidSubjectName = availableSubjects.contains(subject);
    
        if (isCheckboxOn && isValidSubjectName) {
    			      selectedSubjects.add(subject);
    			  }
			  }
%>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Заметки</title>
</head>
<body>
    <h1>Список доступных тем:</h1>
    <form method="post" action="notes.jsp">
    <%
        final String CHECKBOXFMT = "<input type=\"checkbox\" name=\"%s\" %s> %s<br>";
        
        for (String subject : availableSubjects) {   
            String checked = selectedSubjects.contains(subject) ? "checked" : "";
            out.println(CHECKBOXFMT.formatted(subject, checked, subject));
        }
    %>
    
    <input type="submit" value="Выбрать тему"></form>
    
    <h1>Заметки по выбранным темам:</h1>
    <%
        Map<String, Set<Note>> availableRecords = 
                MyNotesForWeb.getNotesBySelectedSubjects(selectedSubjects);
                
        final String RECORD_FORMAT = 
                "<h3><b>Тема:</b> <i><u>%s</u></i> <b>| Заметка:</b></h3><div>%s</div><hr>";
        
        for (Map.Entry<String, Set<Note>> entry : availableRecords.entrySet()) {
            Set<Note> notes = entry.getValue();
            
            for (Note note : notes) {
                out.println(RECORD_FORMAT.formatted(
                        note.subject(), note.note()));
            }
            
            out.println("<hr>");
        }   
    %>
</body>
</html>
