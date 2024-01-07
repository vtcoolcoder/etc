import java.util.Set;
import java.util.HashSet;
import java.io.Serializable;


public record Student(
        int id,
        String name,
        String surname,
        int age,
        int height,
        int weight) implements Serializable
{
    static final long serialVersionUID = 6283487951643796546L;
    private static final Set<Integer> existingUniqueId = new HashSet<>();
    
    private static final boolean ON = true;
    private static final boolean OFF = false;
    
    private static final int IDMIN = 1;
    private static final int AGEMIN = 1;
    private static final int AGEMAX = 120;
    private static final int HEIGHTMIN = 75;
    private static final int HEIGHTMAX = 250;
    private static final int WEIGHTMIN = 20;
    private static final int WEIGHTMAX = 300;
    
    private static final String AMOUNT = "Всего студентов: ";
    private static final String MUSTBE = "должен быть"; 
    private static final String WRONGIDRANGE = "id %s не меньше %d!".formatted(MUSTBE, IDMIN);
    private static final String WRONGIDUNIQUE = "id: %d уже существует!";
    private static final String PREFIX = "Некорректн";
    private static final String WRONGNAME = PREFIX + "ое имя!";
    private static final String WRONGSURNAME = PREFIX + "ая фамилия!";
    private static final String AHWFMT = "%s " + MUSTBE + " в диапазоне от %d до %d включительно!";
    private static final String WRONGAGE = AHWFMT.formatted("Возраст", AGEMIN, AGEMAX);
    private static final String WRONGHEIGHT = AHWFMT.formatted("Рост", HEIGHTMIN, HEIGHTMAX);
    private static final String WRONGWEIGHT = AHWFMT.formatted("Вес", WEIGHTMIN, WEIGHTMAX);
    
    private static final String REGEXP = """
    [AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtVvUuWwXxYyZz\
    АаБбВвГгДдЕеЁёЖжЗзИиЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЪъЫыЬьЭэЮюЯя]+""";
    
    private static final String SHOWFORMAT = """
    id: %d
    Имя: %s
    Фамилия: %s
    Возраст: %d
    Рост: %d
    Вес: %d
    """;
    
    
    private static int counter = 0;
    private static boolean isDeserialization = OFF;
    
    
    private record ServiceParams(
            String errorMsg,
            String nameOrSurname,
            int ageOrHeightOrWeight,
            int min,
            int max) 
    {
         ServiceParams(
                 final String errorMsg,
                 final String nameOrSurname) 
         { 
             this(errorMsg, nameOrSurname, -1, -1, -1);
         }
         
         
         ServiceParams(
                 final String errorMsg,
                 final int ageOrHeightOrWeight,
                 final int min,
                 final int max) 
         {
             this(errorMsg, "", ageOrHeightOrWeight, min, max);
         }
    }

   
    public Student(
            final String name,
            final String surname,
            final int age,
            final int height,
            final int weight)
    {
        this(++counter, name, surname, age, height, weight);
    }
    
    
    public Student {
        checkId(id);
        checkName(name = name.trim());
        checkSurname(surname = surname.trim());
        checkAge(age);
        checkHeight(height);
        checkWeight(weight);
           
        existingUniqueId.add(id);
    }


    @Override
    public String toString() {
        return SHOWFORMAT.formatted(id, name, surname, age, height, weight);
    }
    
    
    public static int getAmount() { return counter; }
    
    
    public static void showInfo() {
        System.out.println(AMOUNT + counter);
    }
    
    
    public static void show(final Student... students) {
        for (Student student : students) {
            System.out.println(student);
        }
    }
    
    
    public static void enableDeserializationMode() { isDeserialization = ON; }
    public static void disableDeserializationMode() { isDeserialization = OFF; }
    
    
    private static void checkId(final int id) {
        if (id < IDMIN) {
            throw new IllegalArgumentException(WRONGIDRANGE);
        } else if (! isDeserialization) {
            if (existingUniqueId.contains(id)) {
                throw new IllegalArgumentException(WRONGIDUNIQUE.formatted(id));
            }
        }
    }
    
    
    private static void checkName(final String name) { 
        checkNameOrSurname(new ServiceParams(WRONGNAME, name)); 
    }
    
    
    private static void checkSurname(final String surname) { 
        checkNameOrSurname(new ServiceParams(WRONGSURNAME, surname)); 
    }
    
    
    private static void checkAge(final int age) { 
        checkAgOrHtOrWt(new ServiceParams(WRONGAGE, age, AGEMIN, AGEMAX)); 
    }
    
    
    private static void checkHeight(final int height) { 
        checkAgOrHtOrWt(new ServiceParams(WRONGHEIGHT, height, HEIGHTMIN, HEIGHTMAX)); 
    }
    
    
    private static void checkWeight(final int weight) { 
        checkAgOrHtOrWt(new ServiceParams(WRONGWEIGHT, weight, WEIGHTMIN, WEIGHTMAX)); 
    }
    
    
    private static void checkNameOrSurname(final ServiceParams args) {
        if (! args.nameOrSurname().matches(REGEXP)) {
            throw new IllegalArgumentException(args.errorMsg());
        }
    }
    
    
    private static void checkAgOrHtOrWt(final ServiceParams args) {
        if (args.ageOrHeightOrWeight() < args.min() || 
            args.ageOrHeightOrWeight() > args.max()) {
            throw new IllegalArgumentException(args.errorMsg());
        }
    }
}