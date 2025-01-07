package org.learners.sqrequlation;


class Matrix 
{
    private static final boolean ZERO = true;
    private static final boolean OTHER = false;
    
    private static final boolean[] MX_ALL_EQ_ZERO = { ZERO, ZERO, ZERO };
    private static final boolean[] MX_ALL_NE_ZERO = { OTHER, OTHER, OTHER }; 
    private static final boolean[] MX_ONLY_FIRST_EQ_ZERO = { ZERO, OTHER, OTHER };  
    private static final boolean[] MX_ONLY_FIRST_NE_ZERO = { OTHER, ZERO, ZERO }; 
    private static final boolean[] MX_ONLY_SECOND_EQ_ZERO = { OTHER, ZERO, OTHER };
    private static final boolean[] MX_ONLY_SECOND_NE_ZERO = { ZERO, OTHER, ZERO }; 
    private static final boolean[] MX_ONLY_THIRD_EQ_ZERO = { OTHER, OTHER, ZERO };   
    private static final boolean[] MX_ONLY_THIRD_NE_ZERO = { ZERO, ZERO, OTHER };
    
    private static final boolean[][] MATRIX_OF_MODES = 
    {
        MX_ALL_EQ_ZERO,
        MX_ALL_NE_ZERO,
        MX_ONLY_FIRST_EQ_ZERO,
        MX_ONLY_FIRST_NE_ZERO,
        MX_ONLY_SECOND_EQ_ZERO,
        MX_ONLY_SECOND_NE_ZERO,
        MX_ONLY_THIRD_EQ_ZERO,
        MX_ONLY_THIRD_NE_ZERO
    };
    
    
    private double a, b, c;
   
    
    Matrix(double a, double b, double c)
    {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    
             
    SetModeList getCase()
    {  
        SetModeList result = null;
        
        for (SetModeList currentValue : SetModeList.values())
        {   
            if (isValid(currentValue.getIndex()))
            {
                result = currentValue;
                break;
            }   
        }
        
        return result;
    }
   
    
    private boolean isValid(int i)
    {
        return
            isValidPart(a, SqrEqualer.A, i) &&
            isValidPart(b, SqrEqualer.B, i) &&
            isValidPart(c, SqrEqualer.C, i);
    }
   
    
    private boolean isValidPart(double k, int maskIndex, int maskValue)
    {
        return ! ( isEqZero(k) ^ getMask(maskValue)[maskIndex] );
    }
    
    
    private boolean[] getMask(int choice) 
    { 
        return MATRIX_OF_MODES[choice]; 
    }
   
    
    private boolean isEqZero(double converted)
    {
        return (converted == 0) ? ZERO : OTHER;
    }
}