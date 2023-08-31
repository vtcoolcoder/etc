package org.learners.sqrequlation;


class MakeVisioner extends Roots
{   
    protected static final String A = "a: ";
    protected static final String B = "b: ";
    protected static final String C = "c: ";
    protected static final String D = "D: ";
    protected static final String X1 = "X = ";
    protected static final String X2 = "X2 = ";
    
       
    MakeVisioner(double a, double b, double c, double d)
    {
        super(a, b, c, d);
    }
   
               
    protected String makeResult(MakeVisionList list)
    {
        String result = "";
        
        switch (list)
        {
            case FIRST_K: 
                result = A + a; 
                break;
                
            case SECOND_K: 
                result = B + b; 
                break;
                
            case THIRD_K: 
                result = C + c; 
                break;
                
            case DISCRIMINANT: 
                result = D + discriminant;
                break;
                
            case ROOT_ONE: 
                result = X1 + getOneRoot(); 
                break;
                
            case ROOT_TWO: 
                result = X1 + getFirstRoot() + "\n" + 
                         X2 + getSecondRoot();
                break;
                
            case LINE_ROOT:
                result = X1 + getLineRoot();
                break;
                
            case OTHER_CASE: 
                result = makeResult(MakeVisionList.DISCRIMINANT) + "\n" +
                         SqrEqualer.selectFunction.select();
                break;   
        }
        
        return result;
    }
}