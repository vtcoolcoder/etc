package org.learners.sqrequlation;


class ConstructorParams 
{
    private double a;
    private double b;
    private double c;

    private boolean isShowGreeting;
    private boolean isShowABCD;
    private boolean isShowD;


    void setA(double a) 
    { 
        this.a = a; 
    }
   
    
    void setB(double b) 
    { 
        this.b = b; 
    }
   
    
    void setC(double c) 
    { 
        this.c = c; 
    }
   
    
    void setIsShowGreeting(boolean isShowGreeting) 
    {
        this.isShowGreeting = isShowGreeting;
    }
   
    
    void setIsShowABCD(boolean isShowABCD)
    {
        this.isShowABCD = isShowABCD;
    }


    void setIsShowD(boolean isShowD)
    {
        this.isShowD = isShowD;
    }


    double getA() 
    { 
        return a; 
    }
  
    
    double getB() 
    { 
        return b; 
    }
   
    
    double getC() 
    { 
        return c; 
    }
    
    
    boolean isShowGreeting() 
    { 
        return isShowGreeting; 
    }
   
    
    boolean isShowABCD() 
    { 
        return isShowABCD; 
    }


    boolean isShowD()
    {
        return isShowD;
    }
}