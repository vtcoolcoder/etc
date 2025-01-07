public final class Converter {

    private static final int SIZE = Short.MAX_VALUE - Short.MIN_VALUE;
    private static final BigInteger SIZE_BIG = BigInteger.valueOf(SIZE);
    
    private final String source;
    
    
    public static BigInteger convertToBigInteger(final String target) {
        Objects.requireNonNull(target);
        
        final int[] CONVERTED = target.chars().toArray();   
        var sum = BigInteger.ZERO;
        
        for (int i = CONVERTED.length - 1, n = 0; i >= 0; i--, n++) {
            var left = BigInteger.valueOf(CONVERTED[i]);
            var right = BigInteger.valueOf(SIZE).pow(n);
            sum = sum.add(left.multiply(right));
        }
       
        return sum;
    }
    
    
    public static String convertToString(final BigInteger target) {
        Objects.requireNonNull(target);
        
        var stack = new Stack<Integer>();
        
        for (var i = target; i.compareTo(BigInteger.ONE) >= 0; i = i.divide(SIZE_BIG)) {      
            stack.push(i.mod(SIZE_BIG).intValue());
        }
        
        char[] chars = new char[stack.size()];
        int idx = 0;
        
        while (! stack.isEmpty()) {
            chars[idx++] = (char) stack.pop().intValue();
        }
        
        return new String(chars);
    }
    
    
    public static String increment(final String target) {
        return runOperation(target, BigInteger::add);
    }
    
    
    public static String decrement(final String target) {
        return runOperation(target, BigInteger::subtract);
    }
    
    
    public static String sum(String first, String second) {
        return runOperation(first, second, BigInteger::add);
    }
    
    
    public static String subtract(String first, String second) {
        return runOperation(first, second, BigInteger::subtract);
    }
    
    
    public static String multiply(String first, String second) {
        return runOperation(first, second, BigInteger::multiply);
    }
    
    
    public static String divide(String first, String second) {
        return runOperation(first, second, BigInteger::divide);
    }
    
    
    public static String mod(String first, String second) {
        return runOperation(first, second, BigInteger::mod);
    }
    
    
    public static String pow(String target, int n) {
        return runOperation(target, n, BigInteger::pow);
    }
    
    
    // Можно зашифровать первую строку второй
    public static String xor(String first, String second) {
        return runOperation(first, second, BigInteger::xor);
    }
    
    
    public Converter() { this.source = ""; }
    
    public Converter(final String source) {
        this.source = Objects.requireNonNull(source);
    }
    
    public Converter increment() {
        return new Converter(increment(source));
    }
    
    public Converter decrement() {
        return new Converter(decrement(source));
    }
    
    public Converter sum(String another) {
        return new Converter(sum(source, another));
    }
    
    public Converter subtract(String another) {
        return new Converter(subtract(source, another));
    }
    
    public Converter multiply(String another) {
        return new Converter(multiply(source, another));
    }
    
    public Converter divide(String another) {
        return new Converter(divide(source, another));
    }
    
    public Converter mod(String another) {
        return new Converter(mod(source, another));
    }
    
    public Converter pow(int n) {
        return new Converter(pow(source, n));
    }
    
    public Converter xor(String another) {
        return new Converter(xor(source, another));
    }
    
    public BigInteger toBigInteger() {
        return convertToBigInteger(source);
    }
    
    @Override
    public String toString() { return source; }
    
    
    private static String runOperation(String target, BinaryOperator<BigInteger> operation) {
        Objects.requireNonNull(target);  
        Objects.requireNonNull(operation);     
        
        return convertToString(operation.apply(
                convertToBigInteger(target), BigInteger.ONE));    
    }
    
    
    private static String runOperation(
            String first, 
            String second, 
            BinaryOperator<BigInteger> operation
    ) {
        Objects.requireNonNull(first);
        Objects.requireNonNull(second);
        Objects.requireNonNull(operation);  
               
        return convertToString(operation.apply(
                convertToBigInteger(first), 
                convertToBigInteger(second)));   
    }
    
    
    private static String runOperation(
            String target, 
            int n, 
            BiFunction<BigInteger, Integer, BigInteger> action
    ) {
        Objects.requireNonNull(target);
        Objects.requireNonNull(action);  
        
        return convertToString(action.apply(
                convertToBigInteger(target), n));
    }
}