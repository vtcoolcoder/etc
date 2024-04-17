package myjdbc;


public enum PrintFormatter {
    GENERAL_FORMAT(Templates.FORMAT.formatted("\n")),          
    COMPACT_FORMAT(Templates.FORMAT.formatted(" | "));
    
    private interface Templates {
        String FORMAT = "Тема: %%s%sЗаметка:%%n%%s%%n%%n";
    }
    
    private String format;
    
    PrintFormatter(final String format) { this.format = format; }
    
    @Override public String toString() { return format; }
}

