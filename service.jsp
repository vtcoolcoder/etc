<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="myservlets.APIServlet" %>
<%@ page import="myservlets.ServiceServlet" %>
<%@ page import="using_spring.SpringConfig" %>
<%@ page import="using_spring.WebNotes" %>
<%@ page import="org.springframework.context.annotation.AnnotationConfigApplicationContext" %>
<%@ page import="org.springframework.beans.factory.annotation.Autowired" %>
<%@ page import="org.springframework.stereotype.Component" %>
<%@ page import="jakarta.servlet.http.HttpServletRequest" %>
<% 

AnnotationConfigApplicationContext context = 
        new AnnotationConfigApplicationContext(SpringConfig.class);
        
context.registerBean(
        "serviceServlet",
        ServiceServlet.class,
        () -> new ServiceServlet(request, context.getBean(WebNotes.class)));

context.registerBean(
        "apiServlet",
        APIServlet.class,
        () -> new APIServlet(context.getBean("serviceServlet", ServiceServlet.class)));
 
APIServlet API = context.getBean("apiServlet", APIServlet.class); 

%>