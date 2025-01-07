import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;


@Controller
public class MainController {

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("title", "Welcome");
        model.addAttribute("message", "Hello, World!");
        return "home.jsp";
    }
}