<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page language="java" import="java.util.Date" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Мой первый JSP</title>
</head>
<body>
    <h1>Добро пожаловать на мой первый JSP-файл!</h1>
    <h2>Текущее время: 
        <%= new Date() %>
    </h2>
</body>
</html>