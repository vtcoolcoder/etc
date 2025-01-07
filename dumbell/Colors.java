package dumbell;


import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.stream.Collectors;


class Colors { 
          
   private static final String[] DEFAULT_COLORS = DefaultColors.getColors();   
   private static final String DEFAULT_FIRST_COLOR = getFirstColorByColors(DEFAULT_COLORS);
   private static final int FIRST_COLOR_ORDINAL = 0; 
   private static final int DEFAULT_LAST_COLOR_ORDINAL = getLastOrdinalByColors(DEFAULT_COLORS);   
   private static final int STEP_COLOR_ORDINAL = 1;    
   
   private final String[] userColors;
   private final String firstColor;
   private final int lastColorOrdinal;
   
   
   Colors() { this(DEFAULT_COLORS); }
   
   
   Colors(String[] userColors) { 
       this.userColors = getTrimmedColorNames(userColors); 
       this.firstColor = getFirstColorByColors(this.userColors);
       this.lastColorOrdinal = getLastOrdinalByColors(this.userColors);
   }
   
        
   String getFirstColor() { return firstColor; }
   
   
   String getNextColor(String currentColor) {
       return userColors[getNextColorOrdinal(getIndexByColor(currentColor))];
   }

   
   String getAllHTMLInlinedColorNames() {                  
       return Arrays.stream(userColors)
                .map(color ->
                        STR."""
                                .\{color} {
                                    background-color: \{color};   
                                } 
                        """
                ).collect(Collectors.joining("\n"));
    }
   
         
   private int getNextColorOrdinal(int currentOrdinal) {
        return (currentOrdinal == lastColorOrdinal) 
                ? FIRST_COLOR_ORDINAL 
                : (currentOrdinal + STEP_COLOR_ORDINAL);
   }
   
   
   private int getIndexByColor(String color) {
       return Arrays.asList(userColors).indexOf(requireNonNull(color));
   }
   
   
   private static String getFirstColorByColors(String[] source) {  
       return Arrays.stream(requireNonNull(source)).findFirst().get();
   }
   
   
   private static int getLastOrdinalByColors(String[] source) {
       return requireNonNull(source).length - 1;
   }
   
   
   private static String[] getTrimmedColorNames(String[] source) {
       return Arrays.stream(requireNonNull(source))
               .map(String::trim)
               .toArray(String[]::new);
   }
}