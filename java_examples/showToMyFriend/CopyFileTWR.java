/* Версия программы CopyFile, в которой демонстрируется применение оператора try
 * с ресурсами и управление двумя ресурсами (в данном случае -- файлами)
 * в одном операторе try
*/

import java.io.*;

// TWR -- Try With Resources
public class CopyFileTWR {
    public static void main(String[] args) throws IOException {
        int i;
        
        // Сначала убедиться, что заданы оба файла
        if (args.length != 2) {
            System.out.println("Использование: CopyFileTWR откуда куда");
            return;
        }
        
        // Открыть два файла и управлять ими в операторе try
        try (FileInputStream fin = new FileInputStream(args[0]);
             FileOutputStream fout = new FileOutputStream(args[1])) {
            do {
                i = fin.read();
                if (i != -1) {
                    fout.write(i);
                }
            } while (i != -1);
        } catch (IOException exc) {
            System.out.println("Ошибка ввода-вывода: " + exc);
        }
    }
}