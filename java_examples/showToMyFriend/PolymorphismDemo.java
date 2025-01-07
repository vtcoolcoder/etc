public class PolymorphismDemo {
    private static class Parent {
        public void greeting() { System.out.println("This is a parent class."); }
    }
    
    private static class ChildFirst extends Parent {
        public void greeting() { System.out.println("This is the first child class."); }
    }
    
    private static class SubChildFirst extends ChildFirst {
        public void greeting() { System.out.println("This is the first child subclass."); }
    }
    
    private static class ChildSecond extends Parent {
        public void greeting() { System.out.println("This is the second child class."); }
    }
    
    private static class SubChildSecond extends ChildSecond {
        public void greeting() { System.out.println("This is the second child subclass."); }
    }
    
    private static class ChildThird extends Parent {
        public void greeting() { System.out.println("This is the third child class."); }
    }
    
    private static class SubChildThird extends ChildThird {
        public void greeting() { System.out.println("This is the third child subclass."); }
    }
    
    public static void main(String[] args) {
        showGreeting(new Parent(),
                     new ChildFirst(),
                     new ChildSecond(),
                     new ChildThird(),
                     new SubChildFirst(),
                     new SubChildSecond(),
                     new SubChildThird());
    }
    
    private static void showGreeting(Parent ... items) {
        for (Parent item : items) { item.greeting(); }    
    }
} 