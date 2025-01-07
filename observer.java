record Event(int id, String name, String surname, int age) {}

interface Observable {
    void accept(Event event);
}

class Main {
    private static final List<Observable> OBSERVERS = List.of(
            new FirstObserver(),
            new SecondObserver(),
            new ThirdObserver());            
            
    public void generate() {
        var event = new Event(0, "Dolores", "Riolinova", 23);
        OBSERVERS.forEach(observer -> observer.accept(event));
    }
}

class FirstObserver implements Observable {
    @Override
    public void accept(Event event) {
        System.err.println("The first observer:");
        System.out.printf(
                "Id: %d%nName: %s%nSurname: %s%nAge: %d%n",
                event.id(),
                event.name(),
                event.surname(),
                event.age());
    }
}

class SecondObserver implements Observable {
    @Override
    public void accept(Event event) {
        System.err.println("The second observer:");
        System.out.printf("%s %s%n", event.name(), event.surname());
    }
}

class ThirdObserver implements Observable {
    @Override
    public void accept(Event event) {
        System.err.println("The third observer:");
        System.out.printf("%d %d%n", event.id(), event.age());
    }
}