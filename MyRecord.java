import java.util.Objects;
import java.util.Arrays;


public record MyRecord(int id,
                       String name,
                       String surname,
                       int age,
                       int weight,
                       int height) {

    private static int seqId;


    public static void main(String[] args) {
        MyRecord[] records = {
            new MyRecord("Аня", "Свиридова", 17, 67, 167),
            new MyRecord("Светлана", "Любимова", 21, 60, 170),
            new MyRecord("Алиса", "Малинова", 23, 55, 165),
            new MyRecord("Оля", "Еленова", 25, 60, 160),
            new MyRecord("Жанна", "Николаева", 33, 66, 163),
            new MyRecord("Кристина", "Уракова", 29, 58, 158),
            new MyRecord("Риолина", "Вестернова", 32, 63, 167)
        };

        Arrays.stream(records).forEach(System.out::println);
    }


    public MyRecord {
        validate(id);
        Objects.requireNonNull(name);
        Objects.requireNonNull(surname);
        validate(age);
        validate(weight);
        validate(height);
    }


    public MyRecord(String name,
                    String surname,
                    int age,
                    int weight,
                    int height) {
        this(++seqId, name, surname, age, weight, height);
    }


    @SuppressWarnings("preview") 
    public String toString() {
        return STR."""
                ID: \{id}
                Имя: \{name}
                Фамилия: \{surname}
                Возраст: \{age}
                Вес: \{weight}
                Рост: \{height}
                """;
    }


    @SuppressWarnings("preview")
    private static void validate(int value) {
        if (value <= 0) {
            throw new IllegalArgumentException(STR."""
                    Значение должно быть больше нуля!
                    Переданное значение: \{value}
                    """
            );
        }
    }
    
}
