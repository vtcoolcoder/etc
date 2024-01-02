import java.util.Scanner;
import java.util.function.Consumer;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class LearningFileOperation {
    private record HandleFileParams (
            InputStream input, 
            Consumer<Scanner> func, 
            String filename) implements AutoCloseable {
            
        HandleFileParams(Consumer<Scanner> func, String filename) 
                throws FileNotFoundException { 
            this(new FileInputStream(filename), func, filename); 
        }
        
        HandleFileParams(String filename) 
                throws FileNotFoundException {
            this(LearningFileOperation::printTextFile, filename);
        }
        
        HandleFileParams(InputStream input) {
            this(input, LearningFileOperation::printTextFile, "");
        }
        
        @Override
        public void close() throws IOException { input.close(); }
    }
    
    
    private static final String FORMAT = "Файл %s не найден!%n";
    private static final String IO_ERROR = "Ошибка ввода-вывода!";
    
    
    public static void main(String[] args) {
        if (args.length != 0) { 
            processCommandLineArgs(args); 
        } else { 
            processStdIn();
        }
    }
    
    
    private static void processCommandLineArgs(String[] args) {
        for (String filename : args) {
            try (HandleFileParams params = new HandleFileParams(filename)) {
                handleFile(params);
            } catch (FileNotFoundException ex) {
                System.err.printf(FORMAT, filename);
            } catch (IOException ex) {
                System.err.println(IO_ERROR);
            }
        }
    }
    
    
    private static void processStdIn() {
        handleFile(new HandleFileParams(System.in));
    }
    
    
    private static void handleFile(HandleFileParams params) {
        try (Scanner scanner = new Scanner(params.input())) {
            params.func().accept(scanner);
        } 
    }
    
    
    private static void printTextFile(Scanner scanner) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line);
        }
    }
}