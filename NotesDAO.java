// Это черновик! НУЖНО ДОРАБАТЫВАТЬ!

import static java.util.stream.Collectors.toList;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

//Bean
//Component
//Autowired
//Configuration
//ComponentScan
//PropertySource

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Optional;
import java.util.List;

import java.util.Random;

import somepackage.Note;


@Component
public class NotesDAO {

    private static final RANDOM = new Random();
/*
    private static final String ALL_NOTES = 
    """
    SELECT id, subject, note
    FROM java
    """;
    
    private static final String NOTE_BY_ID = ALL_NOTES + 
    """
    WHERE id = ?
    """;
    
    private static final String ADD_NOTE =
    """
    INSERT INTO java (subject, note)
    VALUES (?, ?)
    """;
    
    private static final String UPDATE_NOTE =
    """
    UPDATE java
    SET note = ?
    WHERE id = ?
    """;
    
    private static final String DELETE_NOTE =
    """
    DELETE FROM java
    WHERE id = ?
    """;
*/  
    
    private final JdbcTemplate jdbcTemplate;
    private final QueriesData queriesData;
    
    
    @Autowired
    public NotesDAO(JdbcTemplate jdbcTemplate, QueriesData queriesData) {
        this.jdbcTemplate = jdbcTemplate;
        this.queriesData = queriesData;
    } 
    
    
    public Optional<List<Note>> getAllNotes() {
        return Optional.ofNullable(jdbcTemplate.query(
                queriesData.getAllNotes(), 
                new BeanPropertyRowMapper<>(Note.class)));
    } 
    
    
    public Optional<List<Note>> getAllNotesWithoutId() {
        return Optional.ofNullable(jdbcTemplate.query(
                queriesData.getAllNotesWithoutId(),
                new BeanPropertyRowMapper<>(Note.class)));       
    }
    
    
    public Optional<List<String>> getDistinctSubjects() {
        return Optional.ofNullable(jdbcTemplate.query(
                queriesData.getDistinctSubjects(),
                new BeanPropertyRowMapper<>(Note.class))
                        .stream()
                        .map(Note::subject)
                        .collect(toList()));
    
    }
    
    
    public Optional<Note> getNoteById(int id) {       
        return jdbcTemplate.query(queriesData.getNoteById(), 
                new Object[]{id}, 
                new BeanPropertyRowMapper<>(Note.class))
                        .stream().findAny();
    }
    
    
    public Optional<Note> getNoteFragmentById(int id) {
        return jdbcTemplate.query(queriesData.getNoteFragment(),
                new Object[]{id}, 
                new BeanPropertyRowMapper<>(Note.class))
                        .stream().findAny();
    }
    
    
    public Optional<List<Note>> getNotesBySubject(String subject) {
        return Optional.ofNullable(jdbcTemplate.query(
                queriesData.getSpecificNote(),
                new Object[]{subject},
                new BeanPropertyRowMapper<>(Note.class)));
    }
    
    
    public Optional<Integer> getNotesBySubjectAmount()
    
    
    public Optional<Note> getRandomNote() {     
        List<Integer> allId = getAllId();   
        return getNoteById(allID.get(RANDOM.nextInt(allID.size())));
    }
    
    
    public void addNote(Note note) {
        jdbcTemplate.update(queriesData.getAddNote(), note.subject(), note.note());
    }
    
    
    public void updateNote(int id, String content) {
        jdbcTemplate.update(queriesData.getUpdateNote(), content, id);
    }
    
    
    public void safelyUpdateNote(int id, String content) {
        jdbcTemplate.update(queriesData.getTransactionUpdate(), id, content, id);
    }
    
    
    public void deleteNoteById(int id) {
        jdbcTemplate.update(queriesData.getDeleteNote(), id);
    }
    
    
    public void safelyDeleteNoteById(int id) {
        jdbcTemplate.update(queriesData.getTransactionDelete(), id, id);
    }
    
    
    private void backup(int id) {
        jdbcTemplate.update(queriesData.getBackup(), id);
    }
    
    
    private void rollback() {
        jdbcTemplate.update(queriesData.getTransactionRollBack());
    }
    
    
    private Optional<List<Integer>> getAllId() {
        return Optional.ofNullable(jdbcTemplate.query(
                queriesData.getAllId(),
                new BeanPropertyRowMapper<>(Note.class))
                        .stream()
                        .mapToInt(Note::id)
                        .collect(toList()));
        
    }
    
    
    /*
    public Optional<List<Note>> getAllNotes() {
        return Optional.ofNullable(jdbcTemplate.query(ALL_NOTES, 
                new BeanPropertyRowMapper<>(Note.class)));
                
                //jdbcTemplate.query(ALL_NOTES, new NoteMapper()));
    }
    
    
    public Optional<Note> getNoteById(int id) {
        return jdbcTemplate.query(NOTE_BY_ID, new Object[]{id}, new NoteMapper())
                .stream().findAny();
    }
    
    
    public void addNote(Note note) {
        jdbcTemplate.update(ADD_NOTE, note.subject(), note.note());
    }
    
    
    public void updateNote(int id, String updatedNote) {
        jdbcTemplate.update(UPDATE_NOTE, updatedNote, id);
    }
    
    
    public void deleteNote(int id) {
        jdbcTemplate.update(DELETE_NOTE, id);
    }
    */
}


@Configuration
@ComponentScan
@PropertySource
class Config {
    
    @Bean
    public DataSource dataSource(DBConfig config) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        
        dataSource.setDriverClassName(config.getDriverName());
        dataSource.setUrl(config.getURL());
        dataSource.setUsername(config.getUser());
        dataSource.setPassword(config.getPassword());
        
        return dataSource;
    }
    
    
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}


class NoteMapper implements RowMapper<Note> {

    @Override
    public Note mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Note(
                resultSet.getInt("id"),
                resultSet.getString("subject"),
                resultSet.getString("note")
        );   
    }
}