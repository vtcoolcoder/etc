package myjdbc;

public enum Queries {
    UNIQUE_SUBJECTS(
            """
            SELECT DISTINCT subject
            FROM java;
            """),
    
    NOTES(
            """
            SELECT note
            FROM java
            WHERE subject = '%s';
            """);
    
    private final String query;
    
    Queries(final String query) { this.query = query; }
    public String getQuery() { return query; }
}
