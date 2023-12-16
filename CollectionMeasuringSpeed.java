import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashSet;


public class CollectionMeasuringSpeed {
    @FunctionalInterface
    private interface Dealable {
        void deal(Collection<Integer> collection);
    }

    private static final int COLLECTION_SIZE = 10_000_000;
    private static final String ELEMENTS_AMOUNT = "Количество элементов в каждой коллекции: ";
    private static final String INFO = "Время работы: ";
    private static final String COLLECTIONS_FILL = "Заполнение коллекций...";
    private static final String COLLECTION_TYPE = "Тип коллекции: ";
    private static final String OPERATION = "Операция: ";
    private static final String FILL = "заполнение";
    private static final String SEPARATOR = " | ";

    private static List<Integer> arrayList = new ArrayList<>();
    private static List<Integer> linkedList = new LinkedList<>();
    private static Set<Integer> hashSet = new HashSet<>(); 
    private static String currentOperation = FILL;


    public static void main(String[] args) {
        showInfo();
        measureTime(arrayList, CollectionMeasuringSpeed::initCollection);
        measureTime(linkedList, CollectionMeasuringSpeed::initCollection);
        measureTime(hashSet, CollectionMeasuringSpeed::initCollection);
    }
    
    
    private static void showInfo() {
        System.out.println(ELEMENTS_AMOUNT + COLLECTION_SIZE);
        System.out.println(COLLECTIONS_FILL);
    }
    
    
    private static void measureTime(Collection<Integer> collection, Dealable subject) {
        final long before = System.currentTimeMillis();  
        subject.deal(collection);
        final long after = System.currentTimeMillis();
        collection.clear();
        System.out.print(COLLECTION_TYPE + collection.getClass().getSimpleName());
        System.out.print(SEPARATOR + OPERATION + currentOperation);
        System.out.println(SEPARATOR + INFO + (after - before));
    }
    
    
    private static void initCollection(Collection<Integer> collection) {  
        for (int i = 0; i < COLLECTION_SIZE; i++) {
            collection.add(i);
        }
    }
}