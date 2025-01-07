package org.learners.sqrequlation;


class StateMessages
{
    private static final String GREETING = 
        "Попытка решения квадратного уравнения: ax² + bx + c = 0 ...";       
    private static final String PROMPT = "Введите значение ";
    private static final String INVALID_VALUE = 
        "Введено некорректное значение!";    
    private static final String ERROR_MESSAGE = "Непредвиденная ошибка!";
    
    
    static boolean showGreeting()   
    {
        System.err.println(); 
        System.err.println(GREETING); 
        System.err.println();
        
        return false;
    }   
    
    
    static void showPrompt(String prompt) 
    { 
        System.err.println(PROMPT + prompt); 
    }
    
         
    static void showErrorMessage() 
    { 
        System.err.println(ERROR_MESSAGE); 
    }
   
    
    static void showInvalidValue() 
    { 
        System.err.println(INVALID_VALUE); 
    }
}   