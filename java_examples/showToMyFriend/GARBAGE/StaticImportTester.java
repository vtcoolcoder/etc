import static Contenter.*;


enum Contenter {
    RED("красный"), 
    GREEN("зелёный"), 
    YELLOW("жёлтый"), 
    ORANGE("оранжевый"), 
    BLUE("синий"), 
    WHITE("белый"), 
    BLACK("чёрный"), 
    PURPLE("лиловый"), 
    VIOLET("фиолетовый"), 
    GRAY("серый"), 
    BROWN("коричневый"), 
    TURQUOISE("бирюзовый"),
    PINK("розовый");
    
    private String name;
    
    public static int number = 23;
    
    Contenter(String name) { this.name = name; }
    
    @Override
    public String toString() { return name; }
      
    public static void hello() { System.out.println("Hello, World!"); }
}


public class StaticImportTester {
   public static void main(String[] args) {
       hello();
       System.out.println(number);
       System.out.printf("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n",
               RED, GREEN, YELLOW, ORANGE, BLUE, WHITE,
               BLACK, PURPLE, VIOLET, GRAY, BROWN, TURQUOISE, PINK);
   } 
}