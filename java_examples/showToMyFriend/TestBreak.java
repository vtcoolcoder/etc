public class TestBreak
{
    public static void main(String[] args)
    {
        BASELABEL:
        {
            if (args.length == 0) { break BASELABEL; }
            System.out.println("Inside the label \"BASELABEL\".");
            
            MYLABEL:
            {
                if (args[0].equals("0")) { break MYLABEL; }
                System.out.println("\nInside the label \"MYLABEL\".");
                
                if (args[0].equals("1")) { break MYLABEL; }
                for (int i = 0; i < 10; i++) { System.out.print(i); }
                
                if (args[0].equals("2")) { break MYLABEL; }
                System.out.println(
                    "\nThe end of codeblock inside the label \"MYLABEL\"");
                    
                if (args[0].equals("3")) { break BASELABEL; }
            }
            
            MYLABEL2:
            {
                if (args[1].equals("0")) { break MYLABEL2; }
                System.out.println("\nInside the label \"MYLABEL2\".");
                
                if (args[1].equals("1")) { break MYLABEL2; }
                for (char i = 'A'; i <= 'Z'; i++) { System.out.print(i); }
                
                if (args[1].equals("2")) { break MYLABEL2; }
                System.out.println(
                    "\nThe end of codeblock inside the label \"MYLABEL2\"");
                    
                if (args[1].equals("3")) { break BASELABEL; }
            }
            
            System.out.println(
                "\nThe end of codeblock inside the label \"BASELABEL\"");
        }
        
        System.out.println("Bye!\n");
    }
}