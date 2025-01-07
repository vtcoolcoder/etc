package dumbell;


import static dumbell.Constants.*;
import static dumbell.Service.buildEnumMap;
import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Consumer;


class DumbBell {

    record Represent(double sum, String msg) implements Comparable<Represent> {
   
       private static final Comparator<Represent> COMPARATOR = Comparator.comparingDouble(Represent::sum);
          
       Represent { requireNonNull(msg); }
       
       double getTotalWeight() { return getTotalWeightByOneDisk(sum); }
       
       static double getTotalWeightByOneDisk(double diskWeight) {
           return diskWeight*sideAmount.getSideAmount() + gripWeight;
       }
      
       @Override
       public int compareTo(Represent another) { return COMPARATOR.compare(this, requireNonNull(another)); }
    }
    
    
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
    
    final Colors colors;
     
      
    private String currentColor;
    private int diskAmountSetting = DISK_AMOUNT_BY_DEFAULT;
    private boolean isOthersStrictlyEqual = false;
    private int[] diskAmountNumbers = null;
    
       
    String cachedRow = "";
    double cachedValue = Double.NEGATIVE_INFINITY;
    
    
    DumbBell() {
        this(
                DEFAULT_GRIP_WEIGHT, 
                DEFAULT_ALL_WEIGHTS, 
                null, 
                SideAmount.TWO, 
                DiscAmount.THREE, 
                DefaultColors.getColors()
        );
    }
    
    
    DumbBell(double gripWeight,
             double[] uniquePairs,
             Map<Double, Integer> pairAmount,
             SideAmount sideAmount,
             DiscAmount discAmount,
             String[] backgroundRowColors) {
        this.gripWeight = Validator.validateGripWeight(gripWeight);
        this.uniquePairs = Validator.validateUniquePairs(uniquePairs);
        this.pairAmount = Validator.validatePairAmount(pairAmount);
        this.sideAmount = requireNonNull(sideAmount);
        this.discAmount = requireNonNull(discAmount);
        this.backgroundRowColors = Validator.validateBackgroundRowColors(backgroundRowColors);
        this.colors = new Colors();
        this.currentColor = this.colors.getFirstColor();
   }
   
   
   void setDiskAmount(String[] args) {
        var optionalSettings = Service.setDiskAmount(args);
        
        if (optionalSettings.isPresent()) {
            var settings = optionalSettings.get();
            diskAmountSetting = settings.diskAmountSetting();
            isOthersStrictlyEqual = settings.isOthersStrictlyEqual();
            diskAmountNumbers = settings.diskAmountNumbers();
        }
    }
    
        
    void showInfo() {
        runFuncsByCategory(FuncCategories.SHOWING);
    }
    
    
    public void showOneDisksResultPart() {
        Shower.showTitle("С одним диском:");
        resetCachedRow();
        Service.processOneDisksResultPart(item -> {
                var currentRow = STR."\{item}\{SPACES}\{Represent.getTotalWeightByOneDisk(item)}";
                if (! cachedRow.equals(currentRow)) {
                    System.err.println(currentRow);
                }
                cachedRow = currentRow;
        });
    }
    
       
    public void showTwoDiskComboResultPart() {
        showing(this::fillTwoDiskComboResultPart, "С двумя дисками:");
    }
    
    
    public void showThreeDiskComboResultPart() {
        showing(this::fillThreeDiskComboResultPart, "С тремя дисками:");
    }
    
    
    public void showFourDiskComboResultPart() {
        showing(this::fillFourDiskComboResultPart, "С четырьмя дисками:");
    }
    
       
    void showBaseData() {
        preparing();                   
        Printer.printBeginning(this);      
        printBaseData();            
        printAssymetricComboes(getUniqueOneSidedSums());      
        Printer.printEnding(); 
    }
    
      
    private void preparing() {          
        runFuncsByCategory(FuncCategories.FILLING);       
        Collections.sort(instanceResults); 
    }
    
    
    private void runFuncs(FuncCategories funcCategory) {
        FuncRunner.runFuncs(funcCategory, FUNC_CATEGORIES_BY_DISK_AMOUNT, diskAmountNumbers);
    }
    
      
    private boolean hasDiskAmountNumbers() { 
        return Predicates.hasDiskAmountNumbers(diskAmountNumbers); 
    }
    
    
    private void runFuncs(Runnable... funcs) {
        FuncRunner.runFuncs(diskAmountSetting, isOthersStrictlyEqual, funcs);
    }
      
    
    private boolean isStrictlyEqual(int number, boolean isStrictlyEqual) { 
        return Predicates.isStrictlyEqual(number, isStrictlyEqual, diskAmountSetting);
    }
    
     
    private void fillOneDisksResultPart() {       
        Service.processOneDisksResultPart(item -> instanceResults.add(new Represent(item, STR."\{item}")));
    }
    
    
    private void fillTwoDiskComboResultPart() { fillTwoDiskComboResultPart(instanceResults); }
    
       
    private void fillTwoDiskComboResultPart(List<Represent> results) { 
        Filler.fillTwoDiskComboResultPart(results); 
    }   
    
    
    private void fillThreeDiskComboResultPart() { fillThreeDiskComboResultPart(instanceResults); }
    
        
    private void fillThreeDiskComboResultPart(List<Represent> results) { 
        Filler.fillThreeDiskComboResultPart(results); 
    }
    
    
    private void fillFourDiskComboResultPart() { fillFourDiskComboResultPart(instanceResults); }
    
      
    private void fillFourDiskComboResultPart(List<Represent> results) { 
        Filler.fillFourDiskComboResultPart(results); 
    }      
        
           
    private void printBaseData() {             
        printContent(
                instanceResults, 
                getConsumerByMode(PrintMode.BASE)
        );
    }
    
    
    private void printAssymetricComboes(double[] resultSums) {       
        Printer.printAssymetricComboPrefix();
                           
        printContent(
                Filler.getFilledAsymmetricComboContainer(resultSums), 
                getConsumerByMode(PrintMode.ASYMMETRIC)
        );
    }  
    
    
    private Consumer<Represent> getConsumerByMode(PrintMode mode) {
        return switch (mode) {
            case BASE -> e -> coloringRows(
                    e.getTotalWeight(), 
                    BASE_TR.formatted(e.msg(), e.sum(), e.getTotalWeight())
            );
            
            case ASYMMETRIC -> e -> coloringRows(
                    e.sum(), 
                    ASYMMETRIC_TR.formatted(e.msg(), e.sum())
            );
        };        
    }
   
    
    private double[] getUniqueOneSidedSums() {
        return Service.getUniqueOneSidedSums(instanceResults);
    }  
    
     
    private void showing(Consumer<List<Represent>> filling, String title) {  
        Shower.showing(filling, title, this::filteringContainer);
    }
    
    
    private void coloringRows(double currentValue, String info) {   
        Printer.coloringRows(currentValue, info, this, currentColor, this::switchCurrentColor);
    }
  
                    
    private void printContent(List<Represent> target, Consumer<Represent> func) {
        Printer.printContent(target, func, this::filteringContainer, this::resetCachedValue);
    }     
   
   
   private void switchCurrentColor() { currentColor = colors.getNextColor(currentColor); }
   
   
   private void resetCachedValue() { cachedValue = Double.NEGATIVE_INFINITY; }
    
       
   private void resetCachedRow() { cachedRow = ""; }
   
   
   private void filteringContainer(List<Represent> represents) {
        Service.filteringContainer(represents, this, this::resetCachedRow);
   }
   
   
    private void runFuncsByCategory(FuncCategories category) {
        FuncRunner.runFuncsByCategory(
                FUNC_CATEGORIES_BY_DISK_AMOUNT, 
                category, 
                this::runFuncs, 
                this::runFuncs, 
                diskAmountNumbers
         );
     }  
}