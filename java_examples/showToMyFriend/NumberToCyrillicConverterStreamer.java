import java.util.Scanner;

public class NumberToCyrillicConverterStreamer {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLong()) {
                long currentNumber = scanner.nextLong();
                String cyrillic = NumberConverter.convertToCyrillic(currentNumber);
                System.out.println(cyrillic);
            }
        }
    }
}