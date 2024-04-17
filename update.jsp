<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="service.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Редактирование заметок</title>
</head>
<body>
    <div>
        <h3>Навигация</h3>
        <ul>
            <li><a href="index.jsp">Читать</a></li>
            <li><a href="create.jsp">Создать</a></li>
            <li><a href="delete.jsp">Удалить</a></li>
        </ul>
    </div>
    <h1><b>Редактирование заметок:</b></h1>
    <h2><b>Выберите тему редактируемой заметки:</b></h2>
    <form method="post" action="update.jsp">
    <select name="subject" size="7">  
    <%= API.showDefault() %>
    </select><br><br> 
    <input type="submit" name="mode" value="Выбрать тему заметки">  
    <%= API.showChangeSubject() %>
    <%= API.showChangeNote() %>
    <%= API.showUpdateNote() %>
    </form>
</body>
</html>