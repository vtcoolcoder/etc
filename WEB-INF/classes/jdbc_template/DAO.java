package jdbc_template;


import static java.util.stream.Collectors.toMap;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Arrays;
import java.util.Random;

import java.sql.SQLException;
import java.sql.ResultSet;

import java.util.function.BiFunction;


@Component
public final class DAO {

    private static class NoteMapper {
        
        public static Note fullyMapRow(ResultSet resultSet, int i) throws SQLException {
            return new Note(
                    resultSet.getInt("id"),
                    resultSet.getString("subject"),
                    resultSet.getString("note"),
                    resultSet.getTimestamp("timestamp")
            );   
        } 
        
        
        private static BiFunction<ResultSet, Integer, String> getStrLambda(String qualifier) {
            return (rs, i) -> rs.getString(qualifier);
        }  
        
        
        private static BiFunction<ResultSet, Integer, Integer> getIntLambda(String qualifier) {
            return (rs, i) -> rs.getInt(qualifier);
        }
        
        
        public static <R extends BiFunction<ResultSet, Integer, R>> BiFunction<ResultSet, Integer, R> getLambda(String qualifier) {
            return switch (qualifier) {
                case "note", "fragment", "subject" -> (rs, i) -> rs.getString(qualifier);
                        //(BiFunction<ResultSet, Integer, String>) getStrLambda(qualifier);
                        
                case "amount", "id" -> (rs, i) -> rs.getInt(qualifier);
                        //(BiFunction<ResultSet, Integer, Integer>) getIntLambda(qualifier);
                        
                default -> throw new IllegalArgumentException(
                        "Переданный аргумент %s не входит в допустимое множество значений!"
                                .formatted(qualifier));
            };
        }
    }


    private static final Random RANDOM = new Random();
    
    
    private final JdbcTemplate jdbcTemplate;
    private final Queries queries;
    
    
    @Autowired
    public DAO(JdbcTemplate jdbcTemplate, Queries queries) {
        this.jdbcTemplate = jdbcTemplate;
        this.queries = queries;
    } 
    
    
    public void createNote(final Note... notes) {
        Arrays.stream(notes).forEach(this::addNote);
    }
    
    
    public List<Note> getAllNotes() {
        return jdbcTemplate.query(queries.getAllNotes(), NoteMapper::fullyMapRow);
    }
    
    
    public String getNoteContent(final int id) {
        return getNoteTemplate(id, queries.getNoteById(), "note");
    }
    
    
    public String getNoteFragment(final int id) {
        return getNoteTemplate(id, queries.getNoteFragment(), "fragment");
    }
    
    
    public List<String> getAllSubjects() {
        return jdbcTemplate.query(queries.getDistinctSubjects(),
                NoteMapper.getLambda("subject"));
    }
    
    
    public int getAllSubjectsAmount() {
        return getAmountTemplate(queries.getAllSubjectsAmount());     
    }
    
    
    public int getAllNotesAmount() {
        return getAmountTemplate(queries.getAllNotesAmount());
    }
    
    
    public Note getRandomNote() {             
        List<Integer> allId = getAllId();   
        
        return jdbcTemplate.query(queries.getRandom(),   
                new Object[] { allId.get(RANDOM.nextInt(allId.size())) },
                (rs, i) -> new Note(rs.getString("subject"), rs.getString("note")))
                        .stream().findAny().get();                    
    }
    
    
    public Map<String, Integer> getNotesBySubjectAmount() {
        @RequiredArgsConstructor
        @Getter
        class Helper {
            private final String subject;
            private final int amount;
        }
        
        return jdbcTemplate.queryForStream(queries.getNotesBySubjectAmount(),
                (rs, i) -> new Helper(
                        rs.getString("subject"), 
                        rs.getInt("amount")))
                .collect(toMap(Helper::getSubject, Helper::getAmount,
                        (k, v) -> v, LinkedHashMap::new));
                
    }
    
    
    public List<Note> getNotesBySubjects(final String... subjects) {
        final String REPEATED = "?, ";
        final int REPEATED_SIZE = REPEATED.length();
        
        int size = subjects.length;
        String primary = size <= 1 ? " = ?" : " IN (%s)";
        
        if (size > 1) {
            primary = primary.formatted(
                    REPEATED.repeat(size)
                            .substring(0, size*REPEATED_SIZE - (REPEATED_SIZE - 1)));
        }
        
        return jdbcTemplate.query(
                queries.getNotesBySubjects().formatted(primary),
                subjects,
                NoteMapper::fullyMapRow);      
    }
    
    
    public List<Note> getNotesByContent(String content) {
        content = wrapLikeContent(content);
        return jdbcTemplate.query(queries.getNotesByContent(), 
                new Object[]{content}, 
                NoteMapper::fullyMapRow);
    }
    
    
    public void updateNote(final String content, final int id) {
        jdbcTemplate.update(queries.getTransactionUpdate(), id, content, id);
    }
    
    
    public void deleteNote(final int id) {
        jdbcTemplate.update(queries.getTransactionDelete(), id, id);
    }
    
    
    public void updateNoteByLike(String like, final String content) {
        like = wrapLikeContent(like);
        jdbcTemplate.update(queries.getTransactionUpdateByContent(), 
                like, content, like);
    }
    
    
    public void deleteNoteByLike(String like) {
        like = wrapLikeContent(like);
        jdbcTemplate.update(queries.getTransactionDeleteByContent(), 
                like, like);
    }
    
    
    private void addNote(Note note) {
        jdbcTemplate.update(queries.getAddNote(), note.subject(), note.note());
    }
    
    
    private List<Integer> getAllId() {
        return jdbcTemplate.query(queries.getAllId(), 
                NoteMapper.getLambda("id"));
    }
    
    
    private static String wrapLikeContent(final String content) {
        return "%" + content + "%";
    }
    
    
    private static int getAmountTemplate(final String query) {
        return jdbcTemplate.query(query,
                NoteMapper.getLambda("amount"))
                        .stream().findAny().get(); 
    }
    
    
    private static String getNoteTemplate(int id, String query, String qualifier) {
        return jdbcTemplate.query(query, 
                new Object[] { id },
                NoteMapper.getLambda(qualifier))
                        .stream().findAny().get();
    }
}