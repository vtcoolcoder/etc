package parser;

public class ArgumentIsNotParameterException extends RuntimeException {
    public ArgumentIsNotParameterException() { super(); }
    public ArgumentIsNotParameterException(String msg) { super(msg); }
}