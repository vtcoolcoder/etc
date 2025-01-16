package dumbell;


import static dumbell.Constants.*;
import static java.util.Objects.requireNonNull;

import java.util.Map;


public class Validator {
    
   public double validateGripWeight(double gripWeight) {
       return gripWeight;
   }
   
   
   public double[] validateUniquePairs(double[] uniquePairs) {
       requireNonNull(uniquePairs);
       return uniquePairs;
   }
   
   
   public Map<Double, Integer> validatePairAmount(Map<Double, Integer> pairAmount) {
       return pairAmount;
   }
   
   
   public String[] validateBackgroundRowColors(String[] backgroundRowColors) {
       requireNonNull(backgroundRowColors);
       return backgroundRowColors;
   }
   
   
   public int validateDiskAmountSetting(int diskAmount) {
        if (! Predicates.isDiskAmountBelongToValidRange(diskAmount)) {
            throw new IllegalArgumentException(
                    STR."""
                    Количество дисков должно находиться в диапазоне [\{MIN_DISK_AMOUNT}..\{MAX_DISK_AMOUNT}] !
                    Переданное количество дисков: \{diskAmount} .
                    """
            );
        }
        
        return diskAmount;
    }
}