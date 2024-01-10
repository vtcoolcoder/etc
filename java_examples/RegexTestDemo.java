import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class RegexTestDemo {
    private static final String TEXT = """
    23:23
    Хаоса Богов восславим и возрадуемся!
    Гой Ктулху! Великий коловращатель и пожиратель душ!
    Гой Хухор-Мухор! Могучий разрушитель и просветитель!
    Гой Тот! Всезнающий паук пустоты!
    Гой Гайа! Всецелая мать, из хаоса рождённая и гой порождающая!
    Гой Гайа! Гайа Гой!
    Гой Гайа! Гайа Гой!
    Гой Гайа! Гайа Гой!
    Гой Гайа! Гайа Гой!
    Гой! Гой! Гой! Гой! Гой!
    23:46\
    """;
    
    // \\w -- любая английская буква
    // \\d -- любая цифра
    private static final String REGEX = """
    (\\d{1,2}:\\d{1,2}[\n]?)\
    ([а-яА-ЯёЁ!, \n-]+)\
    (\\d{1,2}:\\d{1,2}[\n]?)\
    """;
    
    private static final String SHOWFORMAT = """
    Начало медитации: %s%n
    Воззвание к Хаоса Богам:%n%n%s%n
    Конец медитации: %s
    """;


    public static void main(String[] args) {
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(TEXT);
        
        while (matcher.find()) { 
            String begin = matcher.group(1);
            String appeal = matcher.group(2);
            String end = matcher.group(3);
            
            System.out.printf(SHOWFORMAT, begin, appeal, end);
        }
    }
}