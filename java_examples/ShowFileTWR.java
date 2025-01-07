/* В этой версии программы ShowFile оператор try с ресурсами применяется
 * для автоматического закрытия файла
*/

import java.io.*;

// TWR -- Try With Resources
public class ShowFileTWR {
    public static void main(String[] args) {
        int i;
        
        // Сначала убедиться, что имя файла указано
        if (args.length != 1) {
            System.out.println("Использование: ShowFileTWR имя_файла");
            return;
        }
        
        // Ниже оператор try с ресурсами применяется сначала для открытия, а затем
        // для автоматического закрытия файла по завершении блока этого оператора
        try (FileInputStream fin = new FileInputStream(args[0])) {
            do {
                i = fin.read();
                if (i != -1) {
                    System.out.print((char) i);
                }
            } while (i != -1);
        } catch (FileNotFoundException exc) {
            System.out.println("Файл не найден!");
        } catch (IOException exc) {
            System.out.println("Произошла ошибка ввода-вывода!");
        }
    }
}