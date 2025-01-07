package myjdbc;

public record Note(int id, String subject, String note) implements Comparable<Note> {
    public Note(String subject, String note) {
        this(-1, subject, note);
    }
   
    
    public Note(int id, String subject, String note) {
        this.id = id;
        this.subject = subject.trim();
        this.note = note.trim();
    }
    
    
    @Override
    public int compareTo(Note another) {
        return subject.equals(another.subject) 
                ? note.compareTo(another.note) 
                : subject.compareToIgnoreCase(another.subject);
    }


    @Override
    public String toString() { 
        return ("" + PrintFormatter.COMPACT_FORMAT).formatted(subject, note);
    }
}