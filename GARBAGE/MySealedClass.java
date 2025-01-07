sealed interface MySuperInterface permits MyInterface, Beta, Gamma {}

sealed interface MyInterface extends MySuperInterface permits Alpha, Delta, MySubInterface {
    default void greeting() { System.out.println("This is a default implementation"); }
}

non-sealed interface MySubInterface extends MyInterface {}

public sealed class MySealedClass implements MySubInterface permits Alpha, Beta, Gamma {
    @Override
    public void greeting() { System.out.println("This is a class MySealedClass"); }
}

final class Alpha extends MySealedClass implements MyInterface {
    @Override
    public void greeting() { System.out.println("This is a class Alpha"); }
}

sealed class Beta extends MySealedClass implements MySuperInterface permits Delta {}

non-sealed class Gamma extends MySealedClass implements MySuperInterface {}

final class Delta extends Beta implements MyInterface {
    @Override
    public void greeting() { System.out.println("This is a class Delta"); }
}

// Не скомпилируется!
// non-sealed class Omega extends MySealedClass {}
// non-sealed class Omega extends Alpha {}
// non-sealed class Omega extends Beta {}
// non-sealed class Omega extends Delta {}
// non-sealed class Omega extends Gamma {}

final class Omega extends Gamma implements MySubInterface {}

sealed class Lambda extends Gamma implements MySubInterface permits Another {}

final class Another extends Lambda implements MySubInterface {}
