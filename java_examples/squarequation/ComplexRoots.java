//package squarequation;

import static java.lang.Math.*;

class ComplexRoots extends Roots {
    private Coefficients coefficients;
    private Complex discriminantSqrt;
    private Complex firstRoot;
    private Complex secondRoot;
    
    ComplexRoots(Coefficients coefficients) {
        this.coefficients = coefficients;     
        discriminantSqrt = new Complex(0, sqrt(abs(new Discriminant(coefficients)
                                                   .getDiscriminant())));                                    
        calculateRoots();
    }
    
    public String toString() { return "X1 = " + firstRoot + "\nX2 = " + secondRoot; }
    
    private void calculateRoots() {
        double aX2 = coefficients.getA() * 2;
        double b = coefficients.getB();
        
        Complex neg = new Complex(-1, 0);
        Complex cmxB = new Complex(b, 0);
        Complex cmxAX2 = new Complex(aX2, 0);
        
        firstRoot = discriminantSqrt.times(neg)
                                    .minus(cmxB)
                                    .divides(cmxAX2);
                                            
        secondRoot = discriminantSqrt.minus(cmxB)    
                                     .divides(cmxAX2);                                
    }
}
