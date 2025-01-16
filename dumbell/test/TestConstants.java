package dumbell.test;


import java.util.Map;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import dumbell.constants.*;
import java.lang.reflect.Field;


public class TestConstants 
        implements Render, DefaultGripData, IndexNames, CLParamNames {
    
    public class TryCatchWrapper {
        public interface FunctionWE<T, R, E extends Exception> {
            R apply(T t) throws E;
        }
        
        public static <T, R, E extends Exception> 
        Function<T, R> wrap(FunctionWE<T, R, E> wrappedFunc) {
            return arg -> {
                try {
                    return wrappedFunc.apply(arg);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            };
        }
    }
        
            
    private static final Class<?>[] CLASSES = {  
            IndexNames.class,
            DefaultGripData.class,  
            CLParamNames.class,   
            Render.class
    };
    
    
    private static Class<?> cachedType;
        
        
    public static void main(String... args) {
        printConstants();
    }
    
    
    public static void printConstants() {
        System.out.println(
                STR."""
                BASE_TR = 
                \{BASE_TR}
                
                ASYMMETRIC_TR = 
                \{ASYMMETRIC_TR}
                
                BEGIN_TEMPLATE = 
                \{BEGIN_TEMPLATE}
                
                ASYMMETRIC_TEMPLATE = 
                \{ASYMMETRIC_TEMPLATE}
                
                ENDING_TEMPLATE = 
                \{ENDING_TEMPLATE}
                
                
                COLORS = \{Arrays.toString(COLORS)}
                
                ALL_WEIGHTS = \{Arrays.toString(ALL_WEIGHTS)}
                
                GRIP_WEIGHT = \{GRIP_WEIGHT}
                
                SIDE_AMOUNT = \{SIDE_AMOUNT}
                
                DISK_AMOUNT = \{DISK_AMOUNT}
                
                MIN_DISK_AMOUNT = \{MIN_DISK_AMOUNT}
                
                MAX_DISK_AMOUNT = \{MAX_DISK_AMOUNT}
                
                
                I_IDX = \{I_IDX}
                
                J_IDX = \{J_IDX}
                
                K_IDX = \{K_IDX}
                
                N_IDX = \{N_IDX}
                
                
                OTHERS_STRICTLY_EQUAL_SHORT = \{OTHERS_STRICTLY_EQUAL_SHORT}
                
                OTHERS_STRICTLY_EQUAL_LONG = \{OTHERS_STRICTLY_EQUAL_LONG}
                """
        );
    }
    
    
    public static void printConstants(Class<?>... classes) {
        final var OBJECT = new Object();
        //setCachedType(initCachedType(classes));
        cachedType = null;
        //System.err.println(STR."Начальное значение: \{cachedType}");
        
        Function<Object, String> patternMatcher = obj -> switch (obj) {
                case String[] strings -> Arrays.toString(strings);
                case double[] doubles -> Arrays.toString(doubles);
                case String str -> str;
                default -> obj.toString();
        };
        
        TryCatchWrapper.FunctionWE<
                Field, 
                Map.Entry<Field, ?>,
                Exception
        > prepareMapper = field -> double.class == field.getType()
                ? Map.entry(field, field.getDouble(OBJECT))
                : int.class == field.getType()
                        ? Map.entry(field, field.getInt(OBJECT))
                        : Map.entry(field, patternMatcher.apply(field.get(OBJECT)));
        
        Predicate<Object> isPrintingNextLine = obj -> 
                String.class == obj.getClass() && (! ((String) obj).startsWith("-"));
                
        Predicate<Object> isPrintingNextLineMore = value -> getCachedType() != value.getClass();
        
        Function<
                Map.Entry<Field, ?>, 
                Map.Entry<
                        Map.Entry<Field, ?>, 
                        String
                >
        > resultMapper = entry -> {
                var field = (Field) entry.getKey();
                var value = entry.getValue();
                
                Map.Entry<
                        Map.Entry<Field, ?>, 
                        String
                > result = Map.entry(entry, 
                    "%s = %s%s%s%n"
                            .formatted(
                                    field.getName(), 
                                    isPrintingNextLine.test(value) ? "\n" : "", 
                                    value, 
                                    isPrintingNextLineMore.test(value) ? "\n" : ""
                            )
                ); 
                
                setCachedType(value.getClass());          
                return result; 
        };      
                    
        Predicate<Map.Entry<Field, ?>> isPass = entry -> ! ((String.class == entry.getValue().getClass()) 
                && ("SPACES".equals(entry.getKey().getName())));  
               
                
        Consumer<
                Map.Entry<
                        Map.Entry<Field, ?>, 
                        String
                >
        > cachingType = entry -> 
                setCachedType(((Field) entry.getKey().getKey()).getType());
                             
        Arrays.stream(classes)
                .map(Class::getFields)
                .flatMap(Arrays::stream)
                .map(TryCatchWrapper.wrap(prepareMapper))
                .filter(isPass)
                .map(resultMapper)
                //.peek(e -> System.err.println(cachedType))
                //.peek(cachingType)
                .map(Map.Entry::getValue)
                .forEach(System.out::println);
    }
    
    
    private static Class<?> initCachedType(Class<?>... classes) {                
        return Arrays.stream(classes)
                .map(Class::getFields)
                .flatMap(Arrays::stream)
                .map(TryCatchWrapper.wrap(Field::getType))
                .findFirst().get();
    }
    
    
    private static Class<?> getCachedType() { return cachedType; }
    
    
    private static void setCachedType(Class<?> type) { 
        cachedType = type; 
    }
}