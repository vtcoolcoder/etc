// This is a snippet

import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;

enum Gender { 
    MALE("мужской"), 
    FEMALE("женский"); 
    
    private String gender;
    
    Gender(String gender) { this.gender = gender; }
    
    @Override
    public String toString() { return gender; }
}


record Student(
        String name, 
        String surname, 
        String country, 
        String city, 
        int age, 
        int weight, 
        int height, 
        Gender gender
) implements Comparable<Student> {

    private static final Comparator<Student> COMPARATOR = 
            comparing(Student::gender)
            .thenComparing(Student::country)   
            .thenComparing(Student::city)
            .thenComparingInt(Student::age)
            .thenComparing(Student::surname)
            .thenComparing(Student::name)
            .thenComparingInt(Student::height)
            .thenComparingInt(Student::weight);

    private static final String FORMAT = """
        Пол: %s
        Имя: %s
        Фамилия: %s
        Страна: %s
        Город: %s
        Возраст: %d
        Вес: %d
        Рост: %d
        """;
        
    @Override
    public int compareTo(Student another) {
        return COMPARATOR.compare(this, another);
    }   
    
    @Override
    public String toString() {
        return FORMAT.formatted(
                "" + gender, 
                name, 
                surname, 
                country, 
                city, 
                age, 
                weight, 
                height);
    }        
}


var students = List.of(
        new Student("Алина", "Ильина", "Россия", "Казань", 20, 55, 165, Gender.FEMALE),
        new Student("Олег", "Петров", "Россия", "Москва", 20, 80, 180, Gender.MALE),
        new Student("Риолина", "Аксинина", "Норвегия", "Осло", 18, 50, 170, Gender.FEMALE),
        new Student("Григорий", "Иванов", "Белоруссия", "Минск", 25, 65, 180, Gender.MALE),
        new Student("Наталья", "Ильина", "Россия", "Санкт-Петербург", 30, 60, 180, Gender.FEMALE),
        new Student("Виктор", "Соколов", "США", "Твин-Пикс", 23, 70, 175, Gender.MALE),
        new Student("Вероника", "Андрюшина", "Италия", "Рим", 27, 63, 168, Gender.FEMALE),
        new Student("Юрий", "Цезарь", "Франция", "Париж", 24, 77, 179, Gender.MALE),
        new Student("Марина", "Любимова", "Польша", "Варшава", 26, 57, 167, Gender.FEMALE),
        new Student("Дмитрий", "Осягин", "Нидерланды", "Амстердам", 22, 71, 176, Gender.MALE)
);
        
Function<Map<?, List<Student>>, String> converting = map -> map.values().stream()
        .flatMap(List::stream)
        .map(Student::toString)
        .collect(joining("\n"));

var fmt = "Группировка по %s";
var out = ":%n%n%s";
var show = fmt + out + "\n" + fmt + out;

BiFunction<Map<?, List<Student>>, Map<?, List<Student>>, String> merger = 
        (gender, country) -> show.formatted(
                "полу", 
                converting.apply(gender), 
                "стране", 
                converting.apply(country));

var result = students.stream().collect(teeing(
        groupingBy(Student::gender), 
        groupingBy(Student::country), 
        merger));
        
var studentsByCountry = students.stream()
        .collect(groupingBy(Student::country));
        
/* Сортировка мапы:
var studentByCountryEntries = studentsByCountry.entrySet();
    studentByCountryEntries.stream()
        .sorted(Map.Entry.comparingByKey())
        или .sorted(Map.Entry.comparingByKey(comparator))
        или .sorted(Map.Entry.comparingByValue())
        или .sorted(Map.Entry.comparingByValue(comparator))
*/        

var namesByGender = students.stream().collect(
        groupingBy(Student::gender, mapping(Student::name, toList()))
);

