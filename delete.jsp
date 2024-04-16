<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="service.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Удаление заметок</title>
</head>
<body>
    <h1><b>Удаление заметок:</b></h1>
    <h2><b>Выберите тему удаляемой заметки:</b></h2>
    <form method="post" action="delete.jsp">
    <select name="subject" size="7">  
    <%= API.showDefault() %>
    </select><br><br> 
    <input type="submit" name="mode" value="Выбрать тему заметки">  
    <%= API.showChangeSubject() %>
    <%= API.showChangeNote() %>
    <%= API.showDeleteNote() %>
    </form>
</body>
</html>