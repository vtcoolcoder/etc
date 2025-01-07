package dumbell;


import static dumbell.DumbBell.Represent;
import static dumbell.Constants.*;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Consumer;


class Printer {

    static void printBeginning(DumbBell dumbBell) {
        System.out.printf(
                BEGIN_TEMPLATE, 
                STR."\{dumbBell.colors.getAllHTMLInlinedColorNames()}", 
                STR."\{dumbBell.gripWeight}"
        );
    }
    
    
    static void printAssymetricComboPrefix() {
        System.out.println(ASYMMETRIC_TEMPLATE);
    }
    
    
    static void printEnding() {
        System.out.println(ENDING_TEMPLATE);
    }
    
    
    static void printContent(List<Represent> target, 
                             Consumer<Represent> func, 
                             Consumer<List<Represent>> firstAction, 
                             Runnable secondAction) {
        requireNonNull(target);
        requireNonNull(func);
        requireNonNull(firstAction);
        requireNonNull(secondAction);
        
        firstAction.accept(target);   
        secondAction.run();
        target.forEach(func);
    }
    
    
    static void coloringRows(double currentValue, 
                             String info, 
                             DumbBell dumbBell, 
                             String currentColor,
                             Runnable action) { 
        requireNonNull(info);
        requireNonNull(dumbBell);
        requireNonNull(currentColor);
        requireNonNull(action);
          
        if (currentValue != dumbBell.cachedValue) {
            action.run();
        }  
                
        System.out.printf(info, currentColor);     
        dumbBell.cachedValue = currentValue;
    }
}