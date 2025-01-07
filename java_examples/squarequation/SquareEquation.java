//package squarequation;

public class SquareEquation {
    private Coefficients coefficients;
    private Discriminant discriminant;
    private Roots roots;
    
    public SquareEquation(double a, double b, double c) {
        coefficients = new Coefficients(a, b, c);
        discriminant = new Discriminant(coefficients);
    }
    
    //public Coefficients getCoefficients() { return coefficients; }
    public double getDiscriminant() { return discriminant.getDiscriminant(); }
    public Roots getRoots() { return roots; }
}