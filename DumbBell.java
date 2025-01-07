/* 
 * Написать много перегруженных конструкторов, а для создания объекта лучше написать билдер
 * Также написать конструктор по умолчанию
 * В конструкторах производить валидацию входных аргументов, проверив объекты сначала на null
 * Сделать возможность передачи в конструкторе таких параметров:
 *     вес грифа с замками;
 *     массив со всеми уникальными имеющимися парами дисков -- double[];
 *     мапу с уточнением сколько имеется пар для данного веса -- Map<Double, Integer>;
 *     возможно, количество сторон (1 или 2) -- enum SideAmount { ONE, TWO }; 
 *     возможно, количество дисков с каждой стороны по умолчанию -- enum DiscAmount { ONE, TWO, THREE, FOUR };
 *     массив с названиями цветов фона для строк таблицы с данными -- String[];
*/


import static java.util.Objects.requireNonNull;
import static java.util.stream.IntStream.range;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.EnumMap;

import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.BiConsumer;
import java.util.function.DoubleConsumer;
import java.util.function.Predicate;
import java.util.function.Function;

import java.util.stream.Collectors;
import java.util.stream.DoubleStream;


public class DumbBell {
                       
    public enum SideAmount { ONE, TWO }
    
      
    public enum DiscAmount { ONE, TWO, THREE, FOUR }
    
     
    public enum DefaultValues {
    
        _0_5(0.5),
        _0_75(0.75, 0),
        _1(1.0),
        _1_25(1.25),
        _1_5(1.5),
        _2(2.0),
        _2_5(2.5),
        _5(5.0);
        
