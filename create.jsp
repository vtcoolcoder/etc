<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="service.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Добавление заметок</title>
</head>
<body>
    <div>
        <h3>Навигация</h3>
        <ul>
            <li><a href="index.jsp">Читать</a></li>
            <li><a href="update.jsp">Обновить</a></li>
            <li><a href="delete.jsp">Удалить</a></li>
        </ul>
    </div>
    <h1>Добавление заметок:</h1>
    <%= API.tryCreateNote() %>   
    <h2>Выберите тему добавляемой заметки:</h2>
    <form method="post" action="create.jsp">
    <select name="subject" size="7">
    <%= API.showDefault() %>
    </select>      
    <b>Новая тема:</b><input type="text" name="subject">
    <textarea name="createdNote" cols="130" rows="35" wrap="hard"></textarea>
    <input type="submit" name="mode" value="Добавить заметку"></form>
</body>
</html>