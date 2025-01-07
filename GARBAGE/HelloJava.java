import javax.swing.*;

public class HelloJava {
    private static final String GREETING = "Hello, Java!";
    
    public static void main(String[] args) {
        JFrame frame = new JFrame(GREETING);
        JLabel label = new JLabel(GREETING, JLabel.CENTER);
        frame.add(label);
        frame.setSize(600, 600);
        frame.setVisible(true);
    }
}