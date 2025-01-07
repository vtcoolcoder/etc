// В этой версии класса Stats аргумент типа Т должен быть классом Number
// или наследуемым от него классом
class Stats<T extends Number> {
    T[] nums; // массив класса Number или его подкласса
    
    // Передать конструктору ссылку на массив элементов класса Number или его подкласса
    Stats(T[] o) { nums = o; }
    
    // Возвратить значение типа double в любом случае
    double average() {
        double sum = 0.0;
        
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i].doubleValue();
        }
        
        return sum / nums.length;
    }
}

// Продемонстрировать применение класса Stats
public class BoundsDemo {
    public static void main(String[] args) {
        Integer[] inums = { 1, 2, 3, 4, 5 };
        Stats<Integer> iObj = new Stats<Integer> (inums);
        double v = iObj.average();
        System.out.println("Среднее значение iObj равно " + v);
        
        Double[] dnums = { 1.1, 2.2, 3.3, 4.4, 5.5 };
        Stats<Double> dObj = new Stats<Double> (dnums);
        double w = dObj.average();
        System.out.println("Среднее значение dObj равно " + w);
        
        /* Этот код не скомпилируется, так как класс String
         * не является производным от класса Number
         * String[] strs = { "1", "2", "3", "4", "5" };
         * Stats<String> strObj = new Stats<String> (strs);
         * double x = strObj.average();
         * System.out.println("Среднее значение strObj равно " + x);
        */
    }
}