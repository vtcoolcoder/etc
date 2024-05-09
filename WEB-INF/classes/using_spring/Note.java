package using_spring;


public record Note(int id, String subject, String note) implements Comparable<Note> {
    public Note(String subject, String note) {
        this(-1, subject, note);
    }
   
    
    public Note(int id, String subject, String note) {
        this.id = id;
        this.subject = subject; 
        this.note = note; 
    }
    
    
    @Override
    public int compareTo(Note another) {
        return subject.equals(another.subject) 
                ? note.compareTo(another.note) 
                : subject.compareToIgnoreCase(another.subject);
    }


    @Override
    public String toString() { 
        return "ID: %d | Тема: %s | Заметка:%n%s%n".formatted(id, subject, note);
    }
}