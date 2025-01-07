// This is a snippet

import static java.util.Map.entry;

import java.util.Map;
import java.util.Objects;
import java.util.Arrays;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;


public class ArithmeticRussianString {

    private record Fraction(BigInteger integer, BigInteger remainder) {
        public Fraction {
            Objects.requireNonNull(integer);
            Objects.requireNonNull(remainder);
        }
    }

    private record ComplexInfo(BigInteger number, int nLeadingZeroes, Fraction fraction) {       
        public ComplexInfo(BigInteger number, int nLeadingZeroes) {
            this(number, nLeadingZeroes, null);
        }
        
        public ComplexInfo(Fraction fraction, int nLeadingZeroes) {
            this(null, nLeadingZeroes, fraction);
        }
    }

    private static final LinkedHashSet<Character> LOWER_SET;
    private static final LinkedHashSet<Character> UPPER_SET;
    private static final LinkedHashSet<Character> FULL_SET;
    
    static {
        LOWER_SET = new LinkedHashSet<>();
        UPPER_SET = new LinkedHashSet<>();
        
        IntPredicate hasNext = e -> (e >= 'а' && e <= 'е') 
                || (e == 'ё') 
                || (e >= 'ж' && e <= 'я');
        
        IntUnaryOperator next = n -> n == 'е' 
                ? n + 28 
                : n == 'ё'
                        ? n - 27
                        : n + 1;
        
        IntStream.iterate('а', hasNext, next)
                 .forEach(symbol -> {
                     LOWER_SET.add((char) symbol);
                     UPPER_SET.add((char) (symbol - ((char) symbol == 'ё' ? 80 : 32)));
                 });
             
        FULL_SET = new LinkedHashSet<>(LOWER_SET);
        FULL_SET.addAll(UPPER_SET);
    }
    
    public static final ArithmeticRussianString LOWER = new ArithmeticRussianString(LOWER_SET);
    public static final ArithmeticRussianString UPPER = new ArithmeticRussianString(UPPER_SET);
    public static final ArithmeticRussianString FULL = new ArithmeticRussianString(FULL_SET);

    private final LinkedHashSet<Character> symbols;
    private final int size;
    private final char minValue;
    private final BigInteger bigIntSize; 
    private final String regexp;
    private final Map<Character, Integer> charToIntMapper;
    private final Map<Integer, Character> intToCharMapper;
    
    private int counter;
    
    
    public ArithmeticRussianString(final LinkedHashSet<Character> symbols) {
        this.symbols = Objects.requireNonNull(symbols);
        
        if ((size = symbols.size()) == 0) {
            throw new IllegalArgumentException("Размер множества не может быть нулевым!");
        }
        
        regexp = symbols.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("", "[", "]+"));   
                    
