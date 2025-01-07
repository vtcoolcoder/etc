package dumbell;


import static dumbell.Constants.*;
import static java.util.Objects.requireNonNull;

import java.util.Map;


class Validator {
    
   static double validateGripWeight(double gripWeight) {
       return gripWeight;
   }
   
   
   static double[] validateUniquePairs(double[] uniquePairs) {
       requireNonNull(uniquePairs);
       return uniquePairs;
   }
   
   
   static Map<Double, Integer> validatePairAmount(Map<Double, Integer> pairAmount) {
       return pairAmount;
   }
   
   
   static String[] validateBackgroundRowColors(String[] backgroundRowColors) {
       requireNonNull(backgroundRowColors);
       return backgroundRowColors;
   }
   
   
   static int validateDiskAmountSetting(int diskAmount) {
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