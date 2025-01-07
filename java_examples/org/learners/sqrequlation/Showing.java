package org.learners.sqrequlation;


class Showing extends MakeVisioner
{  
    Showing(double a, double b, double c, double d)
    {
        super(a, b, c, d);
    }
    
      
    void showA() 
    { 
        System.out.println(makeResult(MakeVisionList.FIRST_K)); 
    }
    
    
    void showB() 
    { 
        System.out.println(makeResult(MakeVisionList.SECOND_K)); 
    }
    
    
    void showC() 
    { 
        System.out.println(makeResult(MakeVisionList.THIRD_K)); 
    }
    
    
    void showD() 
    { 
        System.out.println(makeResult(MakeVisionList.DISCRIMINANT)); 
    }
    
    
    void showABCD() 
    { 
        showA(); 
        showB(); 
        showC(); 
        showD(); 
    }
}