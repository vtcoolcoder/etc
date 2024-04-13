package mylib;

public interface MyContent {
    String NAME = "Гой Гайа!";
    
    static String getRandomNumber() { return "" + new java.util.Random().nextDouble(); }
}

