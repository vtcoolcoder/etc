import java.util.Scanner;


public class SqrEqual
{    
    private class DebugMessages
    {
        protected static final String STR_ALL_EQ_ZERO = 
            "a == 0, b == 0, c == 0; -> 0 = 0 -> x -- любое число";
            
        protected static final String STR_ALL_NE_ZERO = 
            "a != 0, b != 0, c != 0; -> ax² + bx + c = 0 -> STANDART";
            
        protected static final String STR_ONLY_FIRST_EQ_ZERO = 
            "a == 0, b != 0, c != 0; -> bx + c = 0 -> x = -c/b";
            
        protected static final String STR_ONLY_FIRST_NE_ZERO = 
            "a != 0, b == 0, c == 0; -> ax² = 0 -> x = 0";
            
        protected static final String STR_ONLY_SECOND_EQ_ZERO = 
            "a != 0, b == 0, c != 0; -> ax² + c = 0 -> x² = -c/a";
            
        protected static final String STR_ONLY_SECOND_NE_ZERO = 
            "a == 0, b != 0, c == 0; -> bx = 0 -> x = 0";
            
        protected static final String STR_ONLY_THIRD_EQ_ZERO = 
            "a != 0, b != 0, c == 0; -> ax² + bx = 0";
            
        protected static final String STR_ONLY_THIRD_NE_ZERO = 
            "a == 0, b == 0, c != 0; -> c != 0 -> NO_ROOTS";
            
        protected void showDebugMessage(String msg)
        {
            if (getGlobalParams().isDebugOn()) 
            { 
                printError(msg); 
            }
        }
    }
    
    
    private class Matrix extends DebugMessages
    {
        private static final boolean ZERO = true;
        private static final boolean OTHER = false;
        
        private static final boolean[] MX_ALL_EQ_ZERO = {ZERO, ZERO, ZERO};
        private static final boolean[] MX_ALL_NE_ZERO = {OTHER, OTHER, OTHER};
        private static final boolean[] MX_ONLY_FIRST_EQ_ZERO = {ZERO, OTHER, OTHER};
        private static final boolean[] MX_ONLY_FIRST_NE_ZERO = {OTHER, ZERO, ZERO};
        private static final boolean[] MX_ONLY_SECOND_EQ_ZERO = {OTHER, ZERO, OTHER};
        private static final boolean[] MX_ONLY_SECOND_NE_ZERO = {ZERO, OTHER, ZERO};
        private static final boolean[] MX_ONLY_THIRD_EQ_ZERO = {OTHER, OTHER, ZERO};
        private static final boolean[] MX_ONLY_THIRD_NE_ZERO = {ZERO, ZERO, OTHER};
        
        private static final boolean[][] CASE_MATRIX = 
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
            
        protected static final int ALL_EQ_ZERO = 0;
        protected static final int ALL_NE_ZERO = 1;
        
        protected static final int ONLY_FIRST_EQ_ZERO = 2;
        protected static final int ONLY_FIRST_NE_ZERO = 3;
        
        protected static final int ONLY_SECOND_EQ_ZERO = 4;
        protected static final int ONLY_SECOND_NE_ZERO = 5;
        
        protected static final int ONLY_THIRD_EQ_ZERO = 6;
        protected static final int ONLY_THIRD_NE_ZERO = 7;
        
        private static final int NOT_FOUND = -1;
        private static final int A = 0;
        private static final int B = 1;
        private static final int C = 2;   
        
        private double a;
        private double b;
        private double c;
        
        private Matrix(double a, double b, double c)
        {
            this.a = a;
            this.b = b;
            this.c = c;
        }
        
        protected int getCase()
        {  
            int found = NOT_FOUND;
            
            for (int i = ALL_EQ_ZERO; i <= ONLY_THIRD_NE_ZERO; i++)
            {   
                if (isValid(i))
                {
                    found = i;
                    break;
                }   
            }
            
            return found;
        }
        
        private boolean isValid(int i)
        {
            return 
                ( isEqZero(a) == getMask(i)[A] ) &&
                ( isEqZero(b) == getMask(i)[B] ) &&
                ( isEqZero(c) == getMask(i)[C] ) ;
        }
        
        private boolean[] getMask(int choice) 
        { 
            return CASE_MATRIX[choice]; 
        }
        
        private boolean isEqZero(double converted)
        {
            return (converted == 0) ? ZERO : OTHER;
        }
    }
    
    
    private class SetMode extends Matrix
    {        
        private boolean isStandartMode;
        private boolean isLineEqualMode;
        
        private boolean isShowAnyNumberMode;
        private boolean isShowXEqualZeroMode;
        private boolean isShowNoRootsMode;
            
