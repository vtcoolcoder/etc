import static java.util.FormatProcessor.FMT;


public class NewerJava21And22FeatureDemo {

    private record Student(String name, String surname, int age) {}

    private static final String RECORD = "RECORD: Student(String name, String surname, int age)";
    private static final String PREFIX = "This is a";


    public static void main(String[] args) {
        var objects = new Object[] {
                null,
                new Student("Riolina", "Wood", 23),
                new Student("Alice", "Merton", 17),
                "Hello, World!",
                "Bye!",
                23.23,
                69.69,
                96.96F,
                2323L,
                2323,
                (short) 2323,
                (byte) 23,
                'G',
                true,
                new Object()
        };

        for (var obj : objects) {
            try {
                System.out.println(process(obj) + "\n\n");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    public static String process(Object obj) {
        return switch (obj) {
            case null -> {
                System.out.println(PREFIX + STR." \{obj}");
                yield STR."\{obj}".toUpperCase();
            }

            case Student(String name, String surname, int age) when 18 <= age -> {
                System.out.println(PREFIX + STR." Record:\n\{obj}");
                System.out.println(PREFIX + "n adult student...");
                System.out.println(STR."""
                        Name: \{name}
                        Surname: \{surname}
                        Age: \{age}
                        """
                );
                yield RECORD;
            }

            case Student(String name, String surname, int age) -> {
                System.out.println(FMT."""
                        Name: %s\{name}
                        Surname: %s\{surname}
                        Age: %d\{age}
                        """
                );
                yield RECORD;
            }

            case String s when 7 < s.length() -> {
                System.out.println(PREFIX + " long String.");
                yield STR."LONG_STRING: \{s}";
            }

            case String _ -> {
                System.out.println(PREFIX + " String.");
                yield STR."STRING: \{obj}";
            }

            case Double d when 23.23 == d -> {
                System.out.println(PREFIX + " Double.");
                System.out.println(PREFIX + " magic number!");
                yield STR."DOUBLE: \{d}";
            }

            case Double _ -> {
                System.out.println(PREFIX + " Double.");
                yield STR."DOUBLE: \{obj}";
            }

            case Float _ -> {
                System.out.println(PREFIX + " Float.");
                yield STR."FLOAT: \{obj}";
            }

            case Long _ -> {
                System.out.println(PREFIX + " Long.");
                yield STR."LONG: \{obj}";
            }

            case Integer _ -> {
                System.out.println(PREFIX + "n Integer.");
                yield STR."INTEGER: \{obj}";
            }

            case Short _ -> {
                System.out.println(PREFIX + " Short.");
                yield STR."SHORT: \{obj}";
            }

            case Byte _ -> {
                System.out.println(PREFIX + " Byte.");
                yield STR."BYTE: \{obj}";
            }

            case Character _ -> {
                System.out.println(PREFIX + " Character.");
                yield STR."CHARACTER: \{obj}";
            }

            case Boolean _ -> {
                System.out.println(PREFIX + " Boolean.");
                yield STR."BOOLEAN: \{obj}";
            }

            default -> {
                System.out.println(PREFIX + "n unknown type...");
                throw new IllegalArgumentException(STR."UNKNOWN TYPE: \{obj}");
            }
        };
    }

}
