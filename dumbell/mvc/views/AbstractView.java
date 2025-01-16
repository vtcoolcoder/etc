package dumbell.mvc.views;


import dumbell.mvc.models.AbstractModel;
import java.util.Objects;


public abstract class AbstractView implements View {

    private final AbstractModel model;


    protected AbstractView(AbstractModel model) {
        Objects.requireNonNull(model);
        this.model = model;
    }
    
    
    @Override
    public String toString() {
        return model.toString();
    }
    
    
    protected final String toIntOrFractString(double number) {
        return (0 == number % 1) 
                ? String.valueOf((int) number)
                : String.valueOf(number);
    }
}