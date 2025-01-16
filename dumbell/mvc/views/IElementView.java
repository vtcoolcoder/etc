package dumbell.mvc.views;


import dumbell.mvc.models.AbstractModel;
import dumbell.enums.Indexes;


public class IElementView extends AbstractView {

    protected final double iElement;
    

    public IElementView(AbstractModel model) {
        super(model);
        this.iElement = model.getAttribute(Indexes.I);
    }
    
    
    @Override
    public String toString() {
        return STR."\{toIntOrFractString(iElement)}";
    }
}