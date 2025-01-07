public class PolymorphismWithInterfaceDemo {
    @FunctionalInterface
    private interface FooBar { String fooBar(); }
    
    private interface ChildFooBar extends FooBar { 
        default String fooBar() { return "ChildFooBar by default"; } 
    } 
    
    private static class BarFoo implements FooBar {
        public String fooBar() { return "BarFoo"; }
    }
    
    private static class ChildBarFoo extends BarFoo {
        public String fooBar() { return "ChildBarFoo"; }
    }
    
    private static class BarFooChild implements ChildFooBar {
        public String fooBar() { return "BarFooChild"; }
    }
    
    private static class ChildBarFooChild extends BarFooChild {}
    
    private static class MOCBarFooChild implements ChildFooBar {}
    
    private static class ChildMOCBarFooChild extends MOCBarFooChild {}
    
    private static class DoubleChildMOCBarFooChild extends ChildMOCBarFooChild {
        public String fooBar() { return "DoubleChildMOCBarFooChild"; }
    }
    
    public static void main(String[] args) {
        show(new BarFoo(),
             new ChildBarFoo(),
             () -> "Lambda",
             new BarFooChild(),
             new ChildBarFooChild(),
             new MOCBarFooChild(),
             new ChildMOCBarFooChild(),
             new DoubleChildMOCBarFooChild());
    }
    
    private static void show(FooBar ... items) {
        for (FooBar item : items) {
            System.out.print(item.getClass().getName() + " : ");
            System.out.println(item.fooBar()); 
        }
    }
}