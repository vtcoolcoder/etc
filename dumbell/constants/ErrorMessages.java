package dumbell.constants;


public interface ErrorMessages extends DefaultGripData {

    static String getInvalidDiskAmountMessage(int diskAmount) {
        return STR."""
                Количество дисков должно находиться в диапазоне [\{MIN_DISK_AMOUNT}..\{MAX_DISK_AMOUNT}] !
                Переданное количество дисков: \{diskAmount} .
                """;
    }
    
    
    static String getInvalidFuncCountMessage(int funcsLength) {
        return STR."""
                Количество функций должно быть равно \{MAX_DISK_AMOUNT} !
                Переданное количество функций: \{funcsLength} .
                """;
    }
}