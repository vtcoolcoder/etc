public static void evaluate(Object obj) {
    switch (obj) {
        case String s -> System.out.println("String: " + s);
        case Integer i -> System.out.println("String: " + i);
        case Double d -> System.out.println("String: " + d);
        default -> System.out.println("String: " + obj);
    }
}


public static String evaluate(Object obj) {
    String result = "";
    
    return switch (obj) {
        case String s when s.length() > 5 -> {
                System.out.println(result = "Long String: " + s);
                yield result;
        }
        case String s -> {
                System.out.println(result = "Short String: " + s);
                yield result;
        }
        case Integer i when i > 100 -> {
                System.out.println(result = "Large Integer: " + i);
                yield result;
        }
        case Integer i -> {
                System.out.println(result = "Small Integer: " + i);
                yield result;
        }
        case List<?> l when l.size() > 2 -> {
                System.out.println(result = "Large List: " + l);
                yield result;
        }
        case List<?> l -> {
                System.out.println(result = "Small List: " + l);
                yield result;
        }
        case Double d -> {
                System.out.println(result = "String: " + d);
                yield result;
                
        }
        default -> {
                System.out.println(result = "Unknown: " + obj);
                yield result;
        }
    };
}


record Person(String name, int age) {}
record Note(String subject, String note) {}

public static void printPersonInfo(Object obj) {
    switch (obj) {
        case Person(String name, int age) when age >= 18 ->
                System.out.println(name + " взрослый");
        case Person(String name, int age) ->
                System.out.println(name + " несовершеннолетний");
        case Note(String subject, String note) when !subject.isEmpty() && !note.isEmpty() ->
                System.out.printf("Тема: %s | Заметка:%n%s%n%n", subject, note);
        default -> 
                System.out.println("Unknown...");
    }
}

// А в 22 версии появились unnamed переменные "_", 
// необходимые если данные переменные не будут использоваться,
// чтобы не выдумывать им название
try {
    ...
} catch (Exception _) {
    ...
}

_ -> new ArrayList<>()