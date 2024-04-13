<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.Set" %>
<%@ page import="myjdbc.MyNotesForWeb, myjdbc.Note" %>

<% 
    String subjectRec = request.getParameter("subject");
    String noteRec = request.getParameter("note");
    
    if (subjectRec != null && noteRec != null) {
			      MyNotesForWeb.addNote(new Note(subjectRec, noteRec));   
			  }
			  
    Set<String> availableSubjects = MyNotesForWeb.getSubjectSet();   
%>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Добавление заметок</title>
</head>
<body>
    <h1>Добавление заметок:</h1>
    <h2>Выберите тему заметки:</h2>
    <form method="post" action="create.jsp">
    <select name="subject" size="7">
    
    <%
        final String OPTFMT = "<option value=\"%s\">%s</option>";
        
        for (String subject : availableSubjects) {
            out.println(OPTFMT.formatted(subject, subject));
        }
    %>
    
    </select>      
    <b>Тема:</b><input type="text" name="subject">
    <textarea name="note" cols="130" rows="35" wrap="hard"></textarea>
    <input type="submit" value="Добавить заметку"></form>
</body>
</html>