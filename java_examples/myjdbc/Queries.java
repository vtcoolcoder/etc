public enum Queries {
    UNIQUE_SUBJECTS(
            """
            SELECT DISTINCT subject
            FROM javanotes;
            """),
    
    NOTE_FORMAT(
            """
            SELECT note
            FROM javanotes
            WHERE subject = '%s';
            """),
    
    GENERAL_FORMAT(
            """
            Тема: %s
            Заметка:%n%s%n
            """);
    
    private final String query;
    
    Queries(final String query) { this.query = query; }
    public String getQuery() { return query; }
}
