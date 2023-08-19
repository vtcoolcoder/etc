public class ConstructorParams 
{
    private double a;
    private double b;
    private double c;
    
    private boolean isShowGreeting;
    private boolean isShowAbcd;
    
    public void setA(double a) { this.a = a; }
    public void setB(double b) { this.b = b; }
    public void setC(double c) { this.c = c; }
    public void setIsShowGreeting(boolean isShowGreeting) 
    {
        this.isShowGreeting = isShowGreeting;
    }
    public void setIsShowAbcd(boolean isShowAbcd)
    {
        this.isShowAbcd = isShowAbcd;
    }
    
    public double getA() { return a; }
    public double getB() { return b; }
    public double getC() { return c; }
    public boolean getIsShowGreeting() { return isShowGreeting; }
    public boolean getIsShowAbcd() { return isShowAbcd; }
}