public class MultiSelectSwitchDemo {
    public static void main(String[] args) {
        int select = Integer.parseInt(args[0]);
        
        switch (select) {
            case 0:
            case 1:
            case 2:
                System.out.println("0 | 1 | 2");
                break;
            case 3:
            case 4:
            case 5:
                System.out.println("3 | 4 | 5");
                break;
            default:
                System.out.println("Another");
        }
        
        // Альтернативный синтаксис для оператора switch
        
        switch (select) {
            case 0, 1, 2:
                System.out.println("0 | 1 | 2");
                break;
            case 3, 4, 5:
                System.out.println("3 | 4 | 5");
                break;
            default:
                System.out.println("Another");
        }
    }
}