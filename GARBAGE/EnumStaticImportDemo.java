public class EnumStaticImportDemo {
    private static final String FORMAT = "%s\t%d\t%s\n";
    private enum MyEnum { ONE, TWO, THREE }   
    
    public static void main(String[] args) {
        for (MyEnum myEnum : MyEnum.values()) {
            System.out.printf(FORMAT, 
                              myEnum, 
                              myEnum.ordinal(), 
                              myEnum.name());

        }      
    }
}