// This is a snippet

class PrimeNumbersChecker {
    private static long firstBuff;
    private static long lastBuff;
    

    public static void printPrimeNumbers(long limit) {
        printPrimeNumbers(2, limit);
    }
    
    public static void printPrimeNumbers(long start, long limit) {
        validateArgs(start, limit);
        looping(start, limit, System.out::println);
    }

    public static List<Long> getPrimeNumbers(long limit) {
        return getPrimeNumbers(2, limit);
    }
    
    public static List<Long> getPrimeNumbers(long start, long limit) {
        validateArgs(start, limit);      
        List<Long> result = new ArrayList<>();       
        looping(start, limit, result::add);    
        return result;
    }
    
    private static void looping(long start, long limit, Consumer<Long> func) {   
        firstBuff = lastBuff = 0L;
           
        for (long i = start; i < limit + start; i++) {
            if (isPrimeNumber(i)) {
                //result.add(i);
                /*
                lastBuff = i;
                if (firstBuff != 0) {
                    long difference = lastBuff - firstBuff;
                    System.err.printf(
                            "first: %d | last: %d | difference: %d%n", 
                            firstBuff, 
                            lastBuff, 
                            difference
                    );
                } 
                long tmp = firstBuff;
                firstBuff = lastBuff;
                lastBuff = tmp;
                */
                func.accept(i);
            }
        }
    }
    
    private static boolean isPrimeNumber(long number) { 
        return new PrimeNumbers(number).getResult(); 
    }
        /*
        for (long i = 2; i < number; i += i == 2 ? 1 : 2) {
            if (number % i == 0) {
                return false;
            }
        }
        
        return true;
        
    } */

    private static void validateArgs(long start, long limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("Лимит не должен быть отрицательным!");
        }
        
        if (start < 2) {
            throw new IllegalArgumentException("Начальное значение должно быть не меньше 2!");
        }
    }

//for (int i = number - 1; i > 1; i--)
}