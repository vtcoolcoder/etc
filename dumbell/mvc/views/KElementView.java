package dumbell.mvc.views;


import dumbell.mvc.models.AbstractModel;
import dumbell.enums.Indexes;


public class KElementView extends JElementView {

    protected final double kElement;
    

    public KElementView(AbstractModel model) {
        super(model);
        this.kElement = model.getAttribute(Indexes.K);
    }
    
    
    @Override
    public String toString() {
        return STR."\{toIntOrFractString(kElement)} + \{super.toString()}";
    }
    
    
    @Override
    public double getElementSum() { return super.getElementSum() + kElement; }
}