import java.util.Scanner;

public class CyrillicToNumberConverterStreamer {
    private static final String REGEX = 
        "[^АаБбВвГгДдЕеЁёЖжЗзИиЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЪъЫыЬьЭэЮюЯя]+";

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                for (String word : line.toUpperCase().split(REGEX)) {
                    long currentNumber = NumberConverter.convertCyrillicToSource(word);
                    System.out.println(currentNumber);
                }
            }
        }
    }
}