        private SetMode(double a, double b, double c)
        {
            super(a, b, c);
            setCurrentMode(getCase());
        }        
        
        private void setCurrentMode(int list)
        {
            switch (list)
            {
                // a == 0, b == 0, c == 0
                case ALL_EQ_ZERO:
                    showDebugMessage(STR_ALL_EQ_ZERO);
                    setShowAnyNumberMode();
                    break;
                    
                // a != 0, b != 0, c != 0    
                case ALL_NE_ZERO: 
                    showDebugMessage(STR_ALL_NE_ZERO);
                    setStandartMode();
                    break;
                    
                // a == 0, b != 0, c != 0   
                case ONLY_FIRST_EQ_ZERO: 
                    showDebugMessage(STR_ONLY_FIRST_EQ_ZERO);
                    setLineEqualMode();
                    break;
                    
                // a != 0, b == 0, c == 0  
                case ONLY_FIRST_NE_ZERO: 
                    showDebugMessage(STR_ONLY_FIRST_NE_ZERO);
                    setShowXEqualZeroMode();
                    break;
                    
                // a != 0, b == 0, c != 0  
                case ONLY_SECOND_EQ_ZERO: 
                    showDebugMessage(STR_ONLY_SECOND_EQ_ZERO);
                    setStandartMode();
                    break;
                    
                // a == 0, b != 0, c == 0   
                case ONLY_SECOND_NE_ZERO: 
                    showDebugMessage(STR_ONLY_SECOND_NE_ZERO);
                    setShowXEqualZeroMode();
                    break;
                    
                // a != 0, b != 0, c == 0   
                case ONLY_THIRD_EQ_ZERO: 
                    showDebugMessage(STR_ONLY_THIRD_EQ_ZERO);
                    setStandartMode();
                    break;
                    
                // a == 0, b == 0, c != 0    
                case ONLY_THIRD_NE_ZERO: 
                    showDebugMessage(STR_ONLY_THIRD_NE_ZERO);
                    setShowNoRootsMode();
                    break;
            }
        }
         
        private void setShowAnyNumberMode() 
        {
            isShowAnyNumberMode = true;
            
            isStandartMode = false;
            isLineEqualMode = false;
            isShowXEqualZeroMode = false;
            isShowNoRootsMode = false;
        }
        
        private void setShowXEqualZeroMode() 
        {
            isShowXEqualZeroMode = true;
            
            isShowAnyNumberMode = false;
            isShowNoRootsMode = false;
            isStandartMode = false;
            isLineEqualMode = false;
        }
        
        private void setShowNoRootsMode() 
        {
            isShowNoRootsMode = true;
            
            isShowXEqualZeroMode = false;
            isShowAnyNumberMode = false;
            isStandartMode = false;
            isLineEqualMode = false;
        }
        
        private void setStandartMode() 
        {
            isStandartMode = true;
            
            isShowNoRootsMode = false;
            isLineEqualMode = false;
            isShowXEqualZeroMode = false;
            isShowAnyNumberMode = false;
        }
        
        private void setLineEqualMode() 
        {
            isLineEqualMode = true;
            
            isStandartMode = false;
            isShowNoRootsMode = false;
            isShowXEqualZeroMode = false;
            isShowAnyNumberMode = false;
        }
        
        protected boolean isStandartMode() 
        { 
            return isStandartMode; 
        }
        
        protected boolean isLineEqualMode() 
        { 
            return isLineEqualMode; 
        }
        
        protected boolean isShowAnyNumberMode() 
        { 
            return isShowAnyNumberMode; 
        }
        
        protected boolean isShowXEqualZeroMode() 
        { 
            return isShowXEqualZeroMode; 
        }
        
        protected boolean isShowNoRootsMode() 
        { 
            return isShowNoRootsMode; 
        }
    }  


    private class Abcd extends SetMode
    {
        protected static final String A = "a: ";
        protected static final String B = "b: ";
        protected static final String C = "c: ";
        protected static final String D = "D: ";
        
        private double a;
        private double b;
        private double c;
        private double discriminant;   
        
        private Abcd(double a, double b, double c)
        {
            super(a, b, c);
            
            this.a = a;
            this.b = b;
            this.c = c;
            
            setDiscriminant();
        }
        
        private void setDiscriminant() 
        { 
            discriminant = b*b - 4*a*c; 
        }
        
        protected double getA() 
        { 
            return a; 
        }
        
        protected double getB() 
        { 
            return b; 
        }
        
        protected double getC() 
        { 
            return c; 
        }
                
        protected double getDiscriminant() 
        { 
            return discriminant; 
        }
        
