import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import java.util.function.Consumer;
import java.util.function.BiConsumer;
import java.util.function.DoubleConsumer;

import java.util.stream.Collectors;


public enum Ganteli {

    _0_5(0.5),
    //_0_75(0.75),
    _1(1.0),
    _1_25(1.25),
    _1_5(1.5),
    _2(2.0),
    _2_5(2.5),
    _5(5.0);
   
   
   private enum Colors { 
   
       PINK("pink"),
       VIOLET("violet"),  
       GOLD("gold"),     
       LIGHT_BLUE("lightblue"),  
       ORANGE("orange"),   
       LIGHT_GREEN("lightgreen"), 
       CORAL("coral");
       
       private static final Colors[] COLORS = values();   
       private static final Colors FIRST_COLOR = Arrays.stream(COLORS).findFirst().get();
       private static final int FIRST_COLOR_ORDINAL = 0; 
       private static final int LAST_COLOR_ORDINAL = COLORS.length - 1;   
       private static final int STEP_COLOR_ORDINAL = 1;      
       
       
       private String colorName; 
       
       
       Colors(String colorName) { this.colorName = colorName; }
       
       
       public static Colors getFirstColor() { return FIRST_COLOR; }
       
       
       public static Colors getNextColor(Colors currentColor) {
           return COLORS[getNextColorOrdinal(currentColor.ordinal())];
       }
    
       
       public static String getAllHTMLInlinedColorNames() {                  
           return Arrays.stream(COLORS)
                    .map(color ->
                            STR."""
                                    .\{color} {
                                        background-color: \{color};   
                                    } 
                            """
                    ).collect(Collectors.joining("\n"));
        }
       
       
       @Override
       public String toString() { return colorName; }
       
       
       private static int getNextColorOrdinal(int currentOrdinal) {
            return (currentOrdinal == LAST_COLOR_ORDINAL) 
                    ? FIRST_COLOR_ORDINAL 
                    : (currentOrdinal + STEP_COLOR_ORDINAL);
       }
   }
   
   
   
   
   private record Represent(double sum, String msg) implements Comparable<Represent> {
   
