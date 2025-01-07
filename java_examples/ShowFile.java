/* Отображение содержимого текстового файла.
 * Чтобы воспользоваться этой программой, укажите имя файла, 
 * который требуется просмотреть.
 * Например, чтобы просмотреть файл TEST.TXT,
 * введите в командной строке следующую команду:
 * java ShowFile TEST.TXT
*/

import java.io.*;

public class ShowFile
{
    public static void main(String[] args)
    {
        int i;
        FileInputStream fin;
        
        // Сначала убедиться, что имя файла указано
        if (args.length != 1)
        {
            System.out.println("Использование: ShowFile имя_файла");
            return;
        }
        
        // Попытка открыть файл
        try
        {
            fin = new FileInputStream(args[0]);
        }
        catch (FileNotFoundException exc)
        {
            System.out.println("Невозможно открыть файл!");
            return;
        }
        
        // Теперь файл открыт и готов к чтению.
        // Далее из него читаются символы до тех пор,
        // пока не встретится признак конца файла
        try
        {
            do
            {
                i = fin.read();
                if (i != -1) 
                { 
                    System.out.print((char) i);
                }
            }
            while (i != -1);
        }
        catch (IOException exc)
        {
            System.out.println("Ошибка чтения из файла!");
        }
        
        // Закрыть файл
        try
        {
            fin.close();
        }
        catch (IOException exc)
        {
            System.out.println("Ошибка закрытия файла!");
        }
    }
}