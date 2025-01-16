package dumbell.mvc.controllers;


import static java.util.Objects.requireNonNull;

import dumbell.mvc.views.*;
import dumbell.mvc.models.*;
import dumbell.enums.Indexes;


public class ElementController extends AbstractController {

    public View fillByOneDisk(double iElem) {
        var model = new MainModel();
        setValidAttribute(model, Indexes.I, iElem, "iElement");
        return new IElementView(model);
    }
    
    
    public View fillByTwoDisks(double iElem, double jElem) {
        var model = new MainModel();
        setValidAttribute(model, Indexes.I, iElem, "iElement");
        setValidAttribute(model, Indexes.J, jElem, "jElement");
        return new JElementView(model);
    }
    
    
    public View fillByThreeDisks(double iElem, double jElem, double kElem) {
        var model = new MainModel();
        setValidAttribute(model, Indexes.I, iElem, "iElement");
        setValidAttribute(model, Indexes.J, jElem, "jElement");
        setValidAttribute(model, Indexes.K, kElem, "kElement");
        return new KElementView(model);
    }
    
    
    public View fillByFourDisks(double iElem, double jElem, double kElem, double nElem) {
        var model = new MainModel();
        setValidAttribute(model, Indexes.I, iElem, "iElement");
        setValidAttribute(model, Indexes.J, jElem, "jElement");
        setValidAttribute(model, Indexes.K, kElem, "kElement");
        setValidAttribute(model, Indexes.N, nElem, "nElement");
        return new NElementView(model);
    }
    
    
    private void setValidAttribute(AbstractModel model, Indexes idx, double elem, String elemName) {
        requireNonNull(model);
        requireNonNull(idx);
        model.setAttribute(idx, validateElem(elem, elemName));
    }
    
    
    private double validateElem(double elem, String elemName) {
        requireNonNull(elemName);
        
        if (0 >= elem) {
            throw new IllegalArgumentException(
                    STR."""
                    Значение \{elemName} должно быть больше нуля!
                    Переданное значение: \{elem} .
                    """
            );
        }
        
        return elem;
    }
}