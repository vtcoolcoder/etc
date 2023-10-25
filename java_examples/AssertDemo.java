/* Чтобы разрешить проверку утверждений во время выполнения,
 * следует указать параметр -ea в командной строке.
 * Например, для проверки утверждений в данной программе 
 * её необходимо запустить на выполнение по следующей команде:
 * java -ea AssertDemo
*/

// Продемонстрировать применение оператора assert
public class AssertDemo {
    static int val = 3;
    
    // Возвратить целочисленное значение
    static int getNum() {
        return val--;
    }
    
    public static void main(String[] args) {
        int n;
        
        for (int i = 0; i < 10; i++) {
            n = getNum();
            
            assert n > 0; // не подтвердится, если n == 0
            
            System.out.println("n равно " + n);
        }
    }
}