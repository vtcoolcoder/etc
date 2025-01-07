package dumbell.enums;


import java.util.Objects;
import java.util.Arrays;


public enum DefaultColors {
       
   PINK("pink"),
   VIOLET("violet"),  
   GOLD("gold"),     
   LIGHT_BLUE("lightblue"),  
   ORANGE("orange"),   
   LIGHT_GREEN("lightgreen"), 
   CORAL("coral");
   
   private static final String[] COLORS = 
           Arrays.stream(values()).map(Object::toString).toArray(String[]::new);
   
   private String colorName; 
   
   
   public static String[] getColors() { return COLORS; }
   
   
   DefaultColors(String colorName) { this.colorName = Objects.requireNonNull(colorName); }
   
   
   @Override
   public String toString() { return colorName; }
}