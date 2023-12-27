package myjdbc;

public enum PrintFormatter {
    GENERAL_FORMAT(
            """
            Тема: %s
            Заметка:%n%s%n
            """),
            
    COMPACT_FORMAT("Тема: %s | Заметка:%n%s%n%n");
    
    private String format;
    
    PrintFormatter(final String format) { this.format = format; }
    public String getFormat() { return format; }
}