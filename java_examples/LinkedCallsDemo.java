import java.util.List;
import java.util.ArrayList;

public class LinkedCallsDemo {
    private static class LinkedCaller {
        private List<Double> result;
        public LinkedCaller() { result = new ArrayList<>(); }
        public LinkedCaller add(double a, double b) {
            result.add(a + b);
            return this;
        }
        public LinkedCaller sub(double a, double b) {
            result.add(a - b);
            return this;
        }
        public LinkedCaller mul(double a, double b) {
            result.add(a * b);
            return this;
        }
        public LinkedCaller div(double a, double b) {
            result.add(a / b);
            return this;
        }
        public LinkedCaller mod(double a, double b) {
            result.add(a % b);
            return this;
        }
        public List<Double> getResult() { return result; }
    }
    
    public static void main(String[] args) {
        LinkedCaller link = new LinkedCaller();
        System.out.println(
            link.add(23, 23)
                .sub(46, 23)
                .mul(2.3, 10)
                .div(529, 23)
                .mod(69, 46)
                .getResult());
    }
}