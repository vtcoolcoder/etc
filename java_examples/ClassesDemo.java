public class ClassesDemo {
    private static final String PREFIX = "Привет из ";
    private static final String POSTFIX = " класса!";
    private static final String INNER = "вложенного ";
    
    // Вложенный статический класс
    private static class InnerStatic {
        private static final String MESSAGE = 
            PREFIX + INNER + "статического" + POSTFIX;
            
        public void greeting() { System.out.println(MESSAGE); }
    }
    
    // Вложенный внутренний класс
    private class Inner {
        private static final String MESSAGE =
            PREFIX + INNER + "внутреннего" + POSTFIX;
            
        public void greeting() { System.out.println(MESSAGE); }
    } 
    
    public static void main(String[] args) {
         // Определение анонимного класса
         // и создание его экземпляра 
         // с последующим вызовом его метода
        new Object() {    
            private static final String MESSAGE = 
                PREFIX + "анонимного" + POSTFIX;      
                
            public void greeting() { System.out.println(MESSAGE); }
        }.greeting();
        
        // Локальный класс
        class LocalDemo {
            private static final String MESSAGE = 
                PREFIX + "локального" + POSTFIX;
                
            public void greeting() { System.out.println(MESSAGE); }
        }  
        
        // Создание анонимного объекта локального класса 
        // и вызов его метода      
        new LocalDemo().greeting();
        
        // Создание анонимного объекта вложенного статического класса 
        // и вызов его метода
        new ClassesDemo.InnerStatic().greeting();
        
        // Создание анонимного объекта вложенного внутреннего класса 
        // и вызов его метода
        new ClassesDemo().new Inner().greeting();
    }
}