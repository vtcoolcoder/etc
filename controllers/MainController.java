package controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
//@RequestMapping("/home")
public class MainController  {     
        
    @GetMapping("/welcome")
    public String welcome() {
        return "welcome.html";
    }
}
