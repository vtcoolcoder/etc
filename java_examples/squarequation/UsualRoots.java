//package squarequation;

class UsualRoots extends Roots {
    private double first;
    private double second;
    
    Roots(double x1, double x2) {
        first = x1;
        second = x2;
    }
    
    double getFirst() { return first; }
    double getSecond() { return second; }
}