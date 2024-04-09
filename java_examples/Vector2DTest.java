import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

import java.util.Arrays;

import org.junit.Test;
import org.junit.Assert;
import org.junit.BeforeClass;


public class Vector2DTest {
    private static final double EPS = 1E-9;
    
    private static final int MIN_ORDINAL_NUMBER = 1;
    private static final int DEFAULT_VALUE = 0;
    
    private static final String TEST_METHODS_NAME_PREFIX = "test";
    private static final String TEMPLATE = "[ТЕСТ]: %%s\n[СТАТУС]: %%s\n%s\n";
    private static final String ERRFORMATTING = "[ПРИЧИНА]: %s\n";
    private static final String OK_FORMAT = TEMPLATE.formatted("");
    private static final String FAIL_FORMAT = TEMPLATE.formatted(ERRFORMATTING);
    private static final String STATUS_OK = "OK";
    private static final String STATUS_FAIL = "FAIL";
    private static final String ILLEGAL_ARG_MSG = 
            "Порядковый номер аргумента командной строки не должен быть меньше %d!"
            .formatted(MIN_ORDINAL_NUMBER);
    
    
    private static Vector2D vector; 
      
    private static int expectedIntoNewVectorShouldHaveZeroLength;
    private static int expectedIntoNewVectorShouldHaveZeroX;
    private static int expectedIntoNewVectorShouldHaveZeroY;
    
    
    static {
        createNewVector();
    }
    

    public static void main(String[] args) {
        initExpectings(args);
        runManualTesting();
    }
    
    
    @BeforeClass
    public static void createNewVector() { vector = new Vector2D(); }
    
    
    @Test
    public void testNewVectorShouldHaveZeroLength() {
        Assert.assertEquals(expectedIntoNewVectorShouldHaveZeroLength, 
                vector.length(), EPS);
    }
    
    
    @Test
    public void testNewVectorShouldHaveZeroX() {
        Assert.assertEquals(expectedIntoNewVectorShouldHaveZeroX, 
                vector.getX(), EPS);
    }
    
    
    @Test
    public void testNewVectorShouldHaveZeroY() {
        Assert.assertEquals(expectedIntoNewVectorShouldHaveZeroY, 
                vector.getY(), EPS);
    }
    
    
    private static void initExpectings(final String[] args) {
        try {
            expectedIntoNewVectorShouldHaveZeroLength = getCLArgByOrdinalNumber(args, 1);
            expectedIntoNewVectorShouldHaveZeroX = getCLArgByOrdinalNumber(args, 2);
            expectedIntoNewVectorShouldHaveZeroY = getCLArgByOrdinalNumber(args, 3);               
        } catch (NumberFormatException e) {}
    }
    
    
    private static int getCLArgByOrdinalNumber(final String[] args, final int ordinalNumber) {
        if (ordinalNumber < MIN_ORDINAL_NUMBER) { throw new IllegalArgumentException(ILLEGAL_ARG_MSG); }
        final int INDEX = ordinalNumber - 1;
        return args.length > INDEX ? Integer.parseInt(args[INDEX]) : DEFAULT_VALUE;
    }
    
    
    private static void runManualTesting() {
        Vector2DTest test = new Vector2DTest();
        Method[] methods = test.getClass().getMethods();
        
        Arrays.stream(methods)
              .filter(method -> method.getName().startsWith(TEST_METHODS_NAME_PREFIX))
              .forEach(method -> {
                  String methodName = method.getName();
                  
                  try {
                      method.invoke(test);
                      System.out.printf(OK_FORMAT, methodName, STATUS_OK);
                  } catch (InvocationTargetException outer) {
                  
                      try {
                          throw outer.getCause();
                      } catch (AssertionError inner) {
                          System.err.printf(FAIL_FORMAT, methodName, 
                                  STATUS_FAIL, inner.getMessage());
                      } catch (Throwable inner) {
                          throw new RuntimeException(inner);
                      }
                  } catch (Exception outer) {
                      throw new RuntimeException(outer);
                  }
              });
    }
}