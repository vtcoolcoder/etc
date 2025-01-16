package dumbell.mvc.views;


import dumbell.mvc.models.AbstractModel;
import dumbell.enums.Indexes;


public class NElementView extends KElementView {

    protected final double nElement;
    

    public NElementView(AbstractModel model) {
        super(model);
        this.nElement = model.getAttribute(Indexes.N);
    }
    
    
    @Override
    public String toString() {
        return STR."\{toIntOrFractString(nElement)} + \{super.toString()}";
    }
    
    
    @Override
    public double getElementSum() { return super.getElementSum() + nElement; }
}