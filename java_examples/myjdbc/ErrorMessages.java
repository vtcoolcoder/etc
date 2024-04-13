package myjdbc;

public enum ErrorMessages {
    INVALID_QUERY("Ошибка в запросе!"),
    NO_CONNECTION("Не удалось получить соединение!");
    
    private String message;
    
    ErrorMessages(final String message) { this.message = message; }
    @Override public String toString() { return message; }
}