import java.util.ArrayList;


public class Relative
{
    public interface Function
    {
        void run(double n);
    }
    
      
    private static final String ERROR = "Нельзя делить на ноль!";
    private static final String MUSTBE_3_ARGS = "Задайте 3 обязательных аргумента!";
    private static final String ILLEGAL_NUMBER_FORMAT = "Ошибка формата числа!";
    
    private static final int NUMERATOR_BEGIN = 1;
    private static final int NUMERATOR_END = 9;
    private static final int DENUMERATOR_BEGIN = NUMERATOR_BEGIN + 1;
    private static final int DENUMERATOR_END = NUMERATOR_END + 1;
    private static final int FAIL_EXIT_CODE = 1;
    
    
    private static double start;
    private static double stop;
    private static double step;
    private static ArrayList<Double> relations, numbers;
    
        
    public static void main(String[] args)
    {      
        checkArgs(args);   
        generateNumbers();
        initRelations(NUMERATOR_BEGIN, NUMERATOR_END, 
                      DENUMERATOR_BEGIN, DENUMERATOR_END);
        showResults(System.out::println, Relative::printPows);
    }
    
    
    private static void checkArgs(String[] args)
    {
        if (args.length != 3)
        {
            showErrorMessage(MUSTBE_3_ARGS);
        }
        try
        {
            start = Double.parseDouble(args[0]);
            stop = Double.parseDouble(args[1]);
            step = Double.parseDouble(args[2]);
        }
        catch (NumberFormatException ex)
        {
            showErrorMessage(ILLEGAL_NUMBER_FORMAT);
        }
    }
    
    
    private static void showErrorMessage(String msg)
    {
        System.err.println(msg);
        System.exit(FAIL_EXIT_CODE);
    }
    
    
    private static void generateNumbers()
    {
        numbers = new ArrayList<>();
        
        for (double i = start; i <= stop; i += step)
        {
            numbers.add(i);
        }
    }
    
    
    private static void initRelations(double numeratorBegin, double numeratorEnd,
                                      double denominatorBegin, double denominatorEnd)
    {
        relations = new ArrayList<>();
        
        for (double num = numeratorBegin; num <= numeratorEnd; ++num)
        {
            for (double den = denominatorBegin; den <= denominatorEnd; ++den)
            {
                double cachedRelation = relate(num, den);
                if (isPass(num, den, cachedRelation))
                {
                    continue;
                }
                relations.add(cachedRelation);
            }
        }
    }
    
    
    private static double relate(double numerator, double denominator)
    {
        if (denominator == 0)
        {
            throw new IllegalArgumentException(ERROR);
        }
        return numerator / denominator;
    }
    
      
    private static boolean isPass(double num, double den, double relation)
    {
        return (num == den) || relations.contains(relation);
    }
    
      
    private static void showResults(Function ... functions)
    {
        for (Function function: functions)
        {
           for (double relation: relations)
            {
                function.run(relation);
            }
        }
    }
    
    
    private static void printPows(double relation)
    {
        for (double base: numbers)
        {
            System.out.print(base + " ^ " + relation + " = ");
            System.out.println(Math.pow(base, relation));
        }
        System.out.println();
    }
}