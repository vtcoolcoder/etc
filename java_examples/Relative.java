import java.util.ArrayList;


public class Relative
{
    public interface Function
    {
        void run(double n);
    }
    
    private static class RelationParams
    {
        private static final int NUMERATOR_BEGIN = 1;
        private static final int NUMERATOR_END = 9;
        private static final int DENOMERATOR_BEGIN = NUMERATOR_BEGIN + 1;
        private static final int DENOMERATOR_END = NUMERATOR_END + 1;
    } 
    
    private static final int FAIL_EXIT_CODE = 1;
    private static final int START_ARG_POSITION = 0;
    private static final int STOP_ARG_POSITION = 1;
    private static final int STEP_ARG_POSITION = 2;
    private static final int ARGS_LENGTH_LIMIT = 3;
      
    private static final String DIVISION_BY_ZERO = "Нельзя делить на ноль!";
    private static final String MUSTBE_3_ARGS = 
        "Задайте 3 обязательных аргумента командной строки: start stop step\n" +
        "-- для генерации числового ряда с шагом step от числа start до числа " +
        "stop -- включительно.";
    private static final String ILLEGAL_NUMBER_FORMAT = "Ошибка формата числа!";
        
    
    private static double start, stop, step;
    private static ArrayList<Double> relations, numbers;
    
        
    public static void main(String[] args)
    {      
        validateArgs(args);   
        generateNumbers();
        initRelations(new RelationParams());
        showResults(System.out::println, Relative::printPows);
    }
    
    
    private static void validateArgs(String[] args)
    {
        if (args.length != ARGS_LENGTH_LIMIT)
        {
            showErrorMessage(MUSTBE_3_ARGS);
        }
        try
        {
            start = Double.parseDouble(args[START_ARG_POSITION]);
            stop = Double.parseDouble(args[STOP_ARG_POSITION]);
            step = Double.parseDouble(args[STEP_ARG_POSITION]);
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
    
    
    private static void initRelations(RelationParams params)
    {
        relations = new ArrayList<>();
        
        for (double num = params.NUMERATOR_BEGIN; 
                    num <= params.NUMERATOR_END; ++num)
        {
            for (double den = params.DENOMERATOR_BEGIN; 
                        den <= params.DENOMERATOR_END; ++den)
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
            throw new IllegalArgumentException(DIVISION_BY_ZERO);
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