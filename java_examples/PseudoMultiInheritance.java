public class PseudoMultiInheritance {
    private static interface BarFoo { String foo(); }
    
    private static interface Foo extends BarFoo {
        default String foo() { return "Foo"; }
    }
    
    private static interface Bar extends BarFoo {
        default String foo() { return "Bar"; }
    }
    
    private static class FooBar implements Foo, Bar {
        public String foo() { return Foo.super.foo() + Bar.super.foo(); }
    }
    
    private static class FooImp implements Foo {}
    
    private static class BarImp implements Bar {}

    public static void main(String[] args) {
        Foo foo = new FooBar();
        Bar bar = new FooBar();
        FooBar fooBar = new FooBar();
        BarFoo barFoo = new FooBar();
        
        Foo fooImp = new FooImp();
        FooImp fooImpObj = new FooImp();
        BarFoo barFooFooImpObj = new FooImp();
        
        Bar barImp = new BarImp();
        BarImp barImpObj = new BarImp();
        BarFoo barFooBarImpObj = new BarImp();
        
        show(foo, 
             bar, 
             fooBar,
             barFoo, 
             fooImp, 
             fooImpObj,
             barFooFooImpObj, 
             barImp, 
             barImpObj,
             barFooBarImpObj);
    }
    
    private static void show(BarFoo ... items) {
        for (BarFoo item : items) { System.out.println(item.foo()); }
    }
}