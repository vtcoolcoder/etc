public class NewerSwitchDemo {
    public static void main(String[] args) {
        int select = Integer.parseInt(args[0]);
    
        String name = switch (select) {
            case 0, 1, 2 -> "One";
            case 3, 4, 5 -> "Two";
            default -> "Another"; 
        };
        
        System.out.println(name);
    }
}