        protected double getDiscriminantSqrt() 
        {
            return Math.sqrt(getDiscriminant());
        }  
    }
    
    
    private class Roots extends Abcd
    {       
        protected static final String X_IS_ANY_NUMBER = "X -- любое число.";
        protected static final String X_IS_ZERO = "X = 0";
        protected static final String COMMON_NO_ROOTS = "Нет корней!";  
                 
        protected static final String X1 = "X = ";
        protected static final String X2 = "X2 = ";
        protected static final String STR_NO_ROOTS = 
            "На множестве действительных чисел -- нет корней!";
            
        protected static final int ONE_ROOT = 0;
        protected static final int FIRST_ROOT = -1;
        protected static final int SECOND_ROOT = 1;
                
        private Roots(double a, double b, double c) 
        { 
            super(a, b, c); 
        }
          
        protected double getRoot(int k) 
        { 
            return ( -getB() + k*getDiscriminantSqrt() ) /2* getA();
        }
        
        protected double getRoot() 
        { 
            return -getC()/getB(); 
        }
    }
    
    
    private class GettingResult extends Roots
    {     
        protected static final int FIRST_K = 0;
        protected static final int SECOND_K = 1;
        protected static final int THIRD_K = 2;
        protected static final int DISCRIMINANT = 3;
       
        private static final int ROOT_ZERO = 4;
        private static final int ROOT_ONE = 5;
        private static final int ROOT_TWO = 6;
        private static final int LINE_ROOT = 7;  
        private static final int ANY_NUMBER = 8;
        private static final int EQUAL_ZERO = 9;  
        private static final int NO_ROOTS = 10;
        private static final int OTHER_CASE = -1;
        
        private GettingResult(double a, double b, double c) 
        { 
            super(a, b, c); 
        }
               
        protected String makeVision(int list)
        {
            String buffer = "";
            
            switch (list)
            {
                case FIRST_K: 
                    buffer =  A + getA(); 
                    break;
                    
                case SECOND_K: 
                    buffer =  B + getB(); 
                    break;
                    
                case THIRD_K: 
                    buffer =  C + getC(); 
                    break;
                    
                case DISCRIMINANT: 
                    buffer = D + getDiscriminant();
                    break;
                    
                case ROOT_ZERO: 
                    buffer = STR_NO_ROOTS; 
                    break;
                    
                case ROOT_ONE: 
                    buffer = X1 + getOneRoot(); 
                    break;
                    
                case ROOT_TWO: 
                    buffer = X1 + getFirstRoot() + "\n" + X2 + getSecondRoot();
                    break;
                    
                case LINE_ROOT:
                    buffer = X1 + getLineRoot();
                    break;
                    
                case ANY_NUMBER:
                    buffer = X_IS_ANY_NUMBER;
                    break;
                    
                case EQUAL_ZERO:
                    buffer = X_IS_ZERO;
                    break;
                    
                case NO_ROOTS:
                    buffer = COMMON_NO_ROOTS;
                    break;
                    
                case OTHER_CASE: 
                    buffer = makeVision(DISCRIMINANT) + "\n" + buildTree();
                    break;   
            }
            
            return buffer;
        }
    
        private double getLineRoot() 
        { 
            return getRoot(); 
        }
        
        private double getOneRoot() 
        { 
            return getRoot(ONE_ROOT); 
        }
        
        private double getFirstRoot() 
        { 
            return getRoot(FIRST_ROOT); 
        }
        
        private double getSecondRoot() 
        { 
            return getRoot(SECOND_ROOT); 
        }
        
        private String discriminantLtThanZero() 
        { 
            return makeVision(ROOT_ZERO); 
        }
        
        private String discriminantEqualZero() 
        { 
            return makeVision(ROOT_ONE); 
        }
        
        private String discriminantGtThanZero() 
        { 
            return makeVision(ROOT_TWO); 
        }
        
        private boolean isDiscriminantLtThanZero()
        {
            return getDiscriminant() < 0;
        }
        
        private boolean isDiscriminantEqualZero()
        {
            return getDiscriminant() == 0;
        }
        
        private String getStandart()
        {
            return isDiscriminantLtThanZero() ? 
                discriminantLtThanZero() : isDiscriminantEqualZero() ?
                    discriminantEqualZero() : discriminantGtThanZero() ;
        }
        
        private String getLineEqualer()
        {
            return makeVision(LINE_ROOT);
        }
        
        private String getAnyNumber()
        {
            return makeVision(ANY_NUMBER);
        }
        
        private String getXEqualZero()
        {
            return makeVision(EQUAL_ZERO);
        }
        
        private String getNoRoots()
        {
            return makeVision(NO_ROOTS);
        }
        
        private String buildTree()
        {
            return isStandartMode() ? 
                getStandart() : isLineEqualMode() ?
                    getLineEqualer() : isShowAnyNumberMode() ?
                        getAnyNumber() : isShowXEqualZeroMode() ?
                            getXEqualZero() : getNoRoots();   
        }
        
