public class AnonymousDemo {
    public static void main(String[] args) {
        System.out.println(new AnonymousDemo() {
            public String toString() { return "Hello from anonymous class!"; }
        } );
    }
}