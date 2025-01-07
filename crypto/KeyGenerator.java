package crypto;

import static java.util.stream.Collectors.*;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
//import java.util.SplittableRandom;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Random;

//import java.util.stream.Collectors;
import java.util.stream.IntStream;

import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;


public class KeyGenerator {
    private static final int FIRST_LEFT_RANGE_BOUND = 33;
    private static final int FIRST_RIGHT_RANGE_BOUND = 126;
    private static final int SECOND_LEFT_RANGE_BOUND = 200;
    private static final int SECOND_RIGHT_RANGE_BOUND = 275;
    private static final int LIMIT = 168;
    private static final int DEFAULT_SHUFFLE_LIMIT = 23 * 7;
    private static final List<Character> SYMBOLS;
    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();
    
    
    static {      
        IntPredicate hasNext = i -> (i >= FIRST_LEFT_RANGE_BOUND && i < FIRST_RIGHT_RANGE_BOUND) 
                                    || (i >= SECOND_LEFT_RANGE_BOUND && i < SECOND_RIGHT_RANGE_BOUND);
                                    
        IntUnaryOperator next = n -> n == FIRST_RIGHT_RANGE_BOUND - 1 
                                             ? n + (SECOND_LEFT_RANGE_BOUND - FIRST_RIGHT_RANGE_BOUND + 1) 
                                             : n + 1;
                                             
        SYMBOLS = IntStream.iterate(FIRST_LEFT_RANGE_BOUND, hasNext, next)
                           .mapToObj(e -> (char) e)
                           .collect(toList());
    }
    
        
    public static String generateKey() { return generateKey(DEFAULT_SHUFFLE_LIMIT); }
      
    public static String generateKey(final int shuffleLimit) {       
        IntStream.range(0, RANDOM.nextInt(shuffleLimit))
                 .forEach(e -> Collections.shuffle(SYMBOLS, new Random(RANDOM.nextInt())));
        
        return SYMBOLS.stream()
                .map(e -> "" + e)
                .collect(joining(""));
    }
}