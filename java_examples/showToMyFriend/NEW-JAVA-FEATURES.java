record Vtszet(String name, int age) {}

Vtszet vtszet = new Vtszet("Виталя", 29);

System.out.println("Name: " + vtszet.name());
System.out.println("Age: " + vtszet.age());


String string = switch (age) {
    case 1, 2, 3 -> {
        System.out.println("1 | 2 | 3");
        yield "123";
    }
    case 4, 5, 6 -> {
        System.out.println("4 | 5 | 6");
        yield "456";
    }
    default -> {
        System.out.println("ANOTHER | ANOTHER | ANOTHER");
        yield "UNDEFINED";
    }
};