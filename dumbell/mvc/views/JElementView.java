package dumbell.mvc.views;


import dumbell.mvc.models.AbstractModel;
import dumbell.enums.Indexes;


public class JElementView extends IElementView {

    protected final double jElement;
    

    public JElementView(AbstractModel model) {
        super(model);
        this.jElement = model.getAttribute(Indexes.J);
    }
    
    
    @Override
    public String toString() {
        return STR."\{toIntOrFractString(jElement)} + \{super.toString()}";
    }
    
    
    public double getElementSum() { return iElement + jElement; }
}