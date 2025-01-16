package dumbell.mvc.controllers;


import dumbell.enums.Indexes;
import dumbell.mvc.views.View;
import dumbell.mvc.views.AbstractView;
import dumbell.mvc.models.AbstractModel;


public abstract class AbstractController implements Controller {

    public View doSomething(AbstractModel model) {
        model.setAttribute(Indexes.I, 23.23);
        return new AbstractView(model) {};
    }
}