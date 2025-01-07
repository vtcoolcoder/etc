// Класс NonGen -- функциональный эквивалент класса Gen без обобщений
class NonGen {
    Object obj; // объект obj теперь имеет тип Object
    
    // Передать конструктору ссылку на объект типа Object
    NonGen(Object o) { obj = o; }
    
    // Возвратить тип Object
    Object getObj() { return obj; }
    
    // Показать тип объекта obj
    void showType() {
        System.out.println("Объект obj относится к типу "
                           + obj.getClass()
                                .getName());
    }
}

// Продемонстрировать необобщённый класс
public class NonGenDemo {
    public static void main(String[] args) {
        NonGen iObj;
        
        // Создать объект типа NonGen и сохранить в нём объект типа Integer.
        // Выполняется автоупаковка
        iObj = new NonGen(88);
        
        // Показать тип данных, хранящихся в переменной iObj
        iObj.showType();
        
        // Получить значение переменной iObj, на этот раз требуется приведение типов
        int v = (Integer) iObj.getObj();
        System.out.println("Значение: " + v + '\n');
        
        // Создать другой объект типа NonGen и сохранить в нём объект типа String
        NonGen strObj = new NonGen("Тест без обобщений");
        
        // Показать тип данных, хранящихся в переменной strObj
        strObj.showType();
        
        // Получить значение переменной strObj, 
        // и в этом случае потребуется приведение типов
        String str = (String) strObj.getObj();
        System.out.println("Значение: " + str);
        
        // Этот код компилируется, но он принципиально неверный!
        iObj = strObj;
        v = (Integer) iObj.getObj(); // Ошибка во время выполнения!
    }
}