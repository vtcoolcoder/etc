//package squarequation;

class Discriminant {
    private double discriminant;  
    
    Discriminant(Coefficients coefficients) {
        discriminant = Math.pow(coefficients.getB(), 2) -
            4 * coefficients.getA() * coefficients.getC();
    }
    
    public double getDiscriminant() { return discriminant; }
}