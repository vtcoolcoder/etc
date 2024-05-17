<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="myservlets.APIServlet" %>
<%@ page import="org.springframework.context.annotation.AnnotationConfigApplicationContext" %>
<%@ page import="jakarta.servlet.http.HttpServletRequest" %>
<%@ page import="lombok.Cleanup" %>
<%     
    @Cleanup
    AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(jdbc_template.Config.class);
          
    context.registerBean(
            "request",
            HttpServletRequest.class,
            () -> request);
                    
    APIServlet API = context.getBean(APIServlet.class); 
%>