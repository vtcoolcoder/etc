import java.io.*;
/**
 * В этом классе демонстрируется применение
 * документирующих комментариев
 * @author Герберт Шилдт
 * @version 1.2
*/
public class SquareNum
{
    /**
     * Этот метод возвращает квадрат числа.
     * Это многострочное описание. В нём можно ввести
     * столько строк, сколько потребуется.
     * @param num Значение, которое требуется возвести в квадрат
     * @return num Значение, возведённое в квадрат
    */
    public double square(double num)
    {
        return num * num;
    }
    
    /**
     * Этот метод вводит число, заданное пользователем
     * @return Введённое значение типа double
     * @exception Если при вводе возникает ошибка, 
     *            генерируется исключение типа IOException
     * @see IOException
    */
    public double getNumber() throws IOException
    {
        /*
         * Создать буферизированный поток чтения
         * типа BufferedReader, используя стандартный
         * поток ввода System.in
        */
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader inData = new BufferedReader(isr);
        String str = inData.readLine();
        return (new Double(str)).doubleValue();
    }
    
    /**
     * В этом методе демонстрируется применение
     * метода square()
     * @param args Не используется
     * @exception  Если при вводе возникает ошибка,
     *             генерируется исключение типа IOException
     * @see IOException
    */
    public static void main(String[] args) throws IOException
    {
        SquareNum ob = new SquareNum();
        double val;
        System.out.println("Введите значение для возведения в квадрат: ");
        val = ob.getNumber();
        val = ob.square(val);
        System.out.println("Квадрат значения равен " + val);
    }
}