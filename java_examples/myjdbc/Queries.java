package myjdbc;

public enum Queries {
    UNIQUE_SUBJECTS(
            """
            SELECT DISTINCT subject
            FROM javanotes;
            """),
    
    NOTES(
            """
            SELECT note
            FROM javanotes
            WHERE subject = '%s';
            """);
    
    private final String query;
    
    Queries(final String query) { this.query = query; }
    public String getQuery() { return query; }
}