        minValue = symbols.iterator().next();
        bigIntSize = BigInteger.valueOf(size);
        charToIntMapper = new HashMap<>();
        intToCharMapper = new HashMap<>();
        initMappers();
    }
    
    private void initMappers() {
        symbols.forEach(e -> {
            charToIntMapper.put(e, counter);
            intToCharMapper.put(counter++, e);   
        });
    }

       
    public void test(final String word) {
        validateWord(word);
        
        ComplexInfo tmp = null;     
        System.out.println(tmp = convertWordToNumber(word));
        System.out.println(convertNumberToWord(tmp));
    }
    
    
    public String subtract(final String minuend, final String subtrahend) {
        validateWord(minuend);
        validateWord(subtrahend);
        
        var minuendComplex = convertWordToNumber(minuend);
        var subtrahendComplex = convertWordToNumber(subtrahend);
        
        var minuendNumber = minuendComplex.number();
        var subtrahendNumber = subtrahendComplex.number();
        
        var minuendNZeroes = minuendComplex.nLeadingZeroes();
        var subtrahendNZeroes = subtrahendComplex.nLeadingZeroes();
        
        return convertNumberToWord(new ComplexInfo(
                minuendNumber.subtract(subtrahendNumber), 
                Integer.max(minuendNZeroes, subtrahendNZeroes)));
    }
    
    
    public String divide(final String dividend, final String divisor) {
        validateWord(dividend);
        validateWord(divisor);
        
        var dividendComplex = convertWordToNumber(dividend);
        var divisorComplex = convertWordToNumber(divisor);
        
        var dividendNumber = dividendComplex.number();
        var divisorNumber = divisorComplex.number();
        
        var dividendNZeroes = dividendComplex.nLeadingZeroes();
        var divisorNZeroes = divisorComplex.nLeadingZeroes();
        
        var integer = dividendNumber.divide(divisorNumber);
        var remainder = dividendNumber.mod(divisorNumber);       
        
        return BigInteger.ZERO.equals(remainder)
                ? convertNumberToWord(new ComplexInfo(
                        integer,
                        Integer.min(dividendNZeroes, divisorNZeroes)
                ))
                
                : convertNumberToWord(new ComplexInfo(
                        new Fraction(integer, remainder),
                        Integer.min(dividendNZeroes, divisorNZeroes)
                ));
    }
    
       
    public String sum(final String... words) {
        Objects.requireNonNull(words);
        
        return convertNumberToWord(getReducingResult(words, new ComplexInfo(BigInteger.ZERO, 0), 
                selectComplexInfoAction(BigInteger::add)));              
    }
    
    
    public String multiply(final String... words) {
        Objects.requireNonNull(words);
            
        return convertNumberToWord(getReducingResult(
                words, 
                new ComplexInfo(BigInteger.ONE, 0), 
                selectComplexInfoAction(BigInteger::multiply)
        ));       
    }
    
    
    private ComplexInfo getReducingResult(
            final String[] words, 
            final ComplexInfo accumulator, 
            final BinaryOperator<ComplexInfo> reducing
    ) {
        Objects.requireNonNull(words);
        Objects.requireNonNull(accumulator);
        Objects.requireNonNull(reducing);
        
        return Arrays.stream(words)
                     .peek(this::validateWord)
                     .map(this::convertWordToNumber)
                     .reduce(accumulator, reducing);
    }
    
    
    private ComplexInfo convertWordToNumber(final String word) {
        validateWord(word);
        
        Predicate<String> isFraction = 
                str -> str.matches("%s\\(%s\\)".formatted(regexp, regexp));
                
        if (isFraction.test(word)) {
            var quotient = word.split("[\\(,\\)]");
            
            var integer = quotient[0];
            var remainder = quotient[1];
            
            var integerComplex = convertWordToNumber(integer);
            var remainderComplex = convertWordToNumber(remainder);
            
            return new ComplexInfo(
                    new Fraction(integerComplex.number(), remainderComplex.number()), 
                    Integer.min(integerComplex.nLeadingZeroes(), remainderComplex.nLeadingZeroes())
            );
        } else {
        
            final int[] CONVERTED = new int[word.length()];  
            
            int j = 0;
            for (var symbol : word.toCharArray()) {
                CONVERTED[j++] = charToIntMapper.get(symbol);
            }
            
            //System.err.println(Arrays.toString(CONVERTED));
            
            var sum = BigInteger.ZERO;
            
            for (int i = CONVERTED.length - 1, n = 0; i >= 0; i--, n++) {
                var left = BigInteger.valueOf(CONVERTED[i]);
                //System.err.println("left: " + left);
                var right = BigInteger.valueOf(size).pow(n);
                //System.err.println("right: " + right);
                sum = sum.add(left.multiply(right));
                //System.err.println("sum: " + sum);
            }
           
            return new ComplexInfo(sum, getLeadingZeroesCount(word));
        }
    }
    
    
    private String convertNumberToWord(final ComplexInfo complexInfo) {
        Objects.requireNonNull(complexInfo);
        String result = null;
        
        if (complexInfo.number() != null) {
            var stack = new Stack<Integer>();
            
            for (var i = complexInfo.number(); i.compareTo(BigInteger.ONE) >= 0; i = i.divide(bigIntSize)) {      
                stack.push(i.mod(bigIntSize).intValue());
            }
            
            char[] chars = new char[stack.size()];
            int idx = 0;
            
            while (! stack.isEmpty()) {
                chars[idx++] = intToCharMapper.get(stack.pop().intValue());
            }
            
            result = ("" + intToCharMapper.get(0)).repeat(complexInfo.nLeadingZeroes()) + new String(chars);
        } else if (complexInfo.fraction() != null) {
            var fraction = complexInfo.fraction();
                      
            var integerPart = convertNumberToWord(new ComplexInfo(
                    fraction.integer(), 
                   complexInfo.nLeadingZeroes()
            ));
           
            var remainderPart = convertNumberToWord(new ComplexInfo(
                    fraction.remainder(), 
                    complexInfo.nLeadingZeroes()
            ));
           
            result = "%s(%s)".formatted(integerPart, remainderPart);
        }
        
        return result;
    }
    
    
    public String increment(final String word) {
        validateWord(word);
        var cache = convertWordToNumber(word);
        return convertNumberToWord(new ComplexInfo(cache.number().add(BigInteger.ONE), cache.nLeadingZeroes()));
    }
    
    
    public String decrement(final String word) {
        validateWord(word);
        
        if (("" + minValue).equals(word)) {
            throw new RuntimeException(
                    "Достигнуто минимальное значение: %s".formatted(word));
        }
        
        var cache = convertWordToNumber(word);
        return convertNumberToWord(new ComplexInfo(cache.number().subtract(BigInteger.ONE), cache.nLeadingZeroes()));
    }
    
    
    private void validateWord(final String word) {
        Objects.requireNonNull(word);
                
        if (! (word.matches("%s\\(%s\\)".formatted(regexp, regexp)) 
                || word.matches(regexp))
        ) {
            throw new IllegalArgumentException("Слово должно содержать только русские буквы!");
        }
    }
    
    
    private int getLeadingZeroesCount(final String word) {
        final var symbols = word.toCharArray();
        int counter = 0;
        
        for (int i = 0; i < symbols.length; i++) {
            if (counter > 0 && symbols[i] != 'а') { 
                break;
            } else if (symbols[i] == 'а') { 
                counter++;
            }
        }
        
        return counter;
    }
    
    
    private BinaryOperator<ComplexInfo> selectComplexInfoAction(final BinaryOperator<BigInteger> action) {   
        return (accumulator, element) -> {
            ComplexInfo result = null;
            
            if (accumulator.number() != null && element.number() != null) {
                result = new ComplexInfo(
                    action.apply(accumulator.number(), element.number()),
                    Integer.min(accumulator.nLeadingZeroes(), element.nLeadingZeroes())
                ); 
            } else if (accumulator.fraction() != null ^ element.fraction() != null) {
                if (accumulator.fraction() != null) {
                    var integer = accumulator.fraction().integer();
                    var remainder = accumulator.fraction().remainder();
                    var product = element.number().multiply(integer).add(remainder);
                    var nZeroes = Integer.min(accumulator.nLeadingZeroes(), element.nLeadingZeroes());
                    result = new ComplexInfo(product, nZeroes);
                } else if (element.fraction() != null) {
                    var integer = element.fraction().integer();
                    var remainder = element.fraction().remainder();
                    var product = accumulator.number().multiply(integer).add(remainder);
                    var nZeroes = Integer.min(accumulator.nLeadingZeroes(), element.nLeadingZeroes());
                    result = new ComplexInfo(product, nZeroes);
                }            
            }
            
            return result;
        };     
    }   
}

    