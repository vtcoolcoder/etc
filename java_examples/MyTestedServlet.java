import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;


public class MyTestedServlet extends HttpServlet {
    public static final String HTML = 
    """
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="UTF-8" />
        <title>Мой первый тестовый сервлет</title>
    </head>
    <body>
        <h1>Добро пожаловать на мой первый тестовый сервлет!</h1>
    </body>
    </html>
    """;

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
        
        writer.println(HTML);
    }
}