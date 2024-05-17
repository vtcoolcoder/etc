<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="myservlets.APIServlet" %>
<%@ page import="jdbc_template.Config" %>
<%@ page import="org.springframework.context.annotation.AnnotationConfigApplicationContext" %>
<%@ page import="jakarta.servlet.http.HttpServletRequest" %>
<%@ page import="lombok.Cleanup" %>
<%     
    @Cleanup
    AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(Config.class);
          
    context.registerBean(
            "request",
            HttpServletRequest.class,
            () -> request);
                    
    APIServlet API = context.getBean(APIServlet.class); 
%>