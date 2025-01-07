public class LocalAnon {
    private static final String PREFIX = "Привет из ";
    private static final String POSTFIX = " класса!";
    private static final String INNER = "вложенного ";
    
    
    private static class InnerStatic {
        private static final String MESSAGE = 
            PREFIX + INNER + "статического" + POSTFIX;
            
        public void greeting() { System.out.println(MESSAGE); }
    }
    
    
    private class Inner {
        private static final String MESSAGE =
            PREFIX + INNER + "внутреннего" + POSTFIX;
            
        public void greeting() { System.out.println(MESSAGE); }
    }
    
    
    public static void main(String[] args) {
        // Анонимный класс
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
        new LocalDemo().greeting();
        
        // Вложенный статический класс
        new LocalAnon.InnerStatic().greeting();
        
        // Вложенный внутренний класс
        new LocalAnon().new Inner().greeting();
    }
}