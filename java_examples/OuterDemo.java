interface Greetable {
    void greeting();
}

public class OuterDemo implements Greetable {
    private class InnerNonStatic implements Greetable { 
        public void greeting() { System.out.println("Hello from InnerNonStatic!"); }
    }
    
    private static class InnerStatic implements Greetable {
        public void greeting() { System.out.println("Hello from InnerStatic!"); }
    }
    
    public static void main(String[] args) {
        OuterDemo.InnerStatic anInnerStatic = new OuterDemo.InnerStatic();
        OuterDemo.InnerNonStatic anInnerNonStatic = 
            new OuterDemo().new InnerNonStatic();
        OuterDemo anOuterDemo = new OuterDemo();
        
        showGreetings(anInnerStatic, 
                      anInnerNonStatic, 
                      anOuterDemo,
                      new Greetable() {
                          public void greeting() { 
                              System.out.println("Hello from AnonymousClass!");
                          }
                      } );
    }
    
    private static void showGreetings(Greetable ... greetings) {
        for (Greetable greeting : greetings) { greeting.greeting(); }
    }
    
    public void greeting() { System.out.println("Hello from Outer!"); }
}