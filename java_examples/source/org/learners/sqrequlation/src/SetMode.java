package org.learners.sqrequlation;


class SetMode 
{       
    protected boolean isStandartMode;
     
          
    SetMode(Function function) 
            throws XIsAnyNumberException, 
                   XEqualsZeroException, 
                   NoRootsException
    {   
        setCurrentMode(function.get());
    }        
    
    
    protected void setCurrentMode(SetModeList list) 
                 throws XIsAnyNumberException, 
                        XEqualsZeroException, 
                        NoRootsException 
    {
        switch (list)
        {
            case ALL_EQ_ZERO:
                setShowAnyNumberMode();
                break;
                
            case ALL_NE_ZERO: 
                setStandartMode();
                break;
                
            case ONLY_FIRST_EQ_ZERO: 
                setLineEqualMode();
                break;
                
            case ONLY_FIRST_NE_ZERO: 
                setShowXEqualZeroMode();
                break;
                
            case ONLY_SECOND_EQ_ZERO: 
                setStandartMode();
                break;
                
            case ONLY_SECOND_NE_ZERO: 
                setShowXEqualZeroMode();
                break;
                
            case ONLY_THIRD_EQ_ZERO: 
                setStandartMode();
                break;
                
            case ONLY_THIRD_NE_ZERO: 
                setShowNoRootsMode();
                break;
        }
    }
    
     
    protected void setShowAnyNumberMode() 
                 throws XIsAnyNumberException
    {   
        throw new XIsAnyNumberException();              
    }
   
    
    protected void setShowXEqualZeroMode() 
                 throws XEqualsZeroException
    {
        throw new XEqualsZeroException();
    }
    
    
    protected void setShowNoRootsMode() 
                 throws NoRootsException
    {
        throw new NoRootsException();
    }
   
    
    protected void setStandartMode() 
    {
        isStandartMode = SqrEqualer.ON;   
    }
   
    
    protected void setLineEqualMode() 
    {
        isStandartMode = SqrEqualer.OFF;
    }
   
    
    protected boolean isStandartMode() 
    { 
        return isStandartMode;
    }
}  