<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page language="java" import="java.util.Date" %>
<%@ page import="mylib.MyContent" %>

<%@ page import="java.util.TimeZone" %>
<% TimeZone.setDefault(TimeZone.getTimeZone("Europe/Moscow")); %>

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
			Student.enableWebFormatMode();

			for (int i = 0; i < studentLimit; i++) {
				out.println(new Student() + "<br>");
			}
			
			Object[][] wrongArgs = { 
					{ "23", "69", -1, -1, -1 },
					{ "Юля", "098", -1, -1, -1 },
					{ "Ася", "Казанцева", -1, -1, -1 },
					{ "Рита", "Романова", 19, -1, -1 },
					{ "Катя", "Митрошина", 30, 170, -1 } };
        
			Object[][] correctArgs = { { "Оля", "Максимова", 23, 165, 55 } };

			Object[][][] dataArgs = { wrongArgs, correctArgs };

			for (Object[][] currentDataArgs : dataArgs) {
				for (Object[] args : currentDataArgs) {
					String curName = (String) args[0];
					String curSurname = (String) args[1];
					int curAge = (int) args[2];
					int curHeight = (int) args[3];
					int curWeight = (int) args[4];
        
					try {
						Student trialStudent = new Student(curName, curSurname, curAge, curHeight, curWeight);
						out.println("<h3>Test-created student:</h3>" + trialStudent);
					} catch (RuntimeException e) {
						out.println(e.getMessage() + "<br>");
					}
				}
			}

			Student.disableWebFormatMode();
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
