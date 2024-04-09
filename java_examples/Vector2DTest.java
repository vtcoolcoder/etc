import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

import java.util.Arrays;

import org.junit.Test;
import org.junit.Assert;


public class Vector2DTest {
    private static final String TEST_METHODS_NAME_PREFIX = "test";
    private static final String TEMPLATE = "[ТЕСТ]: %%s\n[СТАТУС]: %%s\n%s\n";
    private static final String ERRFORMATTING = "[ERROR]: %s\n";
    private static final String OK_FORMAT = TEMPLATE.formatted("");
    private static final String FAIL_FORMAT = TEMPLATE.formatted(ERRFORMATTING);
    private static final String STATUS_OK = "OK";
    private static final String STATUS_FAIL = "FAIL";
      
    private static int expectedIntoNewVectorShouldHaveZeroLength;
    private static int expectedIntoNewVectorShouldHaveZeroX;
    private static int expectedIntoNewVectorShouldHaveZeroY;
    

    public static void main(String[] args) {
        try {
            expectedIntoNewVectorShouldHaveZeroLength = 
                    args.length > 0 ? Integer.parseInt(args[0]) : 0;
                    
            expectedIntoNewVectorShouldHaveZeroX = 
                    args.length > 1 ? Integer.parseInt(args[1]) : 0;
                    
            expectedIntoNewVectorShouldHaveZeroY = 
                    args.length > 2 ? Integer.parseInt(args[2]) : 0;
        } catch (NumberFormatException e) {}
    
    
        Vector2DTest test = new Vector2DTest();
        
        
        Method[] methods = test.getClass().getMethods();
        
        Arrays.stream(methods)
              .filter(method -> method.getName().startsWith(TEST_METHODS_NAME_PREFIX))
              .forEach(method -> {
                  String methodName = method.getName();
                  
                  try {
                      method.invoke(test);
                      System.err.printf(OK_FORMAT, methodName, STATUS_OK);
                  } catch (InvocationTargetException outer) {
                  
                      try {
                          throw outer.getCause();
                      } catch (AssertionError inner) {
                          System.err.printf(FAIL_FORMAT, methodName, 
                                  STATUS_FAIL, inner.getMessage());
                      } catch (Throwable inner) {
                          new RuntimeException(inner);
                      }
                  } catch (Exception outer) {
                      new RuntimeException(outer);
                  }
              });
    }
    
    
    @Test
    public void testNewVectorShouldHaveZeroLength() {
        Vector2D vector = new Vector2D(); // action
        // assertion
        Assert.assertEquals(expectedIntoNewVectorShouldHaveZeroLength, 
                vector.length(), 1E-9);
    }
    
    
    @Test
    public void testNewVectorShouldHaveZeroX() {
        Vector2D vector = new Vector2D();
        
        Assert.assertEquals(expectedIntoNewVectorShouldHaveZeroX, 
                vector.getX(), 1E-9);
    }
    
    
    @Test
    public void testNewVectorShouldHaveZeroY() {
        Vector2D vector = new Vector2D();
        
        Assert.assertEquals(expectedIntoNewVectorShouldHaveZeroY, 
                vector.getY(), 1E-9);
    }
}