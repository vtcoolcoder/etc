/* Копирование файла.
 * Чтобы воспользоваться этой программой, укажите имена исходного и целевого файлов.
 * Например, чтобы скопировать файл FIRST.TXT в файл SECOND.TXT,
 * введите в командной строке следующую команду:
 * java CopyFile FIRST.TXT SECOND.TXT
*/

import java.io.*;

public class CopyFile {
    public static void main(String[] args) {
        int i;
        FileInputStream fin = null;
        FileOutputStream fout = null;
        
        // Сначала убедиться, что указаны имена обоих файлов
        if (args.length != 2) {
            System.out.println("Использование: CopyFile откуда куда");
            return;
        }
        
        // Копировать файл
        try {
            // Попытаться открыть файлы
            fin = new FileInputStream(args[0]);
            fout = new FileOutputStream(args[1]);
            
            do {
                i = fin.read();
                if (i != -1) {
                    fout.write(i);
                }
            } while (i != -1);
        } catch (IOException exc) {
            System.out.println("Ошибка ввода-вывода: " + exc);
        } finally {
            try {
                if (fin != null) {
                    fin.close();
                }
            } catch (IOException excII) {
                System.out.println("Ошибка закрытия файла ввода!");
            }
            
            try {
                if (fout != null) {
                    fout.close();
                }
            } catch (IOException excII) {
                System.out.println("Ошибка закрытия файла вывода!");
            }
        }
    }
}