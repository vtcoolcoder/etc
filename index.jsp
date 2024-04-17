<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="service.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Заметки</title>
</head>
<body>
    <div>
        <ul>
            <li><a href="create.jsp">Создать</a></li>
            <li><a href="update.jsp">Обновить</a></li>
            <li><a href="delete.jsp">Удалить</a></li>
        </ul>
    </div>
    <h1>Заметки</h1>
    <h2>Список доступных тем:</h2>
    <form method="post" action="index.jsp">
    <%= API.showCheckboxesWithSubjects() %>
    <br><input type="submit" name="mode" value="Выбрать тему заметки"></form>   
    <%= API.showNotesBySelectedSubjects() %>
</body>
</html>