       private static final Comparator<Represent> COMPARATOR = Comparator.comparingDouble(Represent::sum);
       
       
       public double getTotalWeight() { return getTotalWeightByOneDisk(sum); }
       
       
       public static double getTotalWeightByOneDisk(double diskWeight) {
           return diskWeight*SIDE_AMOUNT + GRIF_WEIGHT;
       }
      
       
       @Override
       public int compareTo(Represent another) { return COMPARATOR.compare(this, another); }
   }
   


    
    private static final double[] VALUES = Arrays.stream(values()).mapToDouble(Ganteli::getWeight).toArray();  
    private static final List<Represent> RESULTS = new ArrayList<>();
    private static final String SPACES = " ".repeat(23);
    private static final double GRIF_WEIGHT = 1.5;
    private static final int SIDE_AMOUNT = 2;
    
    
    private static double cachedValue = Double.NEGATIVE_INFINITY;
    private static Colors currentColor = Colors.getFirstColor();
    
    
    private double weight; 
    
       
    Ganteli(double weight) { this.weight = weight; }
    
    
    public double getWeight() { return weight; }
    
    
    public String toString() {
        return STR."\{weight}";
    }
    
       
    public static void main(String[] args) {   
        showInfo();
        separateOutputByLines();
        showBaseData();    
    }
    
    
    private static void showInfo() {
        showOneDisksResultPart();
        showTwoDiskComboResultPart();
        showThreeDiskComboResultPart();
        //showFourDiskComboResultPart();
    }
    
    
    public static void showOneDisksResultPart() {
        showTitle("С одним диском:");
        processOneDisksResultPart(item -> System.err.println(
                STR."\{item}\{SPACES}\{Represent.getTotalWeightByOneDisk(item)}"
        ));
    }
    
       
    public static void showTwoDiskComboResultPart() {
        showTitle("С двумя дисками:");
        showing(Ganteli::fillTwoDiskComboResultPart);
    }
    
    
    public static void showThreeDiskComboResultPart() {
        showTitle("С тремя дисками:");
        showing(Ganteli::fillThreeDiskComboResultPart);
    }
    
    
    public static void showFourDiskComboResultPart() {
        showTitle("С четырьмя дисками:");
        showing(Ganteli::fillFourDiskComboResultPart);
    }
    
    
    private static void separateOutputByLines() {
        System.err.println();
        showTitle("");
        System.err.println();
    }
    
    
    private static void showBaseData() {
        preparing();                   
        printBeginning();      
        printBaseData();            
        printAssymetricComboes(getUniqueOneSidedSums());      
        printEnding(); 
    }
    
      
    private static void preparing() {
        fillOneDisksResultPart();
        fillTwoDiskComboResultPart();
        fillThreeDiskComboResultPart();
        //fillFourDiskComboResultPart();
        
        Collections.sort(RESULTS); 
    }
    
    
    private static void fillOneDisksResultPart() {       
        processOneDisksResultPart(item -> RESULTS.add(new Represent(item, STR."\{item}")));
    }
    
    
    private static void processOneDisksResultPart(DoubleConsumer func) {
        for (int i = 0; i < VALUES.length; ++i) {
            func.accept(VALUES[i]);
        }      
    }
    
    
    private static void fillTwoDiskComboResultPart() { fillTwoDiskComboResultPart(RESULTS); }   
    
    
    private static void fillTwoDiskComboResultPart(List<Represent> represents) {     
        fillTwoItems(VALUES, (iElement, jElement) -> represents.add(new Represent(
                iElement + jElement, 
                STR."\{iElement} + \{jElement}"
        )));
    }
    
    
    private static void fillTwoItems(double[] source, BiConsumer<Double, Double> func) {
        for (int i = 0; i < source.length - 1; ++i) {
            for (int j = i + 1; j < source.length; ++j) {
                func.accept(source[i], source[j]);
            }
        }
    }
    
    
    private static void fillThreeDiskComboResultPart() { fillThreeDiskComboResultPart(RESULTS); }
    
    
    private static void fillThreeDiskComboResultPart(List<Represent> represents) {
        for (int i = 0; i < VALUES.length - 2; ++i) {
            for (int j = i + 1; j < VALUES.length - 1; ++j) {
                for (int k = j + 1; k < VALUES.length; ++k) {
                    represents.add(new Represent(
                            VALUES[i] + VALUES[j] + VALUES[k], 
                            STR."\{VALUES[i]} + \{VALUES[j]} + \{VALUES[k]}"
                    ));
                }
            }
        }
    }
    
    
    private static void fillFourDiskComboResultPart() { fillFourDiskComboResultPart(RESULTS); }      
        
      
    private static void fillFourDiskComboResultPart(List<Represent> represents) {
        for (int i = 0; i < VALUES.length - 3; ++i) {
            for (int j = i + 1; j < VALUES.length - 2; ++j) {
                for (int k = j + 1; k < VALUES.length - 1; ++k) {
                    for (int n = k + 1; n < VALUES.length; ++n) {
                        represents.add(new Represent(
                                VALUES[i] + VALUES[j] + VALUES[k] + VALUES[n], 
                                STR."\{VALUES[i]} + \{VALUES[j]} + \{VALUES[k]} + \{VALUES[n]}"
                        ));
                    }
                }
            }
        }
    }
    
    
    private static void printBeginning() {
        System.out.println(
            STR."""
            <!DOCTYPE html>
            <html>
            <head>
                <title>Комбинации блинов для разборной гантели</title>
                <meta charset="UTF-8" />
                <style>
                    body {
                        background-color: black;
                        text-align: center;
                    }
                    
                    h1 {
                        color: gold;
                    }
                    
                    h2 {
                        font-style: italic;
                        font-weight: bold;
                        color: orange;
                    }
                    
                    .grif {      
                        color: snow;
                        font-style: normal;
                        font-weight: normal;
                    }
                    
                    table {
                        border-color: snow;
                        margin: 0 auto;
                        border-width: 1;
                        border-color: snow;
                        border-style: solid;
                        border-radius: 7px;
                        font-weight: bold;
                    }
                    
                    th {
                        background-color: black;
                        color: snow;
                        border-width: 1px;
                        border-style: solid;
                        border-color: snow;
                    }
            
                    td {
                        font-style: italic;
                    }
                    
            \{Colors.getAllHTMLInlinedColorNames()}
                </style>
            </head>
            <body>
                <h1>Комбинации блинов для разборной гантели</h1>
                <h2 class="grif">Вес грифа с замками: \{GRIF_WEIGHT} КГ</h2>
                <h2>Симметричные комбинации</h2>
                <table>
            """
        );
    }
    
    
    private static void printBaseData() {
        System.out.println(
            """
                    <tr>
                        <th>Комбинация</th>
                        <th>Односторонняя сумма, КГ</th>
                        <th>Общий вес с грифом при двусторонней симметричной сумме, КГ</th>
                    </tr>
            """
        );
              
        printContent(RESULTS, e -> coloringRows(e.getTotalWeight(), 
                """
                        <tr class="%%s">
                            <td>%s</td>
                            <td>%s</td>
                            <td>%s</td>
                        </tr>
                """
                    .formatted(e.msg(), e.sum(), e.getTotalWeight())
        ));
    }
    
    
    private static void printAssymetricComboes(double[] resultSums) {
        System.out.println(
            """
                </table>
                
                <h2>Несимметричные комбинации</h2>
                <table>
                
                    <tr>
                        <th>Комбинация</th>
                        <th>Общий вес с грифом, КГ</th>
                    </tr>
            """
        );
                           
        printContent(getFilledAsymmetricComboContainer(resultSums), e -> coloringRows(e.sum(),
                """
                        <tr class="%%s">
                            <td>%s</td>
                            <td>%s</td>
                        </tr>
                """
                    .formatted(e.msg(), e.sum())
        ));
    }  
    
    
    private static List<Represent> getFilledAsymmetricComboContainer(double[] resultSums) {
        var asymmetricCombo = new ArrayList<Represent>();   
        fillTwoItems(resultSums, (iElement, jElement) -> asymmetricCombo.add(new Represent(
                iElement + jElement + GRIF_WEIGHT,
                STR."\{iElement} + \{jElement}"
        )));           
        Collections.sort(asymmetricCombo);    
        return asymmetricCombo;
    }
    
    
    private static double[] getUniqueOneSidedSums() {
        return RESULTS.stream()
                .mapToDouble(Represent::sum)
                .distinct()
                .toArray();          
    }  
    
    
    private static void printEnding() {
        System.out.println(
            """
                </table>
                
            </body>
            </html>
            """
        );
    }
    
    
    private static void showing(Consumer<List<Represent>> filling) {
        var result = new ArrayList<Represent>();    
        filling.accept(result);        
        Collections.sort(result);       
        result.forEach(e -> System.err.println(
                STR."\{e.msg()} \{SPACES} \{e.sum()} \{SPACES} \{e.getTotalWeight()}"
        ));
    }
    
    
    private static void showTitle(String title) {
        var line = "*".repeat(120);
        System.err.println(
            STR."""
            
            \{line}
            \{title}
            \{line}
            """
        );
    }
    
       
    private static void coloringRows(double currentValue, String info) {   
        if (currentValue != cachedValue) {
            switchCurrentColor();
        }          
        System.out.printf(info, currentColor);     
        cachedValue = currentValue;
    }
    
    
    private static void switchCurrentColor() { currentColor = Colors.getNextColor(currentColor); }
    
        
    private static void printContent(List<Represent> target, Consumer<Represent> func) {
        resetCachedValue();
        target.forEach(func);
    }
    
    
    private static void resetCachedValue() { cachedValue = Double.NEGATIVE_INFINITY; }
}