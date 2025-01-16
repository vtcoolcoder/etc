package dumbell;


import static dumbell.Constants.*;
import static dumbell.DumbBell.Represent;
import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;
import java.util.function.DoubleConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.Function; 
import java.util.function.BiFunction; 
import java.util.function.IntFunction; 
import java.util.stream.DoubleStream;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.EnumMap;
import java.util.Optional;


public class Service {

    private final Validator validator = new Validator();
    private final Predicates predicates = new Predicates();
    
        
    public int[] getValidUniqueDiskAmountNumbers(String[] args) {
        requireNonNull(args);
        
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
                .filter(predicates::isDiskAmountBelongToValidRange)
                .toArray();
    }
    
        
    public void processOneDisksResultPart(DoubleConsumer func) {
        requireNonNull(func);        
        Arrays.stream(DEFAULT_ALL_WEIGHTS).forEach(func::accept);
    }
    
    
    public Map<FuncCategories, Runnable> buildEnumMap(Runnable filler, Runnable shower) {
        requireNonNull(filler);
        requireNonNull(shower);
        
        return new EnumMap<>(FuncCategories.class) {{ 
                put(FuncCategories.FILLING, filler); 
                put(FuncCategories.SHOWING, shower); 
        }};
    }
    
        
    public void filteringContainer(List<Represent> represents,
                                   DumbBell dumbBell,
                                   Runnable action) {
        requireNonNull(represents);
        requireNonNull(dumbBell);
        requireNonNull(action);
        
        Predicate<Represent> isRemoveMsg = e -> {
                var currentMsg = e.msg();
                var isRemoved = dumbBell.cachedRow.equals(currentMsg); 
                dumbBell.cachedRow = currentMsg;
                return isRemoved;
        };
        
        action.run();        
        represents.removeIf(isRemoveMsg);  
        action.run();
    }
    
    
    public Optional<SetDiskAmountReturned> setDiskAmount(String[] args) {
        requireNonNull(args);
        
        SetDiskAmountReturned result = null;
                    
        if (args.length > 0) {
            result = getResultIfExist(args);
        }
        
        return Optional.ofNullable(result);
    }
    
    
    private static SetDiskAmountReturned getResultIfExist(String[] args) {
        IntFunction<SetDiskAmountReturned> ifBranchIntFunc = diskAmount -> 
                new SetDiskAmountReturned(diskAmount, true, null);
                
        IntFunction<SetDiskAmountReturned> elseBranchIntFunc = diskAmount -> 
                new SetDiskAmountReturned(diskAmount, true, getValidUniqueDiskAmountNumbers(args));
                
        Supplier<SetDiskAmountReturned> defaultReturnedSupplier = () -> 
                new SetDiskAmountReturned(DISK_AMOUNT_BY_DEFAULT, false, null);
              
        BiFunction<String[], Integer, SetDiskAmountReturned> 
        getDetailInfo = (params, diskAmount) -> params.length > 1        
                ? (predicates.isOthersStrictlyEqual(params)
                        ? ifBranchIntFunc.apply(diskAmount)
                        : elseBranchIntFunc.apply(diskAmount))
                : defaultReturnedSupplier.get();
             
        BiFunction<
                Function<String[], SetDiskAmountReturned>,
                Supplier<SetDiskAmountReturned>,
                Function<String[], SetDiskAmountReturned>
        > tryCatchWrapping = (ifBranch, elseBranch) -> params -> {
                try {
                    return ifBranch.apply(params);
                } catch (IllegalArgumentException _) {
                    return elseBranch.get();
                }
        };
             
        Function<String[], SetDiskAmountReturned> ifBranchFunc = params -> {
                var parsed = Integer.parseInt(params[0]);
                var diskAmount = validator.validateDiskAmountSetting(parsed);
                return getDetailInfo.apply(params, diskAmount);
        };
        
        return tryCatchWrapping.apply(ifBranchFunc, defaultReturnedSupplier).apply(args);
    }
    
    
    static double[] getUniqueOneSidedSums(List<Represent> target) {
        return target.stream()
                .mapToDouble(Represent::sum)
                .distinct()
                .toArray();          
    } 
}