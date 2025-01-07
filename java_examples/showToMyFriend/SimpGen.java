// Простой обобщённый класс с двумя параметрами типа: T и V
class TwoGen<T, V> {
    T obj1;
    V obj2;
    
    // Передать конструктору ссылки на объекты типа T и V
    TwoGen(T o1, V o2) {
        obj1 = o1;
        obj2 = o2;
    }
    
    // Показать типы T и V
    void showTypes() {
        System.out.println("Тип T: " + obj1.getClass().getName());
        System.out.println("Тип V: " + obj2.getClass().getName());
    }
    
    T getObj1() { return obj1; }
    V getObj2() { return obj2; }
}

// Продемонстрировать применение класса TwoGen
public class SimpGen {
    public static void main(String[] args) {
        TwoGen<Integer, String> tgObj = 
            new TwoGen<Integer, String> (88, "Обобщения");
            
        // Показать типы
        tgObj.showTypes();
        
        // Получить и показать значения
        int v = tgObj.getObj1();
        System.out.println("Значение: " + v);
        
        String str = tgObj.getObj2();
        System.out.println("Значение: " + str);
    }
}