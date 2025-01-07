import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;


public class AlternativeWriteInTextFileDemo {
    public static void main(String[] args) {
        String filename = args.length != 0 ? args[0] : "temp-file.txt";
    
        try (PrintWriter pw = new PrintWriter(new File(filename))) {
            for (Student student : ShowStudents.STUDENTS) {
                pw.println(student);
            }
        } catch (FileNotFoundException ex) {
            System.err.printf("Файл %s не найден!", filename);
        }
    }
}