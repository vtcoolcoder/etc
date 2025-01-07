public class HighlyInterestingDemo {
    @FunctionalInterface
    private interface MyFun<T> {
        void func(T refByInstancingObject);
    }
    
    
    private static class Messager {
        private static int id;
        private int currentId = ++id;
        private int currentSubId;
        private String message;
        
        public Messager(String msg) { 
            message = msg; 
        }
        
        public Messager(String msg, int subId) { 
            this(msg);
            currentSubId = subId;
        }
        
        public void saySomething() { say(message, currentSubId, currentId); }
    }
    
    
    private static class Hello extends Messager {  
        private static int id;
        //private int currentId = ++id;   
        public Hello() { super("Hello", id); }
    }
    
    
    private static class Greet extends Messager {
        private static int id;
        //private int currentId = ++id;   
        public Greet() { super("Greeting", id); }
    }
    
    
    private static class Bye extends Messager {
        private static int id;
        //private int currentId = ++id;   
        public Bye() { super("Bye", id); }
    }
    
    
    public static void main(String[] args) {
        showMessage(Hello::saySomething, new Hello());
        showMessage(Greet::saySomething, new Greet(), new Greet(), new Greet());
        showMessage(Bye::saySomething, new Bye(), new Bye());
    }
    
   
    private static <T> void showMessage(MyFun<T> fun, T... objs) {
        for (T obj : objs) {
            fun.func(obj);
        }
    }
    
    
    private static void say(String something, int sub, int sup) {
        System.out.println(something + ", I'm " + getSuffix(sub) + " of " + getSuffix(sup));
    }
    
    
    private static String getSuffix(int number) {
        return number + switch (number) {
            case 1 -> "st";
            case 2 -> "nd";
            case 3 -> "rd";
            default -> "th";
        };
    }
}