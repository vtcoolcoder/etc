import java.util.Scanner;

    /**
    * Нужно продолжать рефакторинг:
    *     сменить имена некоторым методам на более точные;
    *     возможно, реорганизовать структуры данных;
    *     возможно, вместо использования конструкции "switch" можно иначе
    *     решить задачу множественного выбора 
    *     (например, через упаковку масок возможных значений в массив
    *     и их сравнение через последующий перебор в цикле);
    */

public class SqrEqual
{   
    public static void main(String[] args)
    {
        getGlobalParams().setIsDebugOn(true);
        getGlobalParams().setIsShowGreeting(true);
        getGlobalParams().setIsShowD(true);     
        
        System.out.println(new SqrEqual());
        
        for (int i = -1; i <= 1; i++)
        {
            for (int j = -1; j <= 1; j++)
            {
                for (int k = -1; k <= 1; k++)
                {
                    getGlobalParams().setA(i);
                    getGlobalParams().setB(j);
                    getGlobalParams().setC(k);  
                    getGlobalParams().setIsShowGreeting(false);
                    getGlobalParams().setIsShowD(false);
                    getGlobalParams().setIsShowABCD(true);
        
                    System.out.println(
                        new SqrEqual(getGlobalParams()));
                    print();
                }
            } 
        }        
    }
    
     
    private class DebugMessages
    {
        /*
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
        */
            
        protected enum DebugMessagesList
        {
            ALL_EQ_ZERO()
            {
                public String getContent()
                {
                    return 
                        "a == 0, b == 0, c == 0; -> 0 = 0 -> x -- любое число";
                }
            },
            
            ALL_NE_ZERO()
            {
                public String getContent()
                {
                    return 
                        "a != 0, b != 0, c != 0; -> ax² + bx + c = 0 -> STANDART";
                }
            },
            
            ONLY_FIRST_EQ_ZERO()
            {
                public String getContent()
                {
                    return 
                        "a == 0, b != 0, c != 0; -> bx + c = 0 -> x = -c/b";
                }
            },
            
            ONLY_FIRST_NE_ZERO()
            {
                public String getContent()
                {
                    return 
                        "a != 0, b == 0, c == 0; -> ax² = 0 -> x = 0";
                }
            },
            
            ONLY_SECOND_EQ_ZERO()
            {
                public String getContent()
                {
                    return 
                        "a != 0, b == 0, c != 0; -> ax² + c = 0 -> x² = -c/a";
                }
            },
            
            ONLY_SECOND_NE_ZERO()
            {
                public String getContent()
                {
                    return 
                        "a == 0, b != 0, c == 0; -> bx = 0 -> x = 0";
                }
            },
            
            ONLY_THIRD_EQ_ZERO()
            {
                public String getContent()
                {
                    return 
                        "a != 0, b != 0, c == 0; -> ax² + bx = 0";
                }
            },
            
            ONLY_THIRD_NE_ZERO()
            {
                public String getContent()
                {
                    return 
                        "a == 0, b == 0, c != 0; -> c != 0 -> NO_ROOTS";
                }
            };
            
            public String getContent()
            {
                return "";
            }
        };    
            
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
        
        /*    
        protected static final int ALL_EQ_ZERO = 0;
        protected static final int ALL_NE_ZERO = 1;
        
        protected static final int ONLY_FIRST_EQ_ZERO = 2;
        protected static final int ONLY_FIRST_NE_ZERO = 3;
        
        protected static final int ONLY_SECOND_EQ_ZERO = 4;
        protected static final int ONLY_SECOND_NE_ZERO = 5;
        
        protected static final int ONLY_THIRD_EQ_ZERO = 6;
        protected static final int ONLY_THIRD_NE_ZERO = 7;
        */
        
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
        
        //private static final int NOT_FOUND = -1;
        
        /*
        private static final int A = 0;
        private static final int B = 1;
        private static final int C = 2;
        */
        
        
        private enum Abc
        {
            A()
            {
                public int getIndex()
                {
                    return 0;
                }
            },
            
