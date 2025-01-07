public class NewJava {

    private static Object[] ARGS = {
        "Hello, World",
        23L,
        23,
        23.23,
        23.23F,
        'G',
        true,
        (short) 23,
        (byte) 23,
        new StringBuilder()
    };


    public static void main(String[] args) {
        for (var arg : ARGS) {
            System.out.println(process(arg));
        }
    }


    private static String process(Object obj) {
        return switch (obj) {
            case String _ -> "This is a String";
            case Double _ -> "This is a Double";
            case Float _ -> "This is a Float";
            case Long _ -> "This is a Long";
            case Integer _ -> "This is an Integer";
            case Short _ -> "This is a Short";
            case Byte _ -> "This is a Byte";
            case Character _ -> "This is a Character";
            case Boolean _ -> "This is a Boolean";
            default -> "This is an unknown type";
        };
    }
}
