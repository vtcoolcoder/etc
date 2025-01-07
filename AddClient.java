import java.rmi.*;


public class AddClient {
    public static void main(String[] args) {
        try {
            String addServerURL = "rmi://" + args[0] + "/AddServer";
            AddServerIntf addServerIntf = (AddServerIntf) Naming.lookup(addServerURL);
            
            System.out.println("The first number: " + args[1]);
            double d1 = Double.valueOf(args[1]).doubleValue();
            System.out.println("The second number: " + args[2]);
            double d2 = Double.valueOf(args[2]).doubleValue();
            
            System.out.println("Сумма: " + addServerIntf.add(d1, d2));
        } catch (Exception e) {
            System.err.println("Exception: " + e);
        }
    }
}