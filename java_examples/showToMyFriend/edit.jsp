<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="service.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Редактирование заметок</title>
</head>
<body>
    <h1><b>Редактирование заметок:</b></h1>
    <h2><b>Выберите тему редактируемой заметки:</b></h2>
    <form method="post" action="edit.jsp">
    <select name="subject" size="7">  
    <%= SERVICE.showDefault() %>
    </select><br><br> 
    <input type="submit" name="mode" value="Выбрать тему заметки">  
    <%= SERVICE.showChangeSubject() %>
    <%= SERVICE.showChangeNote() %>
    <%= SERVICE.showUpdateNote() %>
    </form>
</body>
</html>