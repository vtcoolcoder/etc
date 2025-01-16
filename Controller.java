

public class Controller {

    public View doSomething(Model model) {
        model.setAttribute("", new Object());
        return new View(model);
    }
}