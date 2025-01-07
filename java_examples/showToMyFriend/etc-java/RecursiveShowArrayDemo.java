import java.util.Arrays;


public class RecursiveShowArrayDemo {
    public static final String[] names = {
            "Оля", "Света", "Юля", "Аня", "Жанна", "Диана", "Марина", "Ева", "Ника"
    };
    
    
    public static void main(String[] args) {
        System.out.println(Arrays.toString(names) + "\n");
        
        show(names);
        System.out.println();
        
        showReverse(names);
        System.out.println();
        
        show(names);
        System.out.println();
        
        showReverse(names);
    }
    
    
    public static void show(final String[] names) {
        class I { static int i = 0; }
        
        if (I.i >= names.length) {
            I.i = 0;
            return;
        }
        
        System.out.println(names[I.i++]);
        show(names);
    }
    

    public static void showReverse(final String[] names) {
        class I { static int i = 0; }
        
        if (I.i < 0 - (names.length - 1)) { 
            I.i = 0; 
            return;
        }
        
        System.out.println(names[I.i-- + (names.length - 1)]); 
        showReverse(names);
    }
}

