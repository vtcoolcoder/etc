import java.util.Scanner;

public class PipeDemo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }
        
        scanner.close();
    }
}