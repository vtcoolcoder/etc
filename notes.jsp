<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="service.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Заметки</title>
</head>
<body>
    <h1>Заметки</h1>
    <h2>Список доступных тем:</h2>
    <form method="post" action="notes.jsp">
    <%= SERVICE.showCheckboxesWithSubjects() %>
    <input type="submit" name="mode" value="Выбрать тему заметки"></form>   
    <%= showNotesBySelectedSubjects() %>
</body>
</html>
