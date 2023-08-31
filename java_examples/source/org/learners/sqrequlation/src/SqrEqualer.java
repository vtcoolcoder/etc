package org.learners.sqrequlation;


public class SqrEqualer
{
    public static final boolean ON = true;
    public static final boolean OFF = false;
    
    public static final int A = 0;
    public static final int B = 1;
    public static final int C = 2;    
    public static final int DT = 3; 
    
    
    static SetMode currentMode;   
    static FunctionSelect selectFunction;     
    private static ConstructorParams unPackedParams = 
        new ConstructorParams();     
        
          
    private double a;
    private double b;
    private double c;
    private double discriminant;
     
    private String strResult;
    
    
    public static void main(String[] args)
    {
        System.out.println(new SqrEqualer());
    }
    
    
    static void setGlobalParams(ConstructorParams params)
    {
        unPackedParams = params;
    }
    
    
    static ConstructorParams getGlobalParams()
    {
        return unPackedParams;
    } 
    
    
    double[] getAbc()
    {
        return new double[] {a, b, c};
    }  
    
       
    public SqrEqualer(double a, double b, double c)
    {    
        try
        {
            double[] ABCD = new DiscriminantChecker(a, b, c)
                               .getValidABCDiscriminant();
            this.a = ABCD[A];
            this.b = ABCD[B];
            this.c = ABCD[C];
            this.discriminant = ABCD[DT];
        }  
        catch (SqrEqualerException e)
        {
            this.strResult = "" + e;
        }    
    }
    
    
    private SqrEqualer(boolean isShowGreeting, 
                       double a, double b, double c)
    {
        this(a, b, c);
        
        if (isShowGreeting) 
        {
            StateMessages.showGreeting(); 
        }
    } 
    
    
    public SqrEqualer(ConstructorParams params)
    {
        this(params.isShowGreeting(),
             params.getA(), 
             params.getB(), 
             params.getC());
        
        setGlobalParams(params);
                
        if (getGlobalParams().isShowABCD()) 
        { 
            new Showing(a, b, c, discriminant).showABCD(); 
        }
    }
    
    
    public SqrEqualer() 
    {   
        this(getGlobalParams().isShowGreeting() ? 
                 StateMessages.showGreeting() : OFF,
             new Parser(MakeVisioner.A).getResult(),                
             new Parser(MakeVisioner.B).getResult(),                 
             new Parser(MakeVisioner.C).getResult());
                                            
        System.out.println();
    }
    
       
    public String toString()
    {
        String result = "";
        
        try
        {      
            Matrix aMatrix = new Matrix(a, b, c);
            Function fun = aMatrix::getCase;
            currentMode = new SetMode(fun);
                      
            GettingResult aGettingResult = new GettingResult(a, b, c, discriminant);
            selectFunction = aGettingResult::selectGettedResult;
            result = aGettingResult.getResult();
            
        }
        catch (SqrEqualerException e)
        {
            result = "" + e;
        }
        
        return strResult == null ? result : strResult;       
    }   
}



