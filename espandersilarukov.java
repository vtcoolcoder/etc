// This is a snippet

import static java.util.Map.entry;
import static java.util.Comparator.*;

record FullHards(double rgc, double base, double dynamo) implements Comparable<FullHards> {
    private static final Comparator<FullHards> COMPARATOR = comparingDouble(FullHards::base)
            .thenComparingDouble(FullHards::dynamo)
            .thenComparingDouble(FullHards::rgc);
            
    @Override
    public int compareTo(FullHards another) {
        return COMPARATOR.compare(this, another);
    }              
    
    public FullHards(FullHards accumulator, FullHards element) {
        this(
                accumulator.rgc + element.rgc, 
                accumulator.base + element.base, 
                accumulator.dynamo + element.dynamo
        );      
    }
}

private static final String FORMAT = """
    <tr><td>\
    %s\
    </td><td>\
    %.1f\
    </td><td>\
    %.1f\
    </td><td>\
    %.1f\
    </td></tr>
    """;

Map<Integer, FullHards> HARDS = Map.ofEntries(
        entry(1, new FullHards(4.6, 8.2, 6.9)),
        entry(2, new FullHards(5.7, 10.1, 8.6)),
        entry(3, new FullHards(7.0, 12.5, 10.5)),
        entry(4, new FullHards(8.3, 14.8, 12.5)),
        entry(5, new FullHards(9.8, 17.4, 14.7)),
        entry(6, new FullHards(11.3, 20.1, 17.0)),
        entry(7, new FullHards(13.0, 23.1, 19.5)),
        entry(8, new FullHards(14.8, 26.3, 22.2)),
        entry(9, new FullHards(16.7, 29.7, 25.1)),
        entry(10, new FullHards(18.8, 33.5, 28.2)),
        entry(11, new FullHards(20.9, 37.2, 31.4)),
        entry(12, new FullHards(23.2, 41.3, 34.8)),
        entry(13, new FullHards(25.6, 45.6, 38.4)),
        entry(14, new FullHards(28.1, 50.0, 42.2)),
        entry(15, new FullHards(30.7, 54.6, 46.1)),
        entry(16, new FullHards(33.4, 59.5, 50.1)),
        entry(17, new FullHards(36.3, 64.6, 54.5)),
        entry(18, new FullHards(39.2, 69.8, 58.8))
);

FullHards[] test = {
        new FullHards(33.4, 59.5, 50.1),
        new FullHards(30.7, 54.6, 46.1),
        new FullHards(23.2, 41.3, 34.8)
};

Map<FullHards, String> result = new TreeMap<>();

FullHards sumAllHards(FullHards... hards) {
    return Arrays.stream(hards).reduce(FullHards::new).get();
}

/*
Runnable one = () -> {
    for (int i = 1; i <= 18; ++i) {
        result.put(HARDS.get(i), "%d".formatted(i));
    }
};
*/

Runnable one = () -> IntStream.rangeClosed(1, 18)
        .forEach(i -> result.put(HARDS.get(i), "%d".formatted(i)));

/*
Runnable two = () -> {
    for (int i = 1; i <= 16; ++i) {
        for (int j = i + 2; j <= 18; ++j) {           
            result.put(
                    sumAllHards(HARDS.get(i), HARDS.get(j)), 
                    "%d, %d".formatted(i, j)
            );
        }
    }
};
*/

Runnable two = () -> IntStream.rangeClosed(1, 16)
        .forEach(i -> IntStream.rangeClosed(i + 2, 18)
                .forEach(j -> result.put(
                        sumAllHards(HARDS.get(i), HARDS.get(j)), 
                        "%d, %d".formatted(i, j))));
/*
Runnable three = () -> {
    for (int i = 1; i <= 14; ++i) {
        for (int j = i + 2; j <= 16; ++j) {
            for (int k = j + 2; k <= 18; ++k) {              
                result.put(
                        sumAllHards(HARDS.get(i), HARDS.get(j), HARDS.get(k)), 
                        "%d, %d, %d".formatted(i, j, k)
                );
            }
        }
    }
};
*/

Runnable three = () -> IntStream.rangeClosed(1, 14)
        .forEach(i -> IntStream.rangeClosed(i + 2, 16)
                .forEach(j -> IntStream.rangeClosed(j + 2, 18)
                        .forEach(k -> result.put(sumAllHards(
                                HARDS.get(i), HARDS.get(j), HARDS.get(k)),
                                "%d, %d, %d".formatted(i, j, k)))));

Consumer<PrintWriter> writer = writer -> result.forEach(
        (k, v) -> writer.printf(
                " ".repeat(8).concat(FORMAT), 
                v, 
                k.rgc(), 
                k.base(), 
                k.dynamo()
        )
);

try (PrintWriter updatedSilarukov = new PrintWriter("SILARUKOV-NEWER-UPDATE.TXT")) {
    updatedSilarukov.println("С 2 пружинами:\n");
    Stream.of(one, two).forEach(Runnable::run);
    writer.accept(updatedSilarukov);
    for (int i = 0; i < 7; ++i) { updatedSilarukov.println(); }
    result.clear();
    updatedSilarukov.println("С 3 пружинами:\n");
    Stream.of(one, two, three).forEach(Runnable::run);
    writer.accept(updatedSilarukov);
}


        
        