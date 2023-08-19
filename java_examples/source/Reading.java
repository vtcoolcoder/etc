import java.util.Scanner;

public class Reading 
{
    public static void main(String[] args) 
    {
        Scanner in = new Scanner(System.in);
        System.out.print("Input a string: ");
        String s = in.nextLine();
        System.out.println("Your string: " + s);
        in.close();
    }
}
        
