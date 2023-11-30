import java.util.Set;
import java.util.HashSet;

public class SetDemo {
    public static void main(String[] args) {
        Set<Integer> set1 = new HashSet<>();
        for (int i = 0; i < 10; i++) { set1.add(i); }
        System.out.print("Первое множество: ");
        System.out.println(set1);
        
        Set<Integer> set2 = new HashSet<>();
        for (int i = 5; i < 15; i++) { set2.add(i); }
        System.out.print("Второе множество: ");
        System.out.println(set2);
        
        // объединение множеств
        Set<Integer> union = new HashSet<>(set1);
        union.addAll(set2);
        System.out.print("Объединение множеств: ");
        System.out.println(union);
        System.out.println("Проверка: " + getSetUnion(set1, set2));
        
        // пересечение множеств
        Set<Integer> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        System.out.print("Пересечение множеств: ");
        System.out.println(intersection);
        System.out.println("Проверка: " + getSetIntersection(set1, set2));
        
        // вычитание множеств
        Set<Integer> differenceSet2FromSet1 = new HashSet<>(set1);
        differenceSet2FromSet1.removeAll(set2);
        System.out.print("Разность множеств (первое - второе): ");
        System.out.println(differenceSet2FromSet1);
        System.out.println("Проверка: " + getSetDifference(set1, set2));
        
        Set<Integer> differenceSet1FromSet2 = new HashSet<>(set2);
        differenceSet1FromSet2.removeAll(set1);
        System.out.print("Разность множеств (второе - первое): ");
        System.out.println(differenceSet1FromSet2);
        System.out.println("Проверка: " + getSetDifference(set2, set1));
        
        // вычитание пересечения множеств из их объединения
        Set<Integer> unionTmp = new HashSet<>(set1);
        unionTmp.addAll(set2);  
        Set<Integer> intersectionTmp = new HashSet<>(set1);
        intersectionTmp.retainAll(set2); 
        unionTmp.removeAll(intersectionTmp);
        Set<Integer> xorIntersection = unionTmp;
        
        System.out.print("Исключающее объединение множеств: ");   
        System.out.println(xorIntersection);
        System.out.println("Проверка: " + getSetXorUnion(set1, set2));
    }
    
    private static <T> Set<T> getSetXorUnion(Set<T> first, Set<T> second) {
        return getSetDifference(getSetUnion(first, second), 
                                getSetIntersection(first, second));
    }
    
    private static <T> Set<T> getSetUnion(Set<T> first, Set<T> second) {
        Set<T> tmp = new HashSet<>(first);
        tmp.addAll(second);
        return tmp;
    }
    
    private static <T> Set<T> getSetIntersection(Set<T> first, Set<T> second) {
        Set<T> tmp = new HashSet<>(first);
        tmp.retainAll(second);
        return tmp;
    }
    
    private static <T> Set<T> getSetDifference(Set<T> first, Set<T> second) {
        Set<T> tmp = new HashSet<>(first);
        tmp.removeAll(second);
        return tmp;
    }
}