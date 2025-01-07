package org.learners.sqrequlation;


class Roots 
{           
    protected static final int ONE_ROOT = 0;
    protected static final int FIRST_ROOT = -1;
    protected static final int SECOND_ROOT = 1;
    
    
    protected double a, b, c, discriminant;
    
    
    Roots(double a, double b, double c, double discriminant)
    {
        this.a = a;
        this.b = b;
        this.c = c;
        this.discriminant = discriminant;
    }
   
    
    protected double getOneRoot() 
    { 
        return getRootByDefault(ONE_ROOT); 
    }
    
    
    protected double getFirstRoot() 
    { 
        return getRootByDefault(FIRST_ROOT); 
    }
    
    
    protected double getSecondRoot() 
    { 
        return getRootByDefault(SECOND_ROOT); 
    }
    
    
    protected double getLineRoot() 
    { 
        return -c/b; 
    }
   
            
    protected double getRootByDefault(int k) 
    { 
        return (-b + k*Math.sqrt(discriminant)) /2*a;
    }
}