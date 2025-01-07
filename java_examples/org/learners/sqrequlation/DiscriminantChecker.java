package org.learners.sqrequlation;


class DiscriminantChecker
{
    private double a, b, c, discriminant;
    
    
    DiscriminantChecker(double a, double b, double c) 
            throws DiscriminantLtThanZeroException
    {
        this.a = a; 
        this.b = b; 
        this.c = c;
        setDiscriminant();
        checkDiscriminant();
    }
    
    
    double[] getValidABCDiscriminant()
    {
        return new double[] {a, b, c, discriminant};
    }
   
    
    private void setDiscriminant() 
    {
        discriminant = b*b - 4*a*c;
    }
   
    
    private void checkDiscriminant() throws DiscriminantLtThanZeroException
    {
        if (discriminant < 0)
        {
            throw new DiscriminantLtThanZeroException();
        }
    }
}