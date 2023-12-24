@interface MyDemoAnnotation {
    String name() default "defaultName";
    int id() default -1;
}


@MyDemoAnnotation(name = "AnnotationUsingDemo", id = 696)
public class AnnotationUsingDemo {    
    @MyDemoAnnotation
    private static String firstField = "first";
    
    @MyDemoAnnotation(name = "something")
    private static String secondField = "second";
    
    @MyDemoAnnotation(id = 0)
    private static String thirdField = "third";
    
    @MyDemoAnnotation(name = "anotherSomething", id = 1)
    private static String fourthField = "fourth";
    
    
    @MyDemoAnnotation(id = 23, name = "main")
    public static void main(@MyDemoAnnotation String[] args) {
        @MyDemoAnnotation(name = "flag") boolean flag = true;
        System.out.println(flag);
    }
}

