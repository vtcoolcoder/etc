<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page language="java" import="java.util.Date" %>
<%@ page import="mylib.MyContent" %>


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
	<h3>
		<%= MyContent.NAME %>
	</h3>
	<h4>
		<%= MyContent.getRandomNumber() %>
	</h4>

	<p>
		<%
			String name = request.getParameter("name");
			String surname = request.getParameter("surname");
			int studentLimit = 0;

			try {
				studentLimit = Integer.parseInt(request.getParameter("limit"));
			} catch (RuntimeException e) {}

			studentLimit = studentLimit <= 0 ? 0 : studentLimit;
		%>
    
		<%= 
			"%s %s".formatted(name != null ? name : "Уважаемый",
							  surname != null ? surname : "Сударь")
		%>
	</p>

	<p>
		<%@ page import="mylib.Student" %>
		<%
			for (int i = 0; i < studentLimit; i++) {
				out.println(new Student().toWebString() + "<br>");
			}
		%>
	</p>

	<p>
		<%@ page import="java.util.Map" %>
		<%      
			Map<String, String[]> params = request.getParameterMap();

			for (Map.Entry<String, String[]> entry : params.entrySet()) {
				out.println("Имя параметра: %s<br>Значения:<br>".formatted(entry.getKey())); 

				for (String value : entry.getValue()) {
					out.println("%s<br>".formatted(value));
				}

				out.println("<br><br>");
			}
		%>
	</p>
</body>
</html>