var general = students.stream().collect(
        teeing(
                teeing(
                        groupingBy(Student::surname, mapping(Student::name, toList())),
                        groupingBy(Student::country, mapping(Student::city, toList())),
                        (namesBySurname, citiesByCountry) -> 
                                Stream.of(namesBySurname, citiesByCountry)
                                        .map(Map::entrySet)
                                        .flatMap(Set::stream)
                                        .collect(toMap(Map.Entry::getKey, Map.Entry::getValue))
                ),
                teeing(
                        groupingBy(Student::height, mapping(Student::weight, toList())),
                        groupingBy(Student::gender, mapping(Student::age, toList())),
                        (weightsByHeight, agesByGender) -> 
                                Stream.of(weightsByHeight, agesByGender)
                                        .map(Map::entrySet)
                                        .flatMap(Set::stream)
                                        .collect(toMap(Map.Entry::getKey, Map.Entry::getValue))
                ),
                (namesBySurnameAndCitiesByCountry, weightsByHeightAndAgesByGender) -> 
                        Stream.of(namesBySurnameAndCitiesByCountry, weightsByHeightAndAgesByGender)
                                .map(Map::entrySet)
                                .flatMap(Set::stream)
                                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue))
        )
);

var studentsByDifferentGroups = students.stream().collect(
    groupingBy(Student::gender,
        groupingBy(Student::country,
            groupingBy(Student::city, 
                groupingBy(Student::age, 
                    groupingBy(Student::surname, 
                        groupingBy(Student::name, 
                            groupingBy(Student::height, 
                                groupingBy(Student::weight)))))))));

// Рекурсивная распечатка мапы
public static <K, V> void printMap(Map<K, V> map) {
    map.forEach((k, v) -> {
        if (v instanceof Map<?, ?> subMap) {
            printMap(subMap);
        } else if (v instanceof Collection<?> collection){
            collection.forEach(System.out::println);
        }
    });
}    

var keys = new ArrayList<>()

public static <K, V> void printMap(Map<K, V> map) {
    for (var entry : map.entrySet()) {
        if (entry.getValue() instanceof Map<?, ?> subMap) {
            keys.add(entry.getKey());
            printMap(subMap);
        } else if (entry.getValue() instanceof Collection<?> collection) {
            Stream.of(keys, collection).forEach(System.out::println);
        }
    }
}   

{
    женский={
        Великобритания=Лондон,
        Норвегия=Осло
    },
    мужской={
        Россия=Казань,
        Польша=Варшава
    }
}

var map = Map.of(
    "женский", Map.of("Великобритания", "Лондон", "Норвегия", "Осло"),
    "мужской", Map.of("Россия", "Казань", "Польша", "Варшава")
)

stack = [
    {
        Великобритания=Лондон,
        Норвегия=Осло
    },
    {
        Россия=Казань,
        Польша=Варшава
    }
]

public static void printMap(final Map<?, ?> map) {
    final Deque<Map<?, ?>> stack = new ArrayDeque<>();
    int size = 0;
    int counter = 0;
    
    LOOP:
    while (true) {   
        for (var entry : (stack.size() > 0 ? stack.pop() : map).entrySet()) {              
            if (entry.getValue() instanceof Map<?, ?> subMap) {
                stack.push(subMap);
                size += subMap.size();
            } else {
                System.err.println(entry.getValue());     
                if (++counter >= size) {
                    break LOOP;
                }       
            }
        }    
    } 
}                                        
                                        
studentsByDifferentGroups.forEach((gender, exceptingGender) -> exceptingGender.forEach(
    (country, exceptingCountry) -> exceptingCountry.forEach(
        (city, exceptingCity) -> exceptingCity.forEach(
            (age, exceptingAge) -> exceptingAge.forEach(
                (surname, exceptingSurname) -> exceptingSurname.forEach(
                    (name, exceptingName) -> exceptingName.forEach(
                        (height, exceptingHeight) -> exceptingHeight.forEach(
                            (weight, students) -> students.forEach(
                                student -> System.out.printf(
                                    """
                                    Пол: %s
                                    Страна: %s
                                    Город: %s
                                    Возраст: %d
                                    Фамилия: %s
                                    Имя: %s
                                    Рост: %d
                                    Вес: %d%n
                                    """,
                                    gender.equals(Gender.MALE) 
                                        ? "мужской" 
                                        : "женский",
                                    country,
                                    city,
                                    age,
                                    surname,
                                    name,
                                    height,
                                    weight))))))))));