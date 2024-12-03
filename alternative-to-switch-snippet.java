
@SuppressWarnings("unchecked")
static void test() {
    var switcher = Map.of(
            "Dog", (Runnable) () -> System.out.println("Hello, World!"),
            "Cat", (Consumer<String>) System.out::println,
            "Riolina", (Supplier<String>) () -> "My name is Mood",
            "Vtszet", (Predicate<String>) e -> e.length() > 7,
            "Hello", (Function<String, String>) e -> e + " " + e
    );
    
    for (var key : new ArrayList<>(switcher.keySet()) {{ add("ASSEMBLER"); }} ) {
        var action = switcher.getOrDefault(key, (Runnable) () -> System.out.println("DEFAULT"));
        
        switch (action) {
            case Runnable r -> r.run();
            case Consumer cs -> cs.accept("This is consumer");
            case Supplier sp -> System.out.println(sp.get());
            case Predicate pr -> System.out.println(pr.test("Predicate"));
            case Function fn -> System.out.println(fn.apply("Function"));
            default -> throw new IllegalArgumentException();
        }
    }
}