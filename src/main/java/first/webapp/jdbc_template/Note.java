package jdbc_template;


import java.util.Comparator;
import static java.util.Comparator.*;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

import java.util.Date;
import java.text.SimpleDateFormat;


public record Note(
        int id, 
        String subject, 
        String note,
        Date timestamp
) implements Comparable<Note> {
        
    @Getter
    @Builder    
    public static class ExtraWrapping {
        private String delimiter;
        private String filler;
        private String node;
        private String separator;
    }
    
    private static final String DATEFORMAT = "EEE, d MMM yyyy HH:mm:ss";
    private static final SimpleDateFormat SDF = new SimpleDateFormat(DATEFORMAT);
    
    private static final ExtraWrapping DEFAULT_WRAPPING_PARAMS = ExtraWrapping.builder()
            .delimiter("|")
            .filler("-")
            .node("+")
            .separator(" ")
            .build();
            
    private static final Comparator<Note> COMPARATOR = 
            comparing(Note::subject, String::compareToIgnoreCase)
            .thenComparing(Note::note)
            .thenComparing(Note::timestamp)
            .thenComparingInt(Note::id);
   
   
    @Setter
    private static ExtraWrapping wrappingParams = DEFAULT_WRAPPING_PARAMS;
            
                   
    public Note(String subject, String note) {
        this(-1, subject, note, null);
    }
    
    public Note(Note another) {
        this(another.id, another.subject, another.note, another.timestamp);
    }
        
          
    @Override
    public int compareTo(Note another) {
        return COMPARATOR.compare(this, another);
    }


    @Override
    public String toString() { 
        String optId = id < 0 ? "" : "ID: %d".formatted(id);
        String optTimestamp = (timestamp == null) 
                ? "" 
                : "Дата: %s".formatted(SDF.format(timestamp)); 
                     
        return (wrapping(optId, "Тема: %s".formatted(subject), optTimestamp, "Заметка:")
                + "%n%s%n").formatted(note); 
    }
    
    
    public static String customWrapping(String... items) {
        return wrapping(wrappingParams, items);
    }
    
    
    private static String wrapping(String... items) {        
        return wrapping(DEFAULT_WRAPPING_PARAMS, items);
    }
    
    
    private static String wrapping(ExtraWrapping extra, String... items) {
        String delimiter = extra.getDelimiter();
        String filler = extra.getFiller();
        String node = extra.getNode();
        String separator = extra.getSeparator();
            
        StringBuilder primary = new StringBuilder();
        StringBuilder secondary = new StringBuilder();
            
        for (int i = 0; i < items.length; i++) {
            String item = items[i];
            
            if (item.isEmpty()) {
                continue;
            }
            
            boolean isFillExtra = (i == items.length - 1);   
            String filledPart = filler.repeat(item.length());
            
            //String baseResult = (delimiter + separator) + item + 
            //        (isFillExtra ? (separator + delimiter) : separator);
                    
            //var baseResult = getWrappingTempResult(isFillExtra, delimiter, separator, item);
            //var partResult = getWrappingTempResult(isFillExtra, node, filler, filledPart);
                    
            //String partResult = (node + filler) + filledPart + 
            //        (isFillExtra ? (filler + node) : filler);
            
            primary.append(getWrappingTempResult(isFillExtra, delimiter, separator, item));
            secondary.append(getWrappingTempResult(isFillExtra, node, filler, filledPart));
        }
             
        return String.join("\n", "" + secondary, "" + primary, "" + secondary);
    }
    
    
    private static String getWrappingTempResult(boolean isFillExtra, 
            String delimiter, String separator, String item
    ) {
        return new StringBuilder(delimiter)
                .append(separator)
                .append(item)
                .append(separator)
                .append(isFillExtra ? delimiter : "")
                .toString();
    }
}