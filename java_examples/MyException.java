

public class DiscriminantLtThanZeroException extends Exception
{
    public String toString()
    {
        return "Дискриминант меньше нуля!";
    }
}

public class Initializator
{
    private double a;
    private double b;
    private double c;
    private double discriminant;
    
    Initializator(double a, double b, double c)
    {
        this.a = a;
        this.b = b;
        this.c = c;
        setDiscriminant();
    }
    
    private void setDiscriminant() throws DiscriminantLtThanZeroException
    {
        discriminant = b*b - 4*a*c;
        
        if (discriminant < 0)
        {
            throw new DiscriminantLtThanZeroException();
        }
    }
}

