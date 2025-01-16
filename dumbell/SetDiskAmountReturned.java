package dumbell;


public record SetDiskAmountReturned(int diskAmountSetting,
                                    boolean isOthersStrictlyEqual,
                                    int[] diskAmountNumbers) {}