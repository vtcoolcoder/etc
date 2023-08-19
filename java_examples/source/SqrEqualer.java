import java.util.Scanner;


public class SqrEqualer
{
    public static final boolean ON = true;
    public static final boolean OFF = false;
    
    private static final int A = 0;
    private static final int B = 1;
    private static final int C = 2;     
    private static final int FAIL_EXIT = 1;
    
    private static final double LIMIT = 1;
    private static final double INCREMENT = 1;
    
    private static final String TESTING = "Тестирование ";
    private static final String P = "...";
    private static final String EXCEPTIONS = "исключений" + P;
    private static final String BASE_CASES = "базовых вариантов" + P;
    private static final String PREFIX = "\t-- ";
    private static final String USER_INPUT = PREFIX + "с пользовательским вводом:";
    private static final String AVTO = PREFIX + "автоперебором:";
    
    
    private SetMode currentMode;       
    private static ConstructorParams unPackedParams = 
        new ConstructorParams();      
        
    private double a;
    private double b;
    private double c;
    private double discriminant;
    
    
    public static void main(String[] args)
    {
        runTesting();
    }
    
    private static void runTesting()
    {
        printlnError(TESTING + EXCEPTIONS);
        printlnError();
        
        showExceptionTestedContent(
            new SqrEqualer(0, 0, 0),
            new SqrEqualer(10, 0, 0),
            new SqrEqualer(0, 10, 0),
            new SqrEqualer(0, 0, 10),
            new SqrEqualer(10, 1, 10));
        
        printlnError(TESTING + BASE_CASES);
        printlnError(USER_INPUT);
        showResult();
        
        printlnError();
        printlnError(AVTO);     
        printlnError();      
        runLoop();
    }
    
    private static void packParams(double a, double b, double c)
    {
        getGlobalParams().setA(a);
        getGlobalParams().setB(b);
        getGlobalParams().setC(c);  
        getGlobalParams().setIsShowGreeting(OFF);
        getGlobalParams().setIsShowD(OFF);
        getGlobalParams().setIsShowABCD(ON);
    }
    
    private static void packParams()
    {
        getGlobalParams().setIsShowGreeting(ON);
        getGlobalParams().setIsShowD(ON);     
    }
    
    private static void runLoop()
    {
        for (double i = -LIMIT; i <= LIMIT; i += INCREMENT)
        {
            for (double j = -LIMIT; j <= LIMIT; j += INCREMENT)
            {
                for (double k = -LIMIT; k <= LIMIT; k += INCREMENT)
                {                 
                    showResult(i, j, k);
                }
            } 
        }     
    }
    
    private static void showResult(double a, double b, double c)
    {
        packParams(a, b, c);
        println(new SqrEqualer(getGlobalParams()));
        println();
    }
    
    private static void showResult()
    {  
        packParams();        
        println(new SqrEqualer());
        println();
    }
    
    
    private static void setGlobalParams(ConstructorParams params)
    {
        unPackedParams = params;
    }
    
    private static ConstructorParams getGlobalParams()
    {
        return unPackedParams;
    } 
    
    
    private static void showExceptionTestedContent(SqrEqualer ... sqrEqualers)
    {  
        for (SqrEqualer aSqrEqualer : sqrEqualers)
        {
            double[] abc = aSqrEqualer.getAbc();
            String aSqrEqualerInfo =
                "( a = " + abc[A] + 
                ", b = " + abc[B] + 
                ", c = " + abc[C] + " ): ";
                
            printError(aSqrEqualerInfo);
            println(aSqrEqualer);
            printlnError();
        }
    }
    
    private double[] getAbc()
    {
        return new double[] {a, b, c};
    }  
    
    private static void println(Object str) 
    { 
        System.out.println(str); 
    }
       
    private static void println() 
    { 
        System.out.println(); 
    }
      
    private static void printlnError(Object str) 
    { 
        System.err.println(str); 
    }
    
