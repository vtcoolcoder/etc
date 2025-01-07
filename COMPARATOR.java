import static java.util.Comparator.*;


record Note(int id, String subject, String note, Date timestamp) implements Comparable<Note> {

    private static final Comparator<Note> COMPARATOR = 
            comparing(e -> e.subject, String::compareToIgnoreCase)
            .thenComparing(e -> e.note)
            .thenComparing(e -> e.timestamp)
            .thenComparingInt(e -> e.id);
            
    @Override
    public int compareTo(Note another) {
        return COMPARATOR.compare(this, another);
    }              
}