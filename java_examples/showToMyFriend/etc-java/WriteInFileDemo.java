import java.io.FileNotFoundException;
import java.io.PrintStream;


public class WriteInFileDemo {
    public static void main(String[] args) {
        String filename = args.length != 0 ? args[0] : "fromWriteInFileDemo.txt";
        
        try (PrintStream ps = new PrintStream(filename)) {
            for (int i = 0; i < 10; i++) {
                ps.printf("Line №%d%n", i);
            }     
        } catch (FileNotFoundException ex) {
            System.err.printf("Файл %s не существует!", filename);
        }
    }
}


