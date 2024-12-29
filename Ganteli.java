import static java.util.Objects.requireNonNull;

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
       
       
       Colors(String colorName) { this.colorName = requireNonNull(colorName); }
       
       
       public static Colors getFirstColor() { return FIRST_COLOR; }
       
       
       public static Colors getNextColor(Colors currentColor) {
           return COLORS[getNextColorOrdinal(requireNonNull(currentColor).ordinal())];
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
       
       
       public Represent { requireNonNull(msg); }
       
       
       public double getTotalWeight() { return getTotalWeightByOneDisk(sum); }
       
       
       public static double getTotalWeightByOneDisk(double diskWeight) {
           return diskWeight*SIDE_AMOUNT + GRIF_WEIGHT;
       }
      
       
       @Override
       public int compareTo(Represent another) { return COMPARATOR.compare(this, requireNonNull(another)); }
   }
   


    
    private static final double[] VALUES = Arrays.stream(values()).mapToDouble(Ganteli::getWeight).toArray();  
    private static final List<Represent> RESULTS = new ArrayList<>();
    private static final String SPACES = " ".repeat(23);
    private static final double GRIF_WEIGHT = 1.5;
    private static final int SIDE_AMOUNT = 2;
    private static final int DISK_AMOUNT_BY_DEFAULT = 3;
    private static final int MIN_DISK_AMOUNT = 1;
    private static final int MAX_DISK_AMOUNT = 4;
    
    
    private static double cachedValue = Double.NEGATIVE_INFINITY;
    private static Colors currentColor = Colors.getFirstColor();
    private static int diskAmountSetting = DISK_AMOUNT_BY_DEFAULT;
    
    
    private double weight; 
    
       
    Ganteli(double weight) { this.weight = weight; }
    
    
    public double getWeight() { return weight; }
    
    
    public String toString() {
        return STR."\{weight}";
    }
    
       
    public static void main(String[] args) {   
        setDiskAmount(args);        
        showInfo();
        separateOutputByLines();
        showBaseData();    
    }
    
    
    private static void setDiskAmount(String[] args) {
        requireNonNull(args);
        
        if (args.length > 0) {
            try {
                diskAmountSetting = validateDiskAmountSetting(Integer.parseInt(args[0]));
            } catch (IllegalArgumentException _) {}
        }
    }
    
    
    private static int validateDiskAmountSetting(int diskAmount) {
        if (isDiskAmountBelongToValidRange(diskAmount)) {
            throw new IllegalArgumentException(
                    STR."""
                    Количество дисков должно находиться в диапазоне [\{MIN_DISK_AMOUNT}..\{MAX_DISK_AMOUNT}] !
                    Переданное количество дисков: \{diskAmount} .
                    """
            );
        }
        
        return diskAmount;
    }
    
    
    private static boolean isDiskAmountBelongToValidRange(int diskAmount) {
        return (diskAmount > MAX_DISK_AMOUNT) || (diskAmount < MIN_DISK_AMOUNT);
    }
    
    
    private static void showInfo() {
        runFuncs(
                Ganteli::showOneDisksResultPart,
                Ganteli::showTwoDiskComboResultPart,
                Ganteli::showThreeDiskComboResultPart,
                Ganteli::showFourDiskComboResultPart
        );
    }
    
    
    public static void showOneDisksResultPart() {
        showTitle("С одним диском:");
        processOneDisksResultPart(item -> System.err.println(
                STR."\{item}\{SPACES}\{Represent.getTotalWeightByOneDisk(item)}"
        ));
    }
    
       
    public static void showTwoDiskComboResultPart() {
        showing(Ganteli::fillTwoDiskComboResultPart, "С двумя дисками:");
    }
    
    
    public static void showThreeDiskComboResultPart() {
        showing(Ganteli::fillThreeDiskComboResultPart, "С тремя дисками:");
    }
    
    
    public static void showFourDiskComboResultPart() {
        showing(Ganteli::fillFourDiskComboResultPart, "С четырьмя дисками:");
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
        runFuncs(
                Ganteli::fillOneDisksResultPart,
                Ganteli::fillTwoDiskComboResultPart,
                Ganteli::fillThreeDiskComboResultPart,
                Ganteli::fillFourDiskComboResultPart
        );
        
        Collections.sort(RESULTS); 
    }
    
    
    private static void runFuncs(Runnable... funcs) {
        requireNonNull(funcs);
        
        if (funcs.length != MAX_DISK_AMOUNT) {
            throw new IllegalArgumentException(
                    STR."""
                    Количество функций должно быть равно \{MAX_DISK_AMOUNT} !
                    Переданное количество функций: \{funcs.length} .
                    """
            );
        }
        
        runFuncIfDiskAmountPermit(MIN_DISK_AMOUNT, funcs[0]);
        runFuncIfDiskAmountPermit(MIN_DISK_AMOUNT + 1, funcs[1]);
        runFuncIfDiskAmountPermit(MAX_DISK_AMOUNT - 1, funcs[2]);
        runFuncIfDiskAmountPermit(MAX_DISK_AMOUNT, funcs[3], true);
    }
    
    
    private static void runFuncIfDiskAmountPermit(int number, Runnable func) { 
        runFuncIfDiskAmountPermit(number, func, false); 
    }
    
    
    private static void runFuncIfDiskAmountPermit(int number, Runnable func, boolean isStrictlyEqual) {
        requireNonNull(func);
        
        if (isStrictlyEqual(number, isStrictlyEqual)) {
            func.run();
        }
    }
    
    
    private static boolean isStrictlyEqual(int number, boolean isStrictlyEqual) {
        return isStrictlyEqual 
                ? (diskAmountSetting == number)
                : (diskAmountSetting >= number);
    }
    
     
    private static void fillOneDisksResultPart() {       
        processOneDisksResultPart(item -> RESULTS.add(new Represent(item, STR."\{item}")));
    }
    
    
    private static void processOneDisksResultPart(DoubleConsumer func) {
        requireNonNull(func);
        
        for (int i = 0; i < VALUES.length; ++i) {
            func.accept(VALUES[i]);
        }      
    }
    
    
    private static void fillTwoDiskComboResultPart() { fillTwoDiskComboResultPart(RESULTS); }   
    
    
    private static void fillTwoDiskComboResultPart(List<Represent> represents) {  
        requireNonNull(represents);  
         
        fillTwoItems(VALUES, (iElement, jElement) -> represents.add(new Represent(
                iElement + jElement, 
                STR."\{iElement} + \{jElement}"
        )));
    }
    
    
    private static void fillTwoItems(double[] source, BiConsumer<Double, Double> func) {
        requireNonNull(source);
        requireNonNull(func);
        
        for (int i = 0; i < source.length - 1; ++i) {
            for (int j = i + 1; j < source.length; ++j) {
                func.accept(source[i], source[j]);
            }
        }
    }
    
    
    private static void fillThreeDiskComboResultPart() { fillThreeDiskComboResultPart(RESULTS); }
    
    
    private static void fillThreeDiskComboResultPart(List<Represent> represents) {
        requireNonNull(represents);
        
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
        requireNonNull(represents);
        
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
                        margin: 0 auto;
                        border-width: 1;
                        border-color: snow;
                        border-style: solid;
                        border-radius: 7px;
                        font-weight: bold;
                    }
                    
                    th {
                        color: snow;
                        border-width: 1px;
                        border-style: solid;
                        border-radius: 7px;
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
    
    
    private static void showing(Consumer<List<Represent>> filling, String title) {
        requireNonNull(filling);
        
        showTitle(title);
        
        var result = new ArrayList<Represent>();    
        filling.accept(result);        
        Collections.sort(result);       
        result.forEach(e -> System.err.println(
                STR."\{e.msg()} \{SPACES} \{e.sum()} \{SPACES} \{e.getTotalWeight()}"
        ));
    }
    
    
    private static void showTitle(String title) {
        requireNonNull(title);
        
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
        requireNonNull(info);
          
        if (currentValue != cachedValue) {
            switchCurrentColor();
        }  
                
        System.out.printf(info, currentColor);     
        cachedValue = currentValue;
    }
    
    
    private static void switchCurrentColor() { currentColor = Colors.getNextColor(currentColor); }
    
        
    private static void printContent(List<Represent> target, Consumer<Represent> func) {
        requireNonNull(target);
        requireNonNull(func);
        
        resetCachedValue();
        target.forEach(func);
    }
    
    
    private static void resetCachedValue() { cachedValue = Double.NEGATIVE_INFINITY; }
}