package org.learners.sqrequlation;

import java.util.Scanner;


class Parser
{
    private static final int FAIL_EXIT = 1;
    
    private String label;
    private double result;   
    
    
    Parser(String label)
    {
        this.label = label;
        result = parser(getString());
    }
   
      
    double getResult() 
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
        catch (NumberFormatException e)
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