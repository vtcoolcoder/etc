package dumbell.events;


import static java.util.Objects.requireNonNull;

import dumbell.enums.*;
import dumbell.constants.DefaultGripData;
import java.util.Map;


public record MainEvent(double gripWeight,
                        double[] uniquePairs,
                        Map<Double, Integer> pairAmount,
                        SideAmount sideAmount,
                        DiscAmount discAmount,
                        String[] backgroundRowColors) 
                        
        implements Event, DefaultGripData {
   
    
    public MainEvent {
        requireNonNull(uniquePairs);
        requireNonNull(sideAmount);
        requireNonNull(discAmount);  
        requireNonNull(backgroundRowColors); 
    }
    
    
    public MainEvent(double gripWeight, 
                     double[] uniquePairs, 
                     SideAmount sideAmount, 
                     DiscAmount discAmount,
                     String[] backgroundRowColors) {
    
        this(
                gripWeight, 
                uniquePairs, 
                null, 
                sideAmount, 
                discAmount, 
                backgroundRowColors
        );      
    }             
    
    
    public MainEvent(double gripWeight, 
                     double[] uniquePairs, 
                     Map<Double, Integer> pairAmount,
                     SideAmount sideAmount, 
                     DiscAmount discAmount) {
    
        this(
                gripWeight, 
                uniquePairs, 
                pairAmount, 
                sideAmount, 
                discAmount, 
                COLORS
        );      
    }     
    
    
    public MainEvent(double gripWeight, 
                     double[] uniquePairs, 
                     SideAmount sideAmount, 
                     DiscAmount discAmount) {
    
        this(
                gripWeight, 
                uniquePairs, 
                sideAmount, 
                discAmount, 
                COLORS
        );      
    }  
    
    
    public MainEvent(double gripWeight, 
                     double[] uniquePairs) {
    
        this(
                gripWeight, 
                uniquePairs, 
                SideAmount.TWO, 
                DiscAmount.THREE
        );      
    }   
    
    
    public MainEvent(double[] uniquePairs) {
        this(GRIP_WEIGHT, uniquePairs);
    }  
    
    
    public MainEvent() {
        this(ALL_WEIGHTS);
    }                          
}