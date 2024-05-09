<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="myservlets.APIServlet" %>
<%@ page import="using_spring.SpringConfig" %>
<%@ page import="org.springframework.context.annotation.AnnotationConfigApplicationContext" %>
<%@ page import="org.springframework.context.annotation.Bean" %>
<%@ page import="org.springframework.context.annotation.Configuration" %>
<%@ page import="jakarta.servlet.http.HttpServletRequest" %>
<% 
    //SpringConfig.setBean(request);
    
    AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(new SpringConfig() {
                @Bean
                public HttpServletRequest httpServletRequest() {
                    return request;
                }
            }.getClass()); 
                              
    APIServlet API = context.getBean(APIServlet.class); 
%>