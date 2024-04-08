package mylib;


import java.io.Serial;
import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;


public record Student(
        int id,
        String name,
        String surname,
        int age,
        int height,
        int weight) implements Serializable
{
    @Serial
    private static final long serialVersionUID = 6283487951643796546L;
    
    private static final Set<Integer> EXISTING_UNIQUE_ID = new HashSet<>();
    
    private static final boolean ON = true;
    private static final boolean OFF = false;
    
    private static final int IDMIN = 1;
    private static final int AGEMIN = 1;
    private static final int AGEMAX = 120;
    private static final int HEIGHTMIN = 75;
    private static final int HEIGHTMAX = 250;
    private static final int WEIGHTMIN = 20;
    private static final int WEIGHTMAX = 300;
    
    private static final int MIN_STUDENT_COUNT = 1;
    private static final int MAX_STUDENT_COUNT = 2323;
    
    
    private static final String[] DEFAULT_FEMALE_NAMES = {
            "Ася", "Диана", "Вероника", "Настя", "Регина", "Оля", "Жанна", 
            "Елена", "Гузель", "Марина", "Ксения", "Яна", "Виталина", "Резеда", 
            "Светлана", "Алёна", "Лолита", "Татьяна", "Юлия", "Галина", "Элина", 
            "Любовь", "Надежда" };
            
    private static final String[] DEFAULT_FEMALE_SURNAMES = {
            "Иванова", "Андрюшина", "Сарычева", "Цветаева", "Закиева", "Романова", "Ильина", 
            "Мишустина", "Орлова", "Аксинина", "Кирюшина", "Жукова", "Тагирова", "Хабибуллина", 
            "Курочкина", "Тахавиева", "Сидорова", "Уракова", "Дурова", "Есенина", "Пушкина", 
            "Булгакова", "Тарасова" };
            
    private static final String[] DEFAULT_MALE_NAMES = { 
            "Николай", "Роман", "Григорий", "Борис", "Виталий", "Глеб", "Андрей", 
            "Александр", "Тимофей", "Вадим", "Пётр", "Сергей", "Дмитрий", "Влад", 
            "Степан", "Феофан", "Юрий", "Кирилл", "Евгений", "Константин", "Виктор", 
            "Антон", "Олег" };
            
    private static final String[] DEFAULT_MALE_SURNAMES = { 
            "Чугунов", "Осягин", "Жандаров", "Котляров", "Никифоров", "Потапов", "Поликарпов", 
            "Иванов", "Петров", "Сидоров", "Задорнов", "Зорин", "Смирнов", "Долматов", 
            "Любимов", "Дроздов", "Рябов", "Перепелов", "Веселов", "Журавлёв", "Степанов", 
            "Соловьёв", "Воробьёв" };
    
    
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
    
    private static final String CONSOLE_INLINE = "";
    private static final String WEB_INLINE = "<br>";
    
    private static final String TEMPLATE_FORMAT = """
    id: %%d%s
    Имя: %%s%s
    Фамилия: %%s%s
    Возраст: %%d%s
    Рост: %%d%s
    Вес: %%d%s
    """;
    
    private static final String SHOWFORMAT = selectShowFormat(ShowFormat.CONSOLE);
    private static final String WEBFORMAT = selectShowFormat(ShowFormat.WEB);
    
    
    private static int counter = 0;
    private static boolean isDeserialization = OFF;
    
    
    private enum ShowFormat { CONSOLE, WEB }
    private enum Sex { FEMALE, MALE }
    private record FullName(String name, String surname) {}
    private record WhatMode(boolean isTestMode, int studentCount) {}
    
    
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
    
    
    public static void main(String[] args) {
        WhatMode whatMode = isRunningTestMode(args); 
        Void aVoid = whatMode.isTestMode() ? test() : showRandomStudents(whatMode.studentCount());    
    }
    
    
    private static WhatMode isRunningTestMode(final String[] args) {
        final WhatMode TEST_MODE = new WhatMode(true, -1);
    
        if (args.length == 0) { return TEST_MODE; }
        
        try {
            int studentCount = Integer.parseInt(args[0]);
            return isValidRange(studentCount) ? new WhatMode(false, studentCount) : TEST_MODE;    
        } catch (NumberFormatException e) {
            return TEST_MODE;
        }
    }
    
    
    private static boolean isValidRange(final int studentCount) {
        return studentCount >= MIN_STUDENT_COUNT && 
               studentCount <= MAX_STUDENT_COUNT;
    }
    
    
    private static Void test() {
        System.err.println("Запуск тестового режима...\n");
    
        final List<Student> STUDENTS = new ArrayList<>();
    
        final String[] NAMES = { "Геральт", "Йениффер", "Цири" };
        final String[] SURNAMES = { "Ведьмак", "Чародейка", "ХаосаДитя" };
        final int[] AGES = { 45, 35, 18 };
        final int[] HEIGHTS = { 180, 170, 165 };
        final int[] WEIGHTS = { 80, 60, 55 };
        
        final int STARTED_SIZE = NAMES.length;
        
        for (int i = 0; i < STARTED_SIZE; i++) {
            STUDENTS.add(new Student(NAMES[i], SURNAMES[i], AGES[i], HEIGHTS[i], WEIGHTS[i]));
        }
        
        final Student EXISTING_RANDOM_STUDENT = STUDENTS.get(
                new Random().nextInt(STARTED_SIZE));
        
        STUDENTS.add(new Student(EXISTING_RANDOM_STUDENT));
        
        final int STARTED_OFFSET = STUDENTS.size();
        
        STUDENTS.stream().forEach(System.out::println);
                
        System.err.println("Генерация случайных студентов...\n");
        
        for (int i = 0; i < 23 * 7; i++) {
            STUDENTS.add(new Student());
        }
        
        for (int i = STARTED_OFFSET; i < STUDENTS.size(); i++) {
            System.out.println(STUDENTS.get(i));
        }
        
        return null;
    }
    
    
    private static Void showRandomStudents(final int studentCount) {
        for (int i = 0; i < studentCount; i++) {
            System.out.println(new Student());
        }
        
        return null;
    }
    
    
    public Student() {
        this(getRandomFullName(),
             getRandomValue(AGEMIN, AGEMAX),
             getRandomValue(HEIGHTMIN, HEIGHTMAX),
             getRandomValue(WEIGHTMIN, WEIGHTMAX));
    }
    
    
    private Student(
            final FullName fullName, 
            final int age, 
            final int height, 
            final int weight) 
    {
        this(fullName.name(), fullName.surname(), age, height, weight);
    }
    
    
    public Student(final Student another) {
        this(another.name, another.surname, another.age, another.height, another.weight);
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
           
        EXISTING_UNIQUE_ID.add(id);
    }


    @Override
    public String toString() {
        return getFinalPresentation(ShowFormat.CONSOLE);
    }
    
    
    public String toWebString() {
        return getFinalPresentation(ShowFormat.WEB);
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
            if (EXISTING_UNIQUE_ID.contains(id)) {
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
    
    
    private static FullName getRandomFullName() {     
        return switch (getRandomSex()) {
            case FEMALE -> getRandomFullName(DEFAULT_FEMALE_NAMES, DEFAULT_FEMALE_SURNAMES);
            case MALE -> getRandomFullName(DEFAULT_MALE_NAMES, DEFAULT_MALE_SURNAMES);
        };
    }
    
    
    private static Sex getRandomSex() {
        final int randomSex = new Random().nextInt(2);
        return randomSex == 0 ? Sex.FEMALE : Sex.MALE;
    }
    
    
    private static FullName getRandomFullName(final String[] names, final String[] surnames) {
        return new FullName(getNameOrSurname(names), getNameOrSurname(surnames));
    }
    
    
    private static String getNameOrSurname(final String[] item) {
        return item[getRandomValue(item.length)];
    }
    
    
    private static int getRandomValue(final int limit) {
        return new Random().nextInt(limit);
    }
    
    
    private static int getRandomValue(final int min, final int max) {
        return new Random().nextInt(min, max + 1);
    }
    
    
    private static String selectShowFormat(final ShowFormat format) {
        return switch (format) {
            case CONSOLE -> getFormattingString(CONSOLE_INLINE);
            case WEB -> getFormattingString(WEB_INLINE);
        };
    }
    
    
    private static String getFormattingString(final String inline) {
        return TEMPLATE_FORMAT.formatted(inline, inline, inline, inline, inline, inline);
    }
    
    
    private String getFinalPresentation(final ShowFormat format) {
        return switch (format) {
            case CONSOLE -> getPresentationByFormat(SHOWFORMAT);
            case WEB -> getPresentationByFormat(WEBFORMAT);
        };
    }
    
    
    private String getPresentationByFormat(final String formattedItem) {
        return formattedItem.formatted(id, name, surname, age, height, weight);
    }
}
