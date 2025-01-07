package observers;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Tester {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(Config.class);
        var subjects = context.getBeansOfType(Subject.class).values();
        subjects.forEach(Subject::notifyObservers);
    }
}