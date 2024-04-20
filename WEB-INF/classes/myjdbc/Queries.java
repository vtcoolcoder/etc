package myjdbc;


public interface Queries {

    String AMOUNT = "amount";
    String BSUFFIX = "_backup";
    String BPREFIX = "source_";
    
    String JAVANOTES = "java";
    String BACKUP_JAVANOTES = JAVANOTES + BSUFFIX;
      
    String ID = "id";
    String SUBJECT = "subject";  
    String NOTE = "note";
    String TIMESTAMP = "timestamp";
       
    String S_ID = BPREFIX + ID;
    String S_SUBJECT = BPREFIX + SUBJECT;
    String S_NOTE = BPREFIX + NOTE;
    String S_TIMESTAMP = BPREFIX + TIMESTAMP;
    
    
    String NOTE_BY_ID = 
    """
    SELECT %s 
    FROM %s 
    WHERE %s = ?
    """.formatted(NOTE, JAVANOTES, ID);
    
    String DISTINCT_SUBJECTS = 
    """
    SELECT DISTINCT %s 
    FROM %s 
    ORDER BY LOWER(%s)
    """.formatted(SUBJECT, JAVANOTES, SUBJECT);
    
    String SPECIFIC_NOTE =
    """
    SELECT %s, %s
    FROM %s
    WHERE %s = ?
    ORDER BY %s
    """.formatted(SUBJECT, NOTE, JAVANOTES, SUBJECT, NOTE);
            
    String FULL_SPECIFIC =
    """
    SELECT *
    FROM %s
    WHERE %s = ?
    ORDER BY %s
    """.formatted(JAVANOTES, SUBJECT, NOTE);
    
    String ALL_NOTES = 
    """
    SELECT %s, %s 
    FROM %s 
    ORDER BY LOWER(%s), %s
    """.formatted(SUBJECT, NOTE, JAVANOTES, SUBJECT, NOTE);
    
    String ADD_NOTE = 
    """
    INSERT INTO %s (%s, %s) 
    VALUES (TRIM(?), TRIM(?))
    """.formatted(JAVANOTES, SUBJECT, NOTE);
            
    String UPDATE_NOTE = 
    """
    UPDATE %s 
    SET %s = TRIM(?) 
    WHERE %s = ?
    """.formatted(JAVANOTES, NOTE, ID);
            
    String DELETE_NOTE = 
    """
    DELETE FROM %s 
    WHERE %s = ?
    """.formatted(JAVANOTES, ID);
    
    String NOTES_BY_SUBJECT_AMOUNT =
    """
    SELECT %s, COUNT(%s) AS %s
    FROM %s
    GROUP BY %s
    """.formatted(SUBJECT, NOTE, AMOUNT, JAVANOTES, SUBJECT);
    
    String TEMPLATE_ALL_AMOUNT =
    """
    SELECT COUNT(%s%s) AS %s
    FROM %s
    """;
    
    String ALL_SUBJECTS_AMOUNT = TEMPLATE_ALL_AMOUNT
            .formatted("DISTINCT ", SUBJECT, AMOUNT, JAVANOTES);
    
    String ALL_NOTES_AMOUNT = TEMPLATE_ALL_AMOUNT
            .formatted("", NOTE, AMOUNT, JAVANOTES);
            
    String ALL_ID = 
    """
    SELECT DISTINCT %s
    FROM %s
    """.formatted(ID, JAVANOTES);
    
    String RANDOM =
    """
    SELECT %s, %s
    FROM %s
    WHERE %s = ?
    """.formatted(SUBJECT, NOTE, JAVANOTES, ID);
    
    String BACKUP =
    """
    INSERT INTO %s (%s, %s, %s, %s) 
        SELECT * 
        FROM %s 
        WHERE %s = ?
    """.formatted(BACKUP_JAVANOTES, 
            S_ID, S_SUBJECT, S_NOTE, S_TIMESTAMP, 
            JAVANOTES, ID);
}