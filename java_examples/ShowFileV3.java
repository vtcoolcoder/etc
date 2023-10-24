/* Отображение содержимого текстового файла.
 * Чтобы воспользоваться этой программой, укажите имя файла, 
 * который требуется просмотреть.
 * Например, чтобы просмотреть файл TEST.TXT,
 * введите в командной строке следующую команду:
 * java ShowFileV3 TEST.TXT
 *
 * В этом варианте программы код, открывающий и получающий доступ к файлу,
 * заключён в один блок оператора try.
 * Файл закрывается в блоке оператора finally.
*/

import java.io.*;

public class ShowFileV3 {
    public static void main(String[] args) {
        int i;
        FileInputStream fin = null;
        
        // Сначала убедиться, что имя файла указано
        if (args.length != 1) {
            System.out.println("Использование: ShowFileV3 имя_файла");
            return;
        }
        
        // В следующем фрагменте кода сначала открывается файл,
        // а затем из него читаются символы до тех пор, пока
        // не встретится признак конца файла
        try {
            fin = new FileInputStream(args[0]);
            
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
        } finally {
            // Закрыть файл в любом случае
            try {
                if (fin != null) {
                    fin.close();
                }
            } catch (IOException exc) {
                System.out.println("Ошибка закрытия файла!");
            }
        }
    }
}