            B()
            {
                public int getIndex()
                {
                    return 1;
                }
            },
            
            C()
            {
                public int getIndex()
                {
                    return 2;
                }
            };
            
            public int getIndex()
            {
                return 0;
            }
        };
          
        
        private double a;
        private double b;
        private double c;
        
        private Matrix(double a, double b, double c)
        {
            this.a = a;
            this.b = b;
            this.c = c;
        }
        
        protected SetModeList getCase()
        {  
            SetModeList result = null;
            
            //for (int i = ALL_EQ_ZERO; i <= ONLY_THIRD_NE_ZERO; i++)
            
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
        
        /*
        private boolean isValid(int i)
        {
            return 
                ( isEqZero(a) == getMask(i)[A] ) &&
                ( isEqZero(b) == getMask(i)[B] ) &&
                ( isEqZero(c) == getMask(i)[C] ) ;
                
           **
           * Нужно затестить ещё такой вариант,
           * хотя он менее явно понимается:
           *     ( ! ( isEqZero(a) ^ getMask(i)[A] ) ) &&
           *     ( ! ( isEqZero(b) ^ getMask(i)[B] ) ) &&
           *     ( ! ( isEqZero(c) ^ getMask(i)[C] ) ) ;
           *
        }
        */
        
        private boolean isValid(int i)
        {
            return
                ( ! ( isEqZero(a) ^ getMask(i)[Abc.A.getIndex()] ) ) &&
                ( ! ( isEqZero(b) ^ getMask(i)[Abc.B.getIndex()] ) ) &&
                ( ! ( isEqZero(c) ^ getMask(i)[Abc.C.getIndex()] ) ) ;
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
        /*      
        private boolean isStandartMode;
        private boolean isLineEqualMode;
        
        private boolean isShowAnyNumberMode;
        private boolean isShowXEqualZeroMode;
        private boolean isShowNoRootsMode;
        */
        
        
        private enum ModeFlags
        {
            STANDART()
            {
                private boolean isStandartMode;
                
                public boolean getStatus()
                {
                    return isStandartMode;
                }
                
                public void setStatus(boolean aStatus)
                {
                    isStandartMode = aStatus;
                }
            },
            
            LINE_EQUAL()
            {
                private boolean isLineEqualMode;
                
                public boolean getStatus()
                {
                    return isLineEqualMode;
                }
                
                public void setStatus(boolean aStatus)
                {
                    isLineEqualMode = aStatus;
                }
            },
            
            ANY_NUMBER()
            {
                private boolean isShowAnyNumberMode;
                
                public boolean getStatus()
                {
                    return isShowAnyNumberMode;
                }
                
                public void setStatus(boolean aStatus)
                {
                    isShowAnyNumberMode = aStatus;
                }
            },
            
            X_EQUALS_ZERO()
            {
                private boolean isShowXEqualZeroMode;
                
                public boolean getStatus()
                {
                    return isShowXEqualZeroMode;
                }
                
                public void setStatus(boolean aStatus)
                {
                    isShowXEqualZeroMode = aStatus;
                }
            },
            
            NO_ROOTS()
            {
                private boolean isShowNoRootsMode;
                
                public boolean getStatus()
                {
                    return isShowNoRootsMode;
                }
                
                public void setStatus(boolean aStatus)
                {
                    isShowNoRootsMode = aStatus;
                }
            };
            
            private boolean status;
            
            public boolean getStatus()
            {
                return status;
            }
            
            public void setStatus(boolean aStatus)
            {
                status = aStatus;
            }
        };
        
            
        private SetMode(double a, double b, double c)
        {
            super(a, b, c);
            setCurrentMode(getCase());
        }        
        
        private void setCurrentMode(SetModeList list)
        {
            switch (list)
            {
                // a == 0, b == 0, c == 0
                case ALL_EQ_ZERO:
                    showDebugMessage(
                        DebugMessagesList.ALL_EQ_ZERO.getContent());
                    setShowAnyNumberMode();
                    break;
                    
                // a != 0, b != 0, c != 0    
                case ALL_NE_ZERO: 
                    showDebugMessage(
                        DebugMessagesList.ALL_NE_ZERO.getContent());
                    setStandartMode();
                    break;
                    
                // a == 0, b != 0, c != 0   
                case ONLY_FIRST_EQ_ZERO: 
                    showDebugMessage(
                        DebugMessagesList.ONLY_FIRST_EQ_ZERO.getContent());
                    setLineEqualMode();
                    break;
                    
                // a != 0, b == 0, c == 0  
                case ONLY_FIRST_NE_ZERO: 
                    showDebugMessage(
                        DebugMessagesList.ONLY_FIRST_NE_ZERO.getContent());
                    setShowXEqualZeroMode();
                    break;
                    
                // a != 0, b == 0, c != 0  
                case ONLY_SECOND_EQ_ZERO: 
                    showDebugMessage(
                        DebugMessagesList.ONLY_SECOND_EQ_ZERO.getContent());
                    setStandartMode();
                    break;
                    
                // a == 0, b != 0, c == 0   
                case ONLY_SECOND_NE_ZERO: 
                    showDebugMessage(
                        DebugMessagesList.ONLY_SECOND_NE_ZERO.getContent());
                    setShowXEqualZeroMode();
                    break;
                    
                // a != 0, b != 0, c == 0   
                case ONLY_THIRD_EQ_ZERO: 
                    showDebugMessage(
                        DebugMessagesList.ONLY_THIRD_EQ_ZERO.getContent());
                    setStandartMode();
                    break;
                    
                // a == 0, b == 0, c != 0    
                case ONLY_THIRD_NE_ZERO: 
                    showDebugMessage(
                        DebugMessagesList.ONLY_THIRD_NE_ZERO.getContent());
                    setShowNoRootsMode();
                    break;
            }
        }
         
        private void setShowAnyNumberMode() 
        {
            /*
            isShowAnyNumberMode = true;
            
            isStandartMode = false;
            isLineEqualMode = false;
            isShowXEqualZeroMode = false;
            isShowNoRootsMode = false;
            */
            
            ModeFlags.ANY_NUMBER.setStatus(true);
            
            ModeFlags.STANDART.setStatus(false);
            ModeFlags.LINE_EQUAL.setStatus(false);
            ModeFlags.X_EQUALS_ZERO.setStatus(false);
            ModeFlags.NO_ROOTS.setStatus(false);
        }
        
        private void setShowXEqualZeroMode() 
        {
            /*
            isShowXEqualZeroMode = true;
            
            isShowAnyNumberMode = false;
            isShowNoRootsMode = false;
            isStandartMode = false;
            isLineEqualMode = false;
            */
            
            ModeFlags.X_EQUALS_ZERO.setStatus(true);
            
            ModeFlags.ANY_NUMBER.setStatus(false);
            ModeFlags.NO_ROOTS.setStatus(false);
            ModeFlags.STANDART.setStatus(false);
            ModeFlags.LINE_EQUAL.setStatus(false);
        }
        
        private void setShowNoRootsMode() 
        {
            /*
            isShowNoRootsMode = true;
            
            isShowXEqualZeroMode = false;
            isShowAnyNumberMode = false;
            isStandartMode = false;
            isLineEqualMode = false;
            */
            
            ModeFlags.NO_ROOTS.setStatus(true);
            
            ModeFlags.X_EQUALS_ZERO.setStatus(false);
            ModeFlags.ANY_NUMBER.setStatus(false);
            ModeFlags.STANDART.setStatus(false);
            ModeFlags.LINE_EQUAL.setStatus(false);
        }
        
        private void setStandartMode() 
        {
            /*
            isStandartMode = true;
            
            isShowNoRootsMode = false;
            isLineEqualMode = false;
            isShowXEqualZeroMode = false;
            isShowAnyNumberMode = false;
            */
            
            ModeFlags.STANDART.setStatus(true);
            
            ModeFlags.NO_ROOTS.setStatus(false);
            ModeFlags.LINE_EQUAL.setStatus(false);
            ModeFlags.X_EQUALS_ZERO.setStatus(false);
            ModeFlags.ANY_NUMBER.setStatus(false);
        }
        
        private void setLineEqualMode() 
        {
            /*
            isLineEqualMode = true;
            
            isStandartMode = false;
            isShowNoRootsMode = false;
            isShowXEqualZeroMode = false;
            isShowAnyNumberMode = false;
            */
            
            ModeFlags.LINE_EQUAL.setStatus(true);
            
            ModeFlags.STANDART.setStatus(false);
            ModeFlags.NO_ROOTS.setStatus(false);
            ModeFlags.X_EQUALS_ZERO.setStatus(false);
            ModeFlags.ANY_NUMBER.setStatus(false);
        }
        
        protected boolean isStandartMode() 
        { 
            //return isStandartMode; 
            
            return ModeFlags.STANDART.getStatus();
        }
        
        protected boolean isLineEqualMode() 
        { 
            //return isLineEqualMode; 

            return ModeFlags.LINE_EQUAL.getStatus();
        }
        
        protected boolean isShowAnyNumberMode() 
        { 
            //return isShowAnyNumberMode; 

            return ModeFlags.ANY_NUMBER.getStatus();
        }
        
        protected boolean isShowXEqualZeroMode() 
        { 
            //return isShowXEqualZeroMode; 

            return ModeFlags.X_EQUALS_ZERO.getStatus();
        }
        
        protected boolean isShowNoRootsMode() 
        { 
            //return isShowNoRootsMode; 

            return ModeFlags.NO_ROOTS.getStatus();
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
        protected static final String X1 = "X = ";
        protected static final String X2 = "X2 = ";
        protected static final String X_IS_ZERO = "X = 0";
        protected static final String X_IS_ANY_NUMBER = "X -- любое число.";
        protected static final String COMMON_NO_ROOTS = "Нет корней!";
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
    {   /*  
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
        */
        
        protected enum MakeVisionList
        {
            FIRST_K,
            SECOND_K,
            THIRD_K,
            DISCRIMINANT,
            ROOT_ZERO,
            ROOT_ONE,
            ROOT_TWO,
            LINE_ROOT,
            ANY_NUMBER,
            EQUAL_ZERO,
            NO_ROOTS,
            OTHER_CASE
        };
        
        private GettingResult(double a, double b, double c) 
        { 
            super(a, b, c); 
        }
               
        protected String makeResult(MakeVisionList list)
        {
            String result = "";
            
            switch (list)
            {
                case FIRST_K: 
                    result =  A + getA(); 
                    break;
                    
                case SECOND_K: 
                    result =  B + getB(); 
                    break;
                    
                case THIRD_K: 
                    result =  C + getC(); 
                    break;
                    
                case DISCRIMINANT: 
                    result = D + getDiscriminant();
                    break;
                    
                case ROOT_ZERO: 
                    result = STR_NO_ROOTS; 
                    break;
                    
                case ROOT_ONE: 
                    result = X1 + getOneRoot(); 
                    break;
                    
                case ROOT_TWO: 
                    result = X1 + getFirstRoot() + "\n" + X2 + getSecondRoot();
                    break;
                    
                case LINE_ROOT:
                    result = X1 + getLineRoot();
                    break;
                    
                case ANY_NUMBER:
                    result = X_IS_ANY_NUMBER;
                    break;
                    
                case EQUAL_ZERO:
                    result = X_IS_ZERO;
                    break;
                    
                case NO_ROOTS:
                    result = COMMON_NO_ROOTS;
                    break;
                    
                case OTHER_CASE: 
                    result = makeResult(MakeVisionList.DISCRIMINANT) + "\n" +
                        selectGettedResult();
                    break;   
            }
            
            return result;
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
            return makeResult(MakeVisionList.ROOT_ZERO); 
        }
        
        private String discriminantEqualZero() 
        { 
            return makeResult(MakeVisionList.ROOT_ONE); 
        }
        
        private String discriminantGtThanZero() 
        { 
            return makeResult(MakeVisionList.ROOT_TWO); 
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
            return makeResult(MakeVisionList.LINE_ROOT);
        }
        
        private String getAnyNumber()
        {
            return makeResult(MakeVisionList.ANY_NUMBER);
        }
        
        private String getXEqualZero()
        {
            return makeResult(MakeVisionList.EQUAL_ZERO);
        }
        
        private String getNoRoots()
        {
            return makeResult(MakeVisionList.NO_ROOTS);
        }
        
        private String selectGettedResult()
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
                makeResult(MakeVisionList.OTHER_CASE) : selectGettedResult();
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
            print(makeResult(MakeVisionList.FIRST_K)); 
        }
        
        private void showB() 
        { 
            print(makeResult(MakeVisionList.SECOND_K)); 
        }
        
        private void showC() 
        { 
            print(makeResult(MakeVisionList.THIRD_K)); 
        }
        
        private void showD() 
        { 
            print(makeResult(MakeVisionList.DISCRIMINANT)); 
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
        
        private static boolean showGreeting()   
        {
            printError(); 
            printError(GREETING); 
            printError();
            return false;
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
        public static final boolean ON = true;
        public static final boolean OFF = false;
        
        private double a;
        private double b;
        private double c;
    
        private boolean isShowGreeting;
        private boolean isShowABCD;
        private boolean isShowD;
        private boolean isDebugOn;

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
            StateMessages.showPromt(label);
            
            try
            {
                result = new Scanner(System.in).nextLine();
            }
            
            catch (Exception e)
            {
                StateMessages.showErrorMessage();
                System.exit(1);
            }     
            
            return result;
        }        
    }
   
   
    private static ConstructorParams unPackedParams =
        new ConstructorParams();    
        
    private Showing roots;

    
    private static void setGlobalParams(ConstructorParams params)
    {
        unPackedParams = params;
    }
    
    
    private static ConstructorParams getGlobalParams()
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
    
    
    public SqrEqual(boolean isShowGreeting, double a, double b, double c)
    {
        if (isShowGreeting) 
        {
            StateMessages.showGreeting(); 
        }
        
        setRoots(a, b, c);
    }
             
             
    public SqrEqual(ConstructorParams params)
    {   
        this(params.isShowGreeting(),
            params.getA(), 
            params.getB(), 
            params.getC());
        
        setGlobalParams(params);
        
        /*
        if (getGlobalParams().isShowGreeting()) 
        {
            StateMessages.showGreeting(); 
        }
         
        setRoots(getGlobalParams().getA(), 
            getGlobalParams().getB(), 
                getGlobalParams().getC());
        */
        
        if (getGlobalParams().isShowABCD()) 
        { 
            getRoots().showABCD(); 
        }
    }      
         
         
    public SqrEqual() 
    {   
        this(getGlobalParams().isShowGreeting() ? 
            StateMessages.showGreeting() : false,
            new Parser(Abcd.A).getResult(),                
            new Parser(Abcd.B).getResult(),                 
            new Parser(Abcd.C).getResult());
        
        /*      
        if (getGlobalParams().isShowGreeting())
        {
            StateMessages.showGreeting();
        }
                         
        setRoots(new Parser(Abcd.A).getResult(),                
            new Parser(Abcd.B).getResult(),                 
                new Parser(Abcd.C).getResult() );
        */
                                            
        printError();
    }
      
      
    private static void print(Object str) 
    { 
        System.out.println(str); 
    }
    
    
    private static void print() 
    { 
        System.out.println(); 
    }
    
    
    private static void printError(Object str) 
    { 
        System.err.println(str); 
    }
    
    
    private static void printError() 
    { 
        System.err.println(); 
    }
}



             