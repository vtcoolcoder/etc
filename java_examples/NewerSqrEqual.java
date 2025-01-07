import java.util.*;


public class SqrEqualRunner
{
    public static void main(String[] args)
    {
        final double LIMIT = 3.333;
        final double INC = 0.333;
        
        for (double i = -LIMIT; i <= LIMIT; i += INC)
        {
            for (double j = -LIMIT; j <= LIMIT; j += INC)
            {
                for (double k = -LIMIT; k <= LIMIT; k += INC)
                {
                    NewerSqrEqual.ConstructorParams params =
                        new NewerSqrEqual.ConstructorParams();
                        
                    params.setA(i);
                    params.setB(j);
                    params.setC(k);
                    params.setIsShowAbcd(true);
                    params.setIsShowGreeting(false);
                    
                    System.out.println(
                        new NewerSqrEqual(params).getResult());
                    System.out.println();
                }
            }
        }
        
        System.out.println(
            new NewerSqrEqual().getResult());
        System.out.println();
    }
}


public class NewerSqrEqual
{  
    private class Roots 
    {
        private double x1;
        private double x2;
        
        private static final String X1 = "X = ";
        private static final String X2 = "X2 = ";
        private static final String NO_ROOTS = "Нет корней!";
        
        private Roots(double x)
        {
            x1 = x;
        }
        
        private Roots(double x1, double x2)
        {
            this.x1 = x1;
            this.x2 = x2;
        }
        
        private double getX1() { return x1; }
        private double getX2() { return x2; }
    }
    
    private class Abcd
    {
        private double a;
        private double b;
        private double c;
        private double discriminant;
        
        private static final String A = "a: ";
        private static final String B = "b: ";
        private static final String C = "c: ";
        private static final String D = "D: ";
        
        private Abcd(double a, double b, double c)
        {
            this.a = a;
            this.b = b;
            this.c = c;
            
            discriminant = b*b - 4*a*c;
        }
        
        private double getA() { return a; }
        private double getB() { return b; }
        private double getC() { return c; }
        
        private double getDiscriminant() { return discriminant; }
        
        private void showA() { System.out.println(A + a); }
        private void showB() { System.out.println(B + b); }
        private void showC() { System.out.println(C + c); }
        private void showD() { System.out.println(D + discriminant); }
        private void showAll() { showA(); showB(); showC(); showD(); }
    }
    
    private class StateMessages
    {
        private static final String GREETING = 
            "Попытка решения квадратного уравнения...";
        private static final String PROMT = "Введите значение ";
        private static final String INVALID_VALUE = 
            "Введено некорректное значение!";
        private static final String ERROR_MESSAGE = "Непредвиденная ошибка!";
    }
    
    
    public static class ConstructorParams 
    {
        private double a;
        private double b;
        private double c;
    
        private boolean isShowGreeting;
        private boolean isShowAbcd;
    
        public void setA(double a) { this.a = a; }
        public void setB(double b) { this.b = b; }
        public void setC(double c) { this.c = c; }
        public void setIsShowGreeting(boolean isShowGreeting) 
        {
            this.isShowGreeting = isShowGreeting;
        }
        public void setIsShowAbcd(boolean isShowAbcd)
        {
            this.isShowAbcd = isShowAbcd;
        }
    
        public double getA() { return a; }
        public double getB() { return b; }
        public double getC() { return c; }
        public boolean getIsShowGreeting() { return isShowGreeting; }
        public boolean getIsShowAbcd() { return isShowAbcd; }
    }
  
    private Roots roots;
    private Abcd abcd;
       
       
    public NewerSqrEqual(ConstructorParams params)
    {   
        if (params.getIsShowGreeting())
        {
            showGreeting();
        }
        
        abcd = new Abcd(params.getA(), params.getB(), params.getC());
        
        if (params.getIsShowAbcd())
        {
            abcd.showAll();
        }
    }      
       
       
    public NewerSqrEqual() 
    {   
        showGreeting();
                         
        abcd = new Abcd(
            parser(
                getString(Abcd.A), 
                    Abcd.A),
                    
                        parser(
                            getString(Abcd.B), 
                                Abcd.B),
                                
                                    parser(
                                        getString(Abcd.C), 
                                            Abcd.C));
    }
      
      
    private void showGreeting()   
    {
        System.err.println();
        System.err.println(StateMessages.GREETING);
        System.err.println();
    }  
    
    
    private String getString(String promt)
    {
        String buffer = "";
        System.err.println(StateMessages.PROMT + promt);
        
        try
        {
            buffer = new Scanner(System.in).nextLine();
        }
        
        catch (Exception e)
        {
            System.err.println(StateMessages.ERROR_MESSAGE);
            System.exit(1);
        }     
        
        return buffer;
    }
   
    
    private double parser(String parsedString, String recursiveLabel)
    {
        double buffer = 0;
        
        try
        {
            buffer = Double.parseDouble(parsedString);
        }
        
        catch (Exception e)
        {
            System.err.println(StateMessages.INVALID_VALUE);
            
            buffer = parser(
                getString(recursiveLabel), 
                    recursiveLabel);
        }
        
        return buffer;
    }
 
    
    public String getResult()
    {           
        if (abcd.getDiscriminant() < 0)
        {
            return Roots.NO_ROOTS;
        }
        
        else if (abcd.getDiscriminant() == 0)
        {
            roots = new Roots(
                        -abcd.getB() /2* 
                            abcd.getA() );
                                
            return Roots.X1 + roots.getX1(); 
        }
        
        else
        {    
            double sqrtFromDiscriminant = 
                Math.sqrt(
                    abcd.getDiscriminant() );
            
            roots = new Roots(
                        ((-abcd.getB() - 
                            sqrtFromDiscriminant) /2*
                                abcd.getA()),
                                
                                    ((-abcd.getB() + 
                                        sqrtFromDiscriminant ) /2*
                                            abcd.getA()) );
            
            return 
                Roots.X1 + roots.getX1() + "\n" +
                Roots.X2 + roots.getX2();
        }       
    }
}



             