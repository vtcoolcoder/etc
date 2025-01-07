package dumbell;


import static dumbell.Constants.*;
import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Consumer;


class FuncRunner {

    static void runFuncs(FuncCategories funcCategory, 
                         Map<Integer, Map<FuncCategories, Runnable>> source,
                         int[] diskAmountNumbers) {
        requireNonNull(funcCategory);
        requireNonNull(source);
        requireNonNull(diskAmountNumbers);
               
        Arrays.stream(diskAmountNumbers)
                .mapToObj(source::get)
                .map(funcsByCategory -> funcsByCategory.get(funcCategory))
                .forEach(Runnable::run);
    }
    
    
    static void runFuncs(int diskAmountSetting, boolean isOthersStrictlyEqual, Runnable... funcs) {
        requireNonNull(funcs);
        
        if (funcs.length != MAX_DISK_AMOUNT) {
            throw new IllegalArgumentException(
                    STR."""
                    Количество функций должно быть равно \{MAX_DISK_AMOUNT} !
                    Переданное количество функций: \{funcs.length} .
                    """
            );
        }
          
        runFuncIfDiskAmountPermit(MIN_DISK_AMOUNT, funcs[0], isOthersStrictlyEqual, diskAmountSetting);
        runFuncIfDiskAmountPermit(MIN_DISK_AMOUNT + 1, funcs[1], isOthersStrictlyEqual, diskAmountSetting);
        runFuncIfDiskAmountPermit(MAX_DISK_AMOUNT - 1, funcs[2], isOthersStrictlyEqual, diskAmountSetting);
        runFuncIfDiskAmountPermit(MAX_DISK_AMOUNT, funcs[3], true, diskAmountSetting);
    }
    
    
    static void runFuncIfDiskAmountPermit(int number, Runnable func, int diskAmountSetting) { 
        runFuncIfDiskAmountPermit(number, func, false, diskAmountSetting); 
    }
    
    
    static void runFuncIfDiskAmountPermit(int number, 
                                          Runnable func, 
                                          boolean isStrictlyEqual,
                                          int diskAmountSetting) {
        requireNonNull(func);
               
        if (Predicates.isStrictlyEqual(number, isStrictlyEqual, diskAmountSetting)) {
            func.run();
        }    
    } 
    
    
    static void runFuncsByCategory(Map<Integer, Map<FuncCategories, Runnable>> funcSource,
                                   FuncCategories category,
                                   Consumer<FuncCategories> actionThen,
                                   Consumer<Runnable[]> actionElse,
                                   int[] diskAmountNumbers) {
        requireNonNull(funcSource);                           
        requireNonNull(category);
        requireNonNull(actionThen);
        requireNonNull(actionElse);
        requireNonNull(diskAmountNumbers);
        
        if (Predicates.hasDiskAmountNumbers(diskAmountNumbers)) {
            actionThen.accept(category);
        } else {
            var funcs = funcSource.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .map(Map.Entry::getValue)
                    .map(funcsByCategory -> funcsByCategory.get(category))
                    .toArray(Runnable[]::new);
                    
            actionElse.accept(funcs);
        }
    }     
}