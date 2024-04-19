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
        <h1>Навигация</h1>
        <ul>
            <li><h2><a href="create.jsp">Создать</a></h2></li>
            <li><h2><a href="update.jsp">Обновить</a></h2></li>
            <li><h2><a href="delete.jsp">Удалить</a></h2></li>
        </ul>
    </div>
    <hr>
    <h1>Заметки</h1>
    <form method="post" action="index.jsp">
    <%= API.showRandomNote() %>
    <%= API.showCheckboxesWithSubjects() %>
    <br><input type="submit" name="mode" value="Выбрать тему заметки">
    </form>   
    <%= API.showNotesBySelectedSubjects() %>
</body>
</html>
