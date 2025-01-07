import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;


@Retention(RetentionPolicy.RUNTIME)
@interface MyDemoAnnotation {
    String name() default "Параметр метода";
    int id() default -1;
}


@MyDemoAnnotation(name = "Демонстрация использования аннотаций", id = 696)
public class AnnotationUsingDemo {    
    @MyDemoAnnotation(id = 1, name = "Первое поле класса")
    private static String firstField = "first";
    
    @MyDemoAnnotation(name = "Второе поле класса", id = 2)
    private static String secondField = "second";
    
    @MyDemoAnnotation(id = 3, name = "Третье поле класса")
    private static String thirdField = "third";
    
    @MyDemoAnnotation(name = "Четвёртое поле класса", id = 4)
    private static String fourthField = "fourth";
    
    
    @MyDemoAnnotation(id = 23, name = "Главный метод")
    public static void main(@MyDemoAnnotation String[] args) {     
        showMetaInfo(AnnotationUsingDemo.class);  
    }
    
    
    private static void showMetaInfo(final AnnotatedElement... items) {
        for (AnnotatedElement item : items) {
            processAnnotatedElement(item);
        }
    }
    
    
    private static void processAnnotatedElement(final AnnotatedElement item) {
        if (item != null) {         
            printAnnotationFields(item); 
            tryRecursivelyRunShowMetaInfo(item);
        }
    }
    
    
    private static void printAnnotationFields(final AnnotatedElement item) {
        var annotation = item.getDeclaredAnnotation(MyDemoAnnotation.class);
        
        if (annotation != null && 
            annotation instanceof MyDemoAnnotation) {
            
            final String FORMAT = """
            Item: %s
            MyDemoAnnotation.id: %s
            MyDemoAnnotation.name: %s%n
            """;
            
            System.out.printf(FORMAT, item, annotation.id(), annotation.name());
        }      
    }
    
    
    private static void tryRecursivelyRunShowMetaInfo(final AnnotatedElement item) {
        if (item instanceof Class clazz) {
            showMetaInfo(clazz.getDeclaredFields());
            showMetaInfo(clazz.getDeclaredMethods());
        } else if (item instanceof Method method) {
            showMetaInfo(method.getParameters());
        }       
    }
}

