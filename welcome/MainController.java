package welcome;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {

    @GetMapping("/welcome")
    public String welcome(Model model) {
        model.addAttribute("message", "Добро пожаловать в Spring MVC!");
        return "welcome.jsp";
    }
}

