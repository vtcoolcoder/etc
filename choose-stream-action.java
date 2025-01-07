enum Actions { FILTER, DROP_WHILE, TAKE_WHILE }

interface ChooseAct {
    static <T> Stream<T> act(Stream<T> stream, Predicate<T> predicate, Actions action) {
        return switch (action) {
            case FILTER -> stream.filter(predicate);
            case DROP_WHILE -> stream.dropWhile(predicate);
            case TAKE_WHILE -> stream.takeWhile(predicate);
        };
    }
}

class Helper {

    public static <T> void act(
            Collection<T> collection, 
            Predicate<T> predicate,
            Actions action,
            Consumer<T> termination
    ) {
        map(collection, predicate, action).forEach(termination);        
    }
    
    public static <T> void print(
            Collection<T> collection, 
            Predicate<T> predicate,
            Actions action
    ) {
        act(collection, predicate, action, System.out::println);       
    }
    
    public static <T> Stream<T> map(
            Collection<T> collection, 
            Predicate<T> predicate,
            Actions action
    ) {
        return ChooseAct.act(collection.stream(), predicate, action);   
    }
}

var numbers = IntStream.range(0, 10).boxed().toList();

Predicate<Integer> pred = e -> e % 2 == 0;

Stream.of(pred, pred.negate())
        .flatMap(predicate -> Arrays.stream(Actions.values())
                                    .flatMap(e -> Helper.map(numbers, predicate, e))
        ).forEach(System.out::println);

Arrays.stream(Actions.values())
        .forEach(e -> {
            Helper.print(numbers, predicate, e); 
            System.out.println();
        });


// Вроде так должно быть...
interface Action<T> {
    Stream<T> act(Stream<T> stream, Predicate<T> predicate); 
}

var numbers = IntStream.range(0, 10).boxed().toList();

Predicate<Integer> pred = e -> e % 2 == 0;

public static void execute(Collection<?> collection, Predicate<?> predicate, Action action) {
    action.act(collection.stream(), predicate).forEach(System.out::println);
}

execute(numbers, pred, Stream::filter)
execute(numbers, pred, Stream::dropWhile)
execute(numbers, pred, Stream::takeWhile)
