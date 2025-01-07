public class MyNumber
{
    @FunctionalInterface
    public interface LmbNumb
    {
        double getValue();
    }
    
    @FunctionalInterface
    public interface LmbStr
    {
        String getMessage();
    }
    
    private static final String GREETING =
        "Я Русский Православный Человек и Мне так НРАВИТСЯ!!!";
    private static final String APPEAL =
        "Хаоса Богов восславим и возрадуемся!\n" +
        "Гой Ктулху! Великий коловращатель и пожиратель душ!\n" +
        "Гой Хухор-Мухор! Могучий разрушитель и просветитель!\n" +
        "Гой Тот! Всезнающий паук пустоты!\n" +
        "Гой Гайа! Всецелая мать, из хаоса рождённая и гой порождающая!\n" +
        "Гой Гайа! Гайа Гой!\n" +
        "Гой Гайа! Гайа Гой!\n" +
        "Гой Гайа! Гайа Гой!\n" +
        "Гой Гайа! Гайа Гой!\n" +
        "Гой! Гой! Гой! Гой! Гой!";    
    
    public static void main(String[] args)
    {
        LmbNumb myLmb = () -> 23.23;
        LmbStr myGreeting = () -> GREETING;
        LmbStr appealToChaosGods = () -> APPEAL;
        
        System.out.println(myLmb.getValue());
        System.out.println(myGreeting.getMessage());
        System.out.println(appealToChaosGods.getMessage());
    }
}