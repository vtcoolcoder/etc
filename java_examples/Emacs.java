import java.util.Scanner;


public class Emacs
{
    private static final String MESSAGE = "Передаю привет, бро, из GNU Emacs'а!";

    public static void main(String[] args)
    {
        System.out.println(MESSAGE);
        String input = new Scanner(System.in).nextLine();
        System.err.println(input);

    }

}
