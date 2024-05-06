<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="myservlets.APIServlet" %>
<%@ page import="using_spring.SpringConfig" %>
<%@ page import="org.springframework.context.annotation.AnnotationConfigApplicationContext" %>
<%@ page import="org.springframework.beans.factory.annotation.Autowired" %>
<%@ page import="org.springframework.stereotype.Component" %>
<%@ page import="jakarta.servlet.http.HttpServletRequest" %>
<% 

AnnotationConfigApplicationContext context = 
        new AnnotationConfigApplicationContext(SpringConfig.class);
        
context.registerBean(
        "httpServletRequest", 
        HttpServletRequest.class, 
        () -> request);

APIServlet API = context.getBean("aPIServlet", APIServlet.class); 

%>