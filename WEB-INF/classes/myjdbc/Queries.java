package myjdbc;

public enum Queries {
    UNIQUE_SUBJECTS(
            """
            SELECT DISTINCT ?
            FROM ?
            """),
    
    NOTES(
            """
            SELECT ?
            FROM ?
            WHERE ? = '?';
            """);
    
    private final String query;
    
    Queries(final String query) { this.query = query; }
    public String getQuery() { return query; }
}
