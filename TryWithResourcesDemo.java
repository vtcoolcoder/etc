import java.io.*;

public class TryWithResourcesDemo {
    public static void main(String[] args) throws Exception {
        final var first = new FileOutputStream("first.dat");
        final var second = new FileOutputStream("second.dat");
        final var third = new FileOutputStream("third.dat");
        
        try (first; second; third) {
            first.write(23);
            second.write(23);
            third.write(23);
        } catch (Exception e) {}
    }
}