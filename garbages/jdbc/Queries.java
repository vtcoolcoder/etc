package myjdbc;


public interface Queries {
    
    String SUBJECT = "subject";
    String JAVANOTES = "java";
    String NOTE = "note";
    String ID = "id";
    String AMOUNT = "amount";
    
    
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
    SELECT %s, %s, %s
    FROM %s
    WHERE %s = ?
    ORDER BY %s
    """.formatted(ID, SUBJECT, NOTE, JAVANOTES, SUBJECT, NOTE);
    
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
    
    String ALL_SUBJECTS_AMOUNT =
    """
    SELECT COUNT(%s) AS %s
    FROM %s
    """.formatted(SUBJECT, AMOUNT, JAVANOTES);
}