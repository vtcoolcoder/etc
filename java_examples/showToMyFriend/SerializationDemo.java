import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;


public class SerializationDemo {
    /* Сериализация -- отображение объекта на массив byte[]
     * Она нужна, чтобы, например, сохранить состояние объекта в файл,
     * либо передать его по сети и т.п.
     * Чтобы объекты некого класса можно было сериализовать,
     * данный класс должен реализовать интерфейс -- Serializable
     *
     * Если нужно некое поле запретить сериализовывать, 
     * следует данное поле пометить модификатором -- transient
     *
     * При сериализации объекта, лучше добавить в него поле 
     * и сгенерировать уникальное значение ему:
     *     private static final long serialVersionUID
     * Это поле необходимо также сериализовывать.
     * Значение данной константы следует изменять каждый раз, 
     * когда меняется структура полей класса.
     * Если значение данной константы у текущего класса в программе 
     * отличается от десериализованного объекта, тогда возникает ошибка.
     */
    private record Student(
            int id,
            String name,
            String surname,
            int age,
            int height,
            int weight
    ) implements Serializable {}
    
    private static final Student[] STUDENTS = {
            new Student(1, "Василий", "Иванов", 20, 180, 80),
            new Student(2, "Маргарита", "Романова", 18, 170, 55),
            new Student(3, "Кристина", "Матвеева", 21, 175, 60)
    };
            

    private static final String NO_FILE = "Файл не найден!";
    private static final String IO_ERROR = "Ошибка ввода-вывода!";
    private static final String NO_CLAZZ = "Класс не найден!";
    private static final String FILENAME = "serialization.bin";
    private static final String SERIALIZATING = "Сериализация...";
    private static final String DESERIALIZATING = "Десериализация...";
    private static final String SHOWING = "Отображение десериализованных данных...";
    

    public static void main(String[] args) throws InterruptedException {
        serialize(FILENAME);
        Thread.sleep(696);
        deserialize(FILENAME);
    }
    
    
    private static void serialize(final String filename) {
        System.err.println(SERIALIZATING);
        
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(STUDENTS);
        } catch (FileNotFoundException ex) {
            System.err.println(NO_FILE);
        } catch (IOException ex) {
            System.err.println(IO_ERROR);
        }
    }
    
    
    private static void deserialize(final String filename) {
        System.err.println(DESERIALIZATING);
    
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            if (ois.readObject() instanceof Student[] students) {
                showStudentInfo(students);
            }
        } catch (FileNotFoundException ex) {
            System.err.println(NO_FILE);
        } catch (IOException ex) {
            System.err.println(IO_ERROR);
        } catch (ClassNotFoundException ex) {
            System.err.println(NO_CLAZZ);
        }
    }
    
    
    private static void showStudentInfo(final Student... students) {
        System.err.println(SHOWING);
    
        final String FORMAT = """
        id: %d
        Имя: %s
        Фамилия: %s
        Возраст: %d
        Рост: %d
        Вес: %d%n
        """;
        
        for (Student student : students) {
            System.out.printf(
                    FORMAT, 
                    student.id(),
                    student.name(),
                    student.surname(),
                    student.age(),
                    student.height(),
                    student.weight());
        }
    }
}