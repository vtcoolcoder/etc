package org.learners.sqrequlation;


class GettingResult extends MakeVisioner
{  
    GettingResult(double a, double b, double c, double d)   
    {
        super(a, b, c, d);
    }     
    
    
    private String discriminantEqualZero() 
    { 
        return makeResult(MakeVisionList.ROOT_ONE); 
    }
    
    
    private String discriminantGtThanZero() 
    { 
        return makeResult(MakeVisionList.ROOT_TWO); 
    }
   
    
    private boolean isDiscriminantEqualZero()
    {
        return discriminant == 0;
    }
    
    
    private String getStandart()
    {
        return isDiscriminantEqualZero() ?
            discriminantEqualZero() : discriminantGtThanZero();
    }
    
    
    private String getLineEqualer()
    {
        return makeResult(MakeVisionList.LINE_ROOT);
    }
   
    
    String selectGettedResult()
    {
        return SqrEqualer.currentMode.isStandartMode() ? 
            getStandart() : getLineEqualer();  
    }
    
    
    String getResult()
    {       
        return SqrEqualer.getGlobalParams().isShowD() ?
            makeResult(MakeVisionList.OTHER_CASE) : selectGettedResult();
    }
}