import static java.lang.Math.*;

public class TestComplexRoots {
    private static final double LIMIT = 10;
    private static final double STEP = 0.5;
    
    public static void main(String[] args) {
        for (double a = -LIMIT; a <= LIMIT; a += STEP) {
            for (double b = -LIMIT; b <= LIMIT; b += STEP) {
                for (double c = -LIMIT; c <= LIMIT; c += STEP) {
                    System.err.printf("a = %f, b = %f, c = %f\n", a, b, c)
                              .printf("%s\n\n", new ComplexRoots(new Coefficients(a, b, c)));
                }
            }
        }    
    }
}