        protected String getResult()
        {       
            return getGlobalParams().isShowD() ?
                makeVision(OTHER_CASE) : buildTree();
        }
    }
    
       
    private class Showing extends GettingResult
    {
        private Showing(double a, double b, double c)
        {
            super(a, b, c);
        }
        
        private void showA() 
        { 
            print(makeVision(FIRST_K)); 
        }
        
        private void showB() 
        { 
            print(makeVision(SECOND_K)); 
        }
        
        private void showC() 
        { 
            print(makeVision(THIRD_K)); 
        }
        
        private void showD() 
        { 
            print(makeVision(DISCRIMINANT)); 
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
            
        private static final String PROMT = "Введите значение ";
        
        private static final String INVALID_VALUE = 
            "Введено некорректное значение!";
            
        private static final String ERROR_MESSAGE = "Непредвиденная ошибка!";
        
        private static void showGreeting()   
        {
            printError(); 
            printError(GREETING); 
            printError();
        }   
        
        private static void showPromt(String promt) 
        { 
            printError(PROMT + promt); 
        }
             
        private static void showErrorMessage() 
        { 
            printError(ERROR_MESSAGE); 
        }
        
        private static void showInvalidValue() 
        { 
            printError(INVALID_VALUE); 
        }
    }   
    
    
    public static class ConstructorParams 
    {
        private static final boolean ON = true;
        private static final boolean OFF = false;
        
        private double a;
        private double b;
        private double c;
    
        private boolean isShowGreeting;
        private boolean isShowABCD;
        private boolean isShowD;
        private boolean isDebugOn;
        private boolean isCalledWithParams;
    
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
        
        public void setIsDebugOn(boolean isDebugOn)
        {
            this.isDebugOn = isDebugOn;
        }
        
        public void setIsCalledWithParams(boolean isCalledWithParams)
        {
            this.isCalledWithParams = isCalledWithParams;
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
        
        public boolean isDebugOn() 
        { 
            return isDebugOn; 
        }
        
        public boolean isCalledWithParams()
        {
            return isCalledWithParams;
        }
        
        public boolean isShowD()
        {
            return isShowD;
        }
    }
    
    
    private class Parser
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
            double buffer = 0;
            
            try
            {
                buffer = Double.parseDouble(parsed);
            }
            
            catch (Exception e)
            {
                StateMessages.showInvalidValue();        
                buffer = parser(getString());
            }
            
            return buffer;
        }       
        
        private String getString()
        {
            String buffer = "";
            StateMessages.showPromt(label);
            
            try
            {
                buffer = new Scanner(System.in).nextLine();
            }
            
            catch (Exception e)
            {
                StateMessages.showErrorMessage();
                System.exit(1);
            }     
            
            return buffer;
        }        
    }
   
   
    private static ConstructorParams unPackedParams =
        new ConstructorParams();    
        
    private Showing roots;
    
    
    private void setGlobalParams(ConstructorParams params)
    {
        unPackedParams = params;
    }
    
    
    private ConstructorParams getGlobalParams()
    {
        return unPackedParams;
    }    
           
           
    private Showing getRoots() 
    { 
        return roots; 
    }
    
    
    private void setRoots(double a, double b, double c)
    {
        roots = new Showing(a, b, c);
    }
       
       
    public String toString()
    {
        return getRoots().getResult();
    }
             
             
    public SqrEqual(ConstructorParams params)
    {   
        setGlobalParams(params);
        //getGlobalParams().setIsCalledWithParams(ConstructorParams.ON);   
    
        if (getGlobalParams().isShowGreeting()) 
        {
            StateMessages.showGreeting(); 
        }
         
        setRoots(getGlobalParams().getA(), 
            getGlobalParams().getB(), 
                getGlobalParams().getC());
        
        if (getGlobalParams().isShowABCD()) 
        { 
            getRoots().showABCD(); 
        }
    }      
         
         
    public SqrEqual() 
    {   
        //getGlobalParams().setIsCalledWithParams(ConstructorParams.OFF); 
        getGlobalParams().setIsShowD(ConstructorParams.ON);
        
        StateMessages.showGreeting();
                         
        setRoots(new Parser(Abcd.A).getResult(),                
            new Parser(Abcd.B).getResult(),                 
                new Parser(Abcd.C).getResult() );
                                            
        printError();
    }
      
      
    private static void print(String str) 
    { 
        System.out.println(str); 
    }
    
    
    private static void print() 
    { 
        System.out.println(); 
    }
    
    
    private static void printError(String str) 
    { 
        System.err.println(str); 
    }
    
    
    private static void printError() 
    { 
        System.err.println(); 
    }
}



             