    private static void printError(Object str) 
    { 
        System.err.print(str); 
    }        
    
    private static void printlnError() 
    { 
        System.err.println(); 
    }
    
    
    public SqrEqualer(double a, double b, double c)
    {
        this.a = a;
        this.b = b;
        this.c = c;  
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
            new Showing().showABCD(); 
        }
    }
    
    public SqrEqualer() 
    {   
        this(getGlobalParams().isShowGreeting() ? 
                 StateMessages.showGreeting() : OFF,
             new Parser(MakeVisioner.A).getResult(),                
             new Parser(MakeVisioner.B).getResult(),                 
             new Parser(MakeVisioner.C).getResult());
                                            
        println();
    }
    
       
    public String toString()
    {
        String result = "";
        
        try
        {
            new DiscriminantChecker();
            currentMode = new SetMode();
            result = new GettingResult().getResult();
        }
        catch (MyExceptions e)
        {
            result = "" + e;
        }
        
        return result;       
    }
    
    
    private abstract class MyExceptions extends Exception
    {
        public abstract String toString();
    }


    private class DiscriminantLtThanZeroException extends MyExceptions
    {   
        public String toString()
        {
            return "На множестве действительных чисел нет корней!";
        }
    }
    
    private class NoRootsException extends MyExceptions
    {
        public String toString()
        {
            return "Нет корней!";
        }
    }
    
    private class XIsAnyNumberException extends MyExceptions
    {
        public String toString()
        {
            return "X -- любое число.";
        }
    }
      
    private class XEqualsZeroException extends MyExceptions
    {
        public String toString()
        {
            return "X = 0";
        }
    }
    
    
    private class DiscriminantChecker
    {
        DiscriminantChecker() throws DiscriminantLtThanZeroException
        {
            setDiscriminant();
            checkDiscriminant();
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
 
    
    private class Matrix 
    {
        private static final boolean ZERO = ON;
        private static final boolean OTHER = OFF;
        
        private static final boolean[] MX_ALL_EQ_ZERO = 
        {
            ZERO, 
            ZERO, 
            ZERO
        };
        
        private static final boolean[] MX_ALL_NE_ZERO = 
        {
            OTHER, 
            OTHER, 
            OTHER
        };
        
        private static final boolean[] MX_ONLY_FIRST_EQ_ZERO = 
        {
            ZERO, 
            OTHER, 
            OTHER
        };
        
        private static final boolean[] MX_ONLY_FIRST_NE_ZERO = 
        {
            OTHER, 
            ZERO, 
            ZERO
        };
        
        private static final boolean[] MX_ONLY_SECOND_EQ_ZERO = 
        {
            OTHER, 
            ZERO, 
            OTHER
        };
        
        private static final boolean[] MX_ONLY_SECOND_NE_ZERO = 
        {
            ZERO, 
            OTHER, 
            ZERO
        };
        
        private static final boolean[] MX_ONLY_THIRD_EQ_ZERO = 
        {
            OTHER, 
            OTHER, 
            ZERO
        };
        
        private static final boolean[] MX_ONLY_THIRD_NE_ZERO = 
        {
            ZERO, 
            ZERO, 
            OTHER
        };
        
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
        
        protected enum SetModeList
        {
            ALL_EQ_ZERO()
            {
                public int getIndex()
                {
                    return 0;
                }
            }, 
                       
            ALL_NE_ZERO()
            {
                public int getIndex()
                {
                    return 1;
                }
            },  
                   
            ONLY_FIRST_EQ_ZERO()
            {
                public int getIndex()
                {
                    return 2;
                }
            },  
                  
            ONLY_FIRST_NE_ZERO()
            {
                public int getIndex()
                {
                    return 3;
                }
            }, 
                 
            ONLY_SECOND_EQ_ZERO()
            {
                public int getIndex()
                {
                    return 4;
                }
            }, 
                     
            ONLY_SECOND_NE_ZERO()
            {
                public int getIndex()
                {
                    return 5;
                }
            },
                       
            ONLY_THIRD_EQ_ZERO()
            {
                public int getIndex()
                {
                    return 6;
                }
            }, 
                    
            ONLY_THIRD_NE_ZERO()
            {
                public int getIndex()
                {
                    return 7;
                }
            };
            
            public int getIndex()
            {
                return 0;
            }
        };
              
        protected SetModeList getCase()
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
                isValidPart(a, A, i) &&
                isValidPart(b, B, i) &&
                isValidPart(c, C, i);
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
    
    
    private class SetMode extends Matrix
    {       
        private boolean isStandartMode;
              
        private SetMode() 
            throws XIsAnyNumberException, XEqualsZeroException, NoRootsException
        {        
            setCurrentMode(getCase());
        }        
        
        private void setCurrentMode(SetModeList list) 
            throws XIsAnyNumberException, XEqualsZeroException, NoRootsException 
        {
            switch (list)
            {
                // a == 0, b == 0, c == 0
                case ALL_EQ_ZERO:
                    setShowAnyNumberMode();
                    break;
                    
                // a != 0, b != 0, c != 0    
                case ALL_NE_ZERO: 
                    setStandartMode();
                    break;
                    
                // a == 0, b != 0, c != 0   
                case ONLY_FIRST_EQ_ZERO: 
                    setLineEqualMode();
                    break;
                    
                // a != 0, b == 0, c == 0  
                case ONLY_FIRST_NE_ZERO: 
                    setShowXEqualZeroMode();
                    break;
                    
                // a != 0, b == 0, c != 0  
                case ONLY_SECOND_EQ_ZERO: 
                    setStandartMode();
                    break;
                    
                // a == 0, b != 0, c == 0   
                case ONLY_SECOND_NE_ZERO: 
                    setShowXEqualZeroMode();
                    break;
                    
                // a != 0, b != 0, c == 0   
                case ONLY_THIRD_EQ_ZERO: 
                    setStandartMode();
                    break;
                    
                // a == 0, b == 0, c != 0    
                case ONLY_THIRD_NE_ZERO: 
                    setShowNoRootsMode();
                    break;
            }
        }
         
        private void setShowAnyNumberMode() throws XIsAnyNumberException
        {   
            throw new XIsAnyNumberException();              
        }
        
        private void setShowXEqualZeroMode() throws XEqualsZeroException
        {
            throw new XEqualsZeroException();
        }
        
        private void setShowNoRootsMode() throws NoRootsException
        {
            throw new NoRootsException();
        }
        
        private void setStandartMode() 
        {
            isStandartMode = ON;   
        }
        
        private void setLineEqualMode() 
        {
            isStandartMode = OFF;
        }
        
        private boolean isStandartMode() 
        { 
            return isStandartMode;
        }
    }  
    
    
    private class Roots 
    {           
        private static final int ONE_ROOT = 0;
        private static final int FIRST_ROOT = -1;
        private static final int SECOND_ROOT = 1;
        
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
                
        private double getRootByDefault(int k) 
        { 
            return (-b + k*Math.sqrt(discriminant))/2*a;
        }
    }
    
    
    private class MakeVisioner extends Roots
    {   
        private static final String A = "a: ";
        private static final String B = "b: ";
        private static final String C = "c: ";
        private static final String D = "D: ";
        private static final String X1 = "X = ";
        private static final String X2 = "X2 = ";
        
        protected enum MakeVisionList
        {
            FIRST_K,
            SECOND_K,
            THIRD_K,
            DISCRIMINANT,
            ROOT_ONE,
            ROOT_TWO,
            LINE_ROOT,
            OTHER_CASE
        };
               
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
                             new GettingResult().selectGettedResult();
                    break;   
            }
            
            return result;
        }
    }
    
    
    private class GettingResult extends MakeVisioner
    {          
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
        
        private String selectGettedResult()
        {
            return currentMode.isStandartMode() ? 
                getStandart() : getLineEqualer();  
        }
        
        private String getResult()
        {       
            return getGlobalParams().isShowD() ?
                makeResult(MakeVisionList.OTHER_CASE) : selectGettedResult();
        }
    }
    
    
    private class Showing extends MakeVisioner
    {    
        private void showA() 
        { 
            println(makeResult(MakeVisionList.FIRST_K)); 
        }
        
        private void showB() 
        { 
            println(makeResult(MakeVisionList.SECOND_K)); 
        }
        
        private void showC() 
        { 
            println(makeResult(MakeVisionList.THIRD_K)); 
        }
        
        private void showD() 
        { 
            println(makeResult(MakeVisionList.DISCRIMINANT)); 
        }
        
        private void showABCD() 
        { 
            showA(); 
            showB(); 
            showC(); 
            showD(); 
        }
    }
    
    
    private class StateMessages
    {
        private static final String GREETING = 
            "Попытка решения квадратного уравнения: ax² + bx + c = 0 ...";       
        private static final String PROMPT = "Введите значение ";
        private static final String INVALID_VALUE = 
            "Введено некорректное значение!";    
        private static final String ERROR_MESSAGE = "Непредвиденная ошибка!";
        
        private static boolean showGreeting()   
        {
            printlnError(); 
            printlnError(GREETING); 
            printlnError();
            
            return OFF;
        }   
        
        private static void showPrompt(String prompt) 
        { 
            printlnError(PROMPT + prompt); 
        }
             
        private static void showErrorMessage() 
        { 
            printlnError(ERROR_MESSAGE); 
        }
        
        private static void showInvalidValue() 
        { 
            printlnError(INVALID_VALUE); 
        }
    }   
    
    
    public static class ConstructorParams 
    {
        private double a;
        private double b;
        private double c;
    
        private boolean isShowGreeting;
        private boolean isShowABCD;
        private boolean isShowD;

        public void setA(double a) 
        { 
            this.a = a; 
        }
        
        public void setB(double b) 
        { 
            this.b = b; 
        }
        
        public void setC(double c) 
        { 
            this.c = c; 
        }
        
        public void setIsShowGreeting(boolean isShowGreeting) 
        {
            this.isShowGreeting = isShowGreeting;
        }
        
        public void setIsShowABCD(boolean isShowABCD)
        {
            this.isShowABCD = isShowABCD;
        }

        public void setIsShowD(boolean isShowD)
        {
            this.isShowD = isShowD;
        }
    
        public double getA() 
        { 
            return a; 
        }
        
        public double getB() 
        { 
            return b; 
        }
        
        public double getC() 
        { 
            return c; 
        }
        
        public boolean isShowGreeting() 
        { 
            return isShowGreeting; 
        }
        
        public boolean isShowABCD() 
        { 
            return isShowABCD; 
        }

        public boolean isShowD()
        {
            return isShowD;
        }
    }
    
    
    private static class Parser
    {
        private String label;
        private double result;   
        
        private Parser(String label)
        {
            this.label = label;
            result = parser(getString());
        }
          
        private double getResult() 
        { 
            return result; 
        }
        
        private double parser(String parsed)
        {
            double result = 0;
            
            try
            {
                result = Double.parseDouble(parsed);
            }
            
            catch (Exception e)
            {
                StateMessages.showInvalidValue();        
                result = parser(getString());
            }
            
            return result;
        }       
        
        private String getString()
        {
            String result = "";
            StateMessages.showPrompt(label);
            
            try
            {
                result = new Scanner(System.in).nextLine();
            }
            
            catch (Exception e)
            {
                StateMessages.showErrorMessage();
                System.exit(FAIL_EXIT);
            }     
            
            return result;
        }        
    }
}



