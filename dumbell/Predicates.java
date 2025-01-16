package dumbell;


import static dumbell.Constants.*;
import static java.util.Objects.requireNonNull;


public class Predicates {

    public boolean isOthersStrictlyEqual(String[] args) {
        requireNonNull(args);
        return (OTHERS_STRICTLY_EQUAL_LONG_PARAM_NAME.equals(args[1]) 
                || OTHERS_STRICTLY_EQUAL_SHORT_PARAM_NAME.equals(args[1]));
    }
    
       
    public boolean isDiskAmountBelongToValidRange(int diskAmount) {
        return (diskAmount >= MIN_DISK_AMOUNT) && (diskAmount <= MAX_DISK_AMOUNT);
    }
    
    
    public boolean hasDiskAmountNumbers(int[] diskAmountNumbers) { return null != diskAmountNumbers; }
    
    
    public boolean isStrictlyEqual(int number, boolean isStrictlyEqual, int diskAmountSetting) {
        return isStrictlyEqual 
                ? (diskAmountSetting == number)
                : (diskAmountSetting >= number);
    }
}