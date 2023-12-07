import java.util.Scanner;

public class PipeDemo {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        }
    }
}