        private double weight; 
        private int theSameDiskPairAmount;
    
    
        DefaultValues(double weight, int theSameDiskPairAmount) {
            this.weight = weight;
            this.theSameDiskPairAmount = theSameDiskPairAmount;
        }   
        
        
        DefaultValues(double weight) { 
            this.weight = weight; 
            this.theSameDiskPairAmount = 1;
        }
        
        
        public double getWeight() { return weight; }
        
        
        public int getTheSameDiskAmount() { return theSameDiskPairAmount; }
        
        
        public String toString() {
            return STR."""
                Вес диска: \{weight}
                Количество пар: \{theSameDiskPairAmount}
                """;
        }
    }
    
    
    
         
   public record Colors() { 
   
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
           
           
           DefaultColors(String colorName) { this.colorName = requireNonNull(colorName); }
           
           
           @Override
           public String toString() { return colorName; }
       }
       
       
       private static final String[] COLORS = DefaultColors.getColors();   
       private static final String FIRST_COLOR = Arrays.stream(COLORS).findFirst().get();
       private static final int FIRST_COLOR_ORDINAL = 0; 
       private static final int LAST_COLOR_ORDINAL = COLORS.length - 1;   
       private static final int STEP_COLOR_ORDINAL = 1;    
       
            
       public static String getFirstColor() { return FIRST_COLOR; }
       
       
       public static String getNextColor(String currentColor) {
           requireNonNull(currentColor);
           return COLORS[getNextColorOrdinal(Arrays.asList(COLORS).indexOf(currentColor))];
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
           return diskWeight*SIDE_AMOUNT + DEFAULT_GRIP_WEIGHT;
       }
      
       
       @Override
       public int compareTo(Represent another) { return COMPARATOR.compare(this, requireNonNull(another)); }
   }
   



    private enum FuncCategories { FILLING, SHOWING }

    
    
     
    private static final double[] DEFAULT_ALL_WEIGHTS = generateAllWeights(); 
    //private static final List<Represent> RESULTS = new ArrayList<>();
    private static final String SPACES = " ".repeat(23);
    private static final String OTHERS_STRICTLY_EQUAL_SHORT_PARAM_NAME = "-o";  
    private static final String OTHERS_STRICTLY_EQUAL_LONG_PARAM_NAME = "--one";
    private static final double DEFAULT_GRIP_WEIGHT = 1.5;
    private static final int SIDE_AMOUNT = 2;
    private static final int DISK_AMOUNT_BY_DEFAULT = 3;
    private static final int MIN_DISK_AMOUNT = 1;
    private static final int MAX_DISK_AMOUNT = 4;
    private static final int I_IDX = 0;
    private static final int J_IDX = 1;
    private static final int K_IDX = 2;
    private static final int N_IDX = 3;
    
        
    private final List<Represent> instanceResults = new ArrayList<>();
    private final double gripWeight;
    private final double[] uniquePairs;
    private final Map<Double, Integer> pairAmount;
    private final SideAmount sideAmount;
    private final DiscAmount discAmount;
    private final String[] backgroundRowColors;  
    private final Map<Integer, Map<FuncCategories, Runnable>> FUNC_CATEGORIES_BY_DISK_AMOUNT = Map.of(
            1, buildEnumMap(this::fillOneDisksResultPart, this::showOneDisksResultPart),
            2, buildEnumMap(this::fillTwoDiskComboResultPart, this::showTwoDiskComboResultPart), 
            3, buildEnumMap(this::fillThreeDiskComboResultPart, this::showThreeDiskComboResultPart),
            4, buildEnumMap(this::fillFourDiskComboResultPart, this::showFourDiskComboResultPart)
    );
    
     
    private double cachedValue = Double.NEGATIVE_INFINITY;
    private String currentColor = Colors.getFirstColor();
    private int diskAmountSetting = DISK_AMOUNT_BY_DEFAULT;
    private boolean isOthersStrictlyEqual = false;
    private int[] diskAmountNumbers = null;
    private String cachedRow = "";
    
               
    public static void main(String... args) {   
        var dumbBell = new DumbBell();
        
        dumbBell.setDiskAmount(args);        
        dumbBell.showInfo();
        separateOutputByLines();
        dumbBell.showBaseData();    
    }
    
    
    public DumbBell() {
        this(
                DEFAULT_GRIP_WEIGHT, 
                DEFAULT_ALL_WEIGHTS, 
                null, 
                SideAmount.TWO, 
                DiscAmount.THREE, 
                Colors.DefaultColors.getColors()
        );
    }
    
    
    public DumbBell(double gripWeight,
                    double[] uniquePairs,
                    Map<Double, Integer> pairAmount,
                    SideAmount sideAmount,
                    DiscAmount discAmount,
                    String[] backgroundRowColors) {
        this.gripWeight = validateGripWeight(gripWeight);
        this.uniquePairs = validateUniquePairs(uniquePairs);
        this.pairAmount = validatePairAmount(pairAmount);
        this.sideAmount = requireNonNull(sideAmount);
        this.discAmount = requireNonNull(discAmount);
        this.backgroundRowColors = validateBackgroundRowColors(backgroundRowColors);
   }
   
   
   private static double validateGripWeight(double gripWeight) {
       return gripWeight;
   }
   
   
   private static double[] validateUniquePairs(double[] uniquePairs) {
       requireNonNull(uniquePairs);
       return uniquePairs;
   }
   
   
   private static Map<Double, Integer> validatePairAmount(Map<Double, Integer> pairAmount) {
       return pairAmount;
   }
   
   
   private static String[] validateBackgroundRowColors(String[] backgroundRowColors) {
       requireNonNull(backgroundRowColors);
       return backgroundRowColors;
   }
    
    
    public static double[] getAllWeights() { return DEFAULT_ALL_WEIGHTS; }
    
    
    private void setDiskAmount(String[] args) {
        requireNonNull(args);
        
        if (args.length > 0) {
            try {
                diskAmountSetting = validateDiskAmountSetting(Integer.parseInt(args[0]));
                
                if (args.length > 1) {
                    if (isOthersStrictlyEqual(args)) {
                        isOthersStrictlyEqual = true;
                    } else {
                        isOthersStrictlyEqual = true;
                        diskAmountNumbers = getValidUniqueDiskAmountNumbers(args);
                    }
                }   
            } catch (IllegalArgumentException _) {}
        }
    }
    
    
    private static boolean isOthersStrictlyEqual(String[] args) {
        return (OTHERS_STRICTLY_EQUAL_LONG_PARAM_NAME.equals(args[1]) 
                || OTHERS_STRICTLY_EQUAL_SHORT_PARAM_NAME.equals(args[1]));
    }
    
    
    private static int validateDiskAmountSetting(int diskAmount) {
        if (! isDiskAmountBelongToValidRange(diskAmount)) {
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
        return (diskAmount >= MIN_DISK_AMOUNT) && (diskAmount <= MAX_DISK_AMOUNT);
    }
    
    
    private static int[] getValidUniqueDiskAmountNumbers(String[] args) {
        Predicate<String> isNumber = str -> {
            try {
                Integer.valueOf(str);
                return true;
            } catch (NumberFormatException _) {
                return false;
            }
        };
        
        return Arrays.stream(args)
                .filter(isNumber)
                .mapToInt(Integer::valueOf)
                .sorted()
                .distinct()
                .filter(DumbBell::isDiskAmountBelongToValidRange)
                .toArray();
    }
    
    
    private void showInfo() {
        runFuncsByCategory(FuncCategories.SHOWING);
    }
    
    
    public void showOneDisksResultPart() {
        showTitle("С одним диском:");
        resetCachedRow();
        processOneDisksResultPart(item -> {
                var currentRow = STR."\{item}\{SPACES}\{Represent.getTotalWeightByOneDisk(item)}";
                if (! cachedRow.equals(currentRow)) {
                    System.err.println(currentRow);
                }
                cachedRow = currentRow;
        });
    }
    
       
    public void showTwoDiskComboResultPart() {
        showing(DumbBell::fillTwoDiskComboResultPart, "С двумя дисками:");
    }
    
    
    public void showThreeDiskComboResultPart() {
        showing(DumbBell::fillThreeDiskComboResultPart, "С тремя дисками:");
    }
    
    
    public void showFourDiskComboResultPart() {
        showing(DumbBell::fillFourDiskComboResultPart, "С четырьмя дисками:");
    }
    
    
    private static void separateOutputByLines() {
        System.err.println();
        showTitle("");
        System.err.println();
    }
    
    
    private void showBaseData() {
        preparing();                   
        printBeginning();      
        printBaseData();            
        printAssymetricComboes(getUniqueOneSidedSums());      
        printEnding(); 
    }
    
      
    private void preparing() {          
        runFuncsByCategory(FuncCategories.FILLING);       
        Collections.sort(instanceResults); 
    }
    
    
    private void runFuncs(FuncCategories funcCategory) {
        requireNonNull(funcCategory);
               
        Arrays.stream(diskAmountNumbers)
                .mapToObj(FUNC_CATEGORIES_BY_DISK_AMOUNT::get)
                .map(funcsByCategory -> funcsByCategory.get(funcCategory))
                .forEach(Runnable::run);
    }
    
    
    private boolean hasDiskAmountNumbers() { return null != diskAmountNumbers; }
    
      
    private void runFuncs(Runnable... funcs) {
        requireNonNull(funcs);
        
        if (funcs.length != MAX_DISK_AMOUNT) {
            throw new IllegalArgumentException(
                    STR."""
                    Количество функций должно быть равно \{MAX_DISK_AMOUNT} !
                    Переданное количество функций: \{funcs.length} .
                    """
            );
        }
          
        runFuncIfDiskAmountPermit(MIN_DISK_AMOUNT, funcs[0], isOthersStrictlyEqual);
        runFuncIfDiskAmountPermit(MIN_DISK_AMOUNT + 1, funcs[1], isOthersStrictlyEqual);
        runFuncIfDiskAmountPermit(MAX_DISK_AMOUNT - 1, funcs[2], isOthersStrictlyEqual);
        runFuncIfDiskAmountPermit(MAX_DISK_AMOUNT, funcs[3], true);
    }
    
    
    private void runFuncIfDiskAmountPermit(int number, Runnable func) { 
        runFuncIfDiskAmountPermit(number, func, false); 
    }
    
    
    private void runFuncIfDiskAmountPermit(int number, Runnable func, boolean isStrictlyEqual) {
        requireNonNull(func);
               
        if (isStrictlyEqual(number, isStrictlyEqual)) {
            func.run();
        }    
    }
    
    
    private boolean isStrictlyEqual(int number, boolean isStrictlyEqual) {
        return isStrictlyEqual 
                ? (diskAmountSetting == number)
                : (diskAmountSetting >= number);
    }
    
     
    private void fillOneDisksResultPart() {       
        processOneDisksResultPart(item -> instanceResults.add(new Represent(item, STR."\{item}")));
    }
    
    
    private static void processOneDisksResultPart(DoubleConsumer func) {
        requireNonNull(func);
                
        Arrays.stream(DEFAULT_ALL_WEIGHTS).forEach(func::accept);
    }
    
    
    private void fillTwoDiskComboResultPart() { fillTwoDiskComboResultPart(instanceResults); }   
    
    
    private static void fillTwoDiskComboResultPart(List<Represent> represents) {  
        requireNonNull(represents);  
         
        fillTwoItems(DEFAULT_ALL_WEIGHTS, (iElement, jElement) -> represents.add(new Represent(
                iElement + jElement, 
                STR."\{iElement} + \{jElement}"
        )));
    }
    
    
    private static void fillTwoItems(double[] source, BiConsumer<Double, Double> func) {
        requireNonNull(source);
        requireNonNull(func);
              
        IntConsumer secondLoop = i -> 
                range(i + 1, source.length)
                .forEach(j -> func.accept(source[i], source[j]));
        
        range(0, source.length - 1).forEach(secondLoop);
    }
    
    
    private void fillThreeDiskComboResultPart() { fillThreeDiskComboResultPart(instanceResults); }
    
    
    private static void fillThreeDiskComboResultPart(List<Represent> represents) {
        requireNonNull(represents);
                
        Function<int[], Represent> toRepresent = iJK -> 
                new Represent(
                        DEFAULT_ALL_WEIGHTS[iJK[I_IDX]] + DEFAULT_ALL_WEIGHTS[iJK[J_IDX]] + DEFAULT_ALL_WEIGHTS[iJK[K_IDX]], 
                        STR."\{DEFAULT_ALL_WEIGHTS[iJK[I_IDX]]} + \{DEFAULT_ALL_WEIGHTS[iJK[J_IDX]]} + \{DEFAULT_ALL_WEIGHTS[iJK[K_IDX]]}"
                );
                                          
        BiConsumer<Integer, Integer> thirdLoop = (i, j) -> 
                range(j + 1, DEFAULT_ALL_WEIGHTS.length)
                .mapToObj(k -> new int[] { i, j, k })
                .map(toRepresent)
                .forEach(represents::add);
                
        IntConsumer secondLoop = i -> 
                range(i + 1, DEFAULT_ALL_WEIGHTS.length - 1)
                .forEach(j -> thirdLoop.accept(i, j));
                
        range(0, DEFAULT_ALL_WEIGHTS.length - 2).forEach(secondLoop);
    }
    
    
    private void fillFourDiskComboResultPart() { fillFourDiskComboResultPart(instanceResults); }      
        
      
    private static void fillFourDiskComboResultPart(List<Represent> represents) {
        requireNonNull(represents);
        
        Function<int[], Represent> toRepresent = iJKN -> 
                new Represent(
                        DEFAULT_ALL_WEIGHTS[iJKN[I_IDX]] + DEFAULT_ALL_WEIGHTS[iJKN[J_IDX]] + 
                        DEFAULT_ALL_WEIGHTS[iJKN[K_IDX]] + DEFAULT_ALL_WEIGHTS[iJKN[N_IDX]], 
                        
                        STR."""
                        \{DEFAULT_ALL_WEIGHTS[iJKN[I_IDX]]} + \{DEFAULT_ALL_WEIGHTS[iJKN[J_IDX]]} + \
                        \{DEFAULT_ALL_WEIGHTS[iJKN[K_IDX]]} + \{DEFAULT_ALL_WEIGHTS[iJKN[N_IDX]]}\
                        """
                );
                
        Consumer<int[]> fourthLoop = iJK ->
                range(iJK[K_IDX] + 1, DEFAULT_ALL_WEIGHTS.length)
                .mapToObj(n -> new int[] { iJK[I_IDX], iJK[J_IDX], iJK[K_IDX], n })
                .map(toRepresent)
                .forEach(represents::add);
                
        BiConsumer<Integer, Integer> thirdLoop = (i, j) ->
                range(j + 1, DEFAULT_ALL_WEIGHTS.length - 1)   
                .forEach(k -> fourthLoop.accept(new int[] { i, j, k }));    
                
        IntConsumer secondLoop = i ->
                range(i + 1, DEFAULT_ALL_WEIGHTS.length - 2)
                .forEach(j -> thirdLoop.accept(i, j));
                
        range(0, DEFAULT_ALL_WEIGHTS.length - 3).forEach(secondLoop);
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
                <h2 class="grif">Вес грифа с замками: \{DEFAULT_GRIP_WEIGHT} КГ</h2>
                <h2>Симметричные комбинации</h2>
                <table>
            """
        );
    }
    
    
    private void printBaseData() {
        System.out.println(
            """
                    <tr>
                        <th>Комбинация</th>
                        <th>Односторонняя сумма, КГ</th>
                        <th>Общий вес с грифом при двусторонней симметричной сумме, КГ</th>
                    </tr>
            """
        );
              
        printContent(instanceResults, e -> coloringRows(e.getTotalWeight(), 
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
    
    
    private void printAssymetricComboes(double[] resultSums) {       
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
                iElement + jElement + DEFAULT_GRIP_WEIGHT,
                STR."\{iElement} + \{jElement}"
        )));           
        Collections.sort(asymmetricCombo);    
        return asymmetricCombo;
    }
    
    
    private double[] getUniqueOneSidedSums() {
        return instanceResults.stream()
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
    
    
    private void showing(Consumer<List<Represent>> filling, String title) {
        requireNonNull(filling);
        
        showTitle(title);
        
        var result = new ArrayList<Represent>();    
        filling.accept(result);    
                    
        filteringContainer(result);
        
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
    
       
    private void coloringRows(double currentValue, String info) { 
        requireNonNull(info);
          
        if (currentValue != cachedValue) {
            switchCurrentColor();
        }  
                
        System.out.printf(info, currentColor);     
        cachedValue = currentValue;
    }
    
    
    private void switchCurrentColor() { currentColor = Colors.getNextColor(currentColor); }
    
        
    private void printContent(List<Represent> target, Consumer<Represent> func) {
        requireNonNull(target);
        requireNonNull(func);
        
        filteringContainer(target);
        
        resetCachedValue();
        target.forEach(func);
    }
    
    
    private void resetCachedValue() { cachedValue = Double.NEGATIVE_INFINITY; }
    
    
    private void resetCachedRow() { cachedRow = ""; }
    
    
    private static 
    Map<FuncCategories, Runnable> buildEnumMap(Runnable filler, Runnable shower) {
        requireNonNull(filler);
        requireNonNull(shower);
        
        return new EnumMap<FuncCategories, Runnable>(FuncCategories.class) {{ 
                put(FuncCategories.FILLING, filler); 
                put(FuncCategories.SHOWING, shower); 
        }};
    }
    
    
    private void runFuncsByCategory(FuncCategories category) {
        requireNonNull(category);
        
        if (hasDiskAmountNumbers()) {
            runFuncs(category);
        } else {
            var funcs = FUNC_CATEGORIES_BY_DISK_AMOUNT.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .map(Map.Entry::getValue)
                    .map(funcsByCategory -> funcsByCategory.get(category))
                    .toArray(Runnable[]::new);
                    
            runFuncs(funcs);
        }
    }
    
    
    private static double[] generateAllWeights() {
        return Arrays.stream(DefaultValues.values())
                .map(DumbBell::getTheSameDiskSequence)
                .flatMapToDouble(Arrays::stream)
                .toArray();
    }
    
    
    private static double[] getTheSameDiskSequence(DefaultValues values) {
        return DoubleStream.generate(values::getWeight)
                .limit(values.getTheSameDiskAmount())
                .toArray();
    }
    
    
    private void filteringContainer(List<Represent> represents) {
        Predicate<Represent> isRemoveMsg = e -> {
                var currentMsg = e.msg();
                var isRemoved = cachedRow.equals(currentMsg); 
                cachedRow = currentMsg;
                return isRemoved;
        };
        
        resetCachedRow();         
        represents.removeIf(isRemoveMsg);  
        resetCachedRow(); 
    }
}