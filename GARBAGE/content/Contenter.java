package content;

public enum Contenter {
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