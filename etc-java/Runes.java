public enum Runes {  
    FEHU("Феху", Att.FIRST), HAGALAZ("Хагалаз", Att.SECOND), TEIWAZ("Тейваз", Att.THIRD),
    URUZ("Уруз", Att.FIRST), NAUTIZ("Наутиз", Att.SECOND), BERKANA("Беркана", Att.THIRD),
    TURISAZ("Турисаз", Att.FIRST), ISA("Иса", Att.SECOND), AWAZ("Эваз", Att.THIRD),
    ANSUZ("Ансуз", Att.FIRST), JERA("Джера", Att.SECOND), MANNAZ("Манназ", Att.THIRD),
    RAIDO("Райдо", Att.FIRST), AIVAZ("Эйваз", Att.SECOND), LAGUZ("Лагуз", Att.THIRD),
    KENAZ("Кеназ", Att.FIRST), PERT("Перт", Att.SECOND), INGUZ("Ингуз", Att.THIRD),
    GEBO("Гебо", Att.FIRST), ALGIZ("Альгиз", Att.SECOND), ODAL("Одал", Att.THIRD),
    WUNJO("Вуньо", Att.FIRST), SOWILO("Совило", Att.SECOND), DAGAZ("Дагаз", Att.THIRD);
    
    
    public static void main(String[] args) { showAllCombinations(); }
    
    
    private static final Runes[] FIRST_ATT = { FEHU, URUZ, TURISAZ, ANSUZ, RAIDO, KENAZ, GEBO, WUNJO };
    private static final Runes[] SECOND_ATT = { HAGALAZ, NAUTIZ, ISA, JERA, AIVAZ, PERT, ALGIZ, SOWILO };
    private static final Runes[] THIRD_ATT = { TEIWAZ, BERKANA, AWAZ, MANNAZ, LAGUZ, INGUZ, ODAL, DAGAZ };
    
    private static final String SHOW_FORMAT = "%s -> %s -> %s%n";
    
    
    private enum Att { FIRST, SECOND, THIRD }
    private Att att;
    
    private String name;   
    
    
    Runes(final String name, final Att att) {
        this.name = name;
        this.att = att;
    }
    
    
    public String getName() { return name; }
    public int getAtt() { return att.ordinal() + 1; }
    
    
    public static void showAllCombinations() {
        showCombinations(FIRST_ATT, SECOND_ATT, THIRD_ATT);
        showCombinations(FIRST_ATT, THIRD_ATT, SECOND_ATT);
        showCombinations(SECOND_ATT, FIRST_ATT, THIRD_ATT);
        showCombinations(SECOND_ATT, THIRD_ATT, FIRST_ATT);
        showCombinations(THIRD_ATT, FIRST_ATT, SECOND_ATT);
        showCombinations(THIRD_ATT, SECOND_ATT, FIRST_ATT);
    }
    
    
    private static void showCombinations(final Runes[] firstRunes, 
                                         final Runes[] secondRunes, 
                                         final Runes[] thirdRunes) {
        for (Runes first : firstRunes) {
            for (Runes second : secondRunes) {
                for (Runes third : thirdRunes) {
                    showCurrentCombo(first, second, third);
                }
            }
        }
    }
    
    
    private static void showCurrentCombo(final Runes first, final Runes second, final Runes third) {
        System.out.format(SHOW_FORMAT, first.name, second.name, third.name);
    }
}