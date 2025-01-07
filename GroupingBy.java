import static java.util.stream.Collectors.*;

record Note(String subject, String note) {}

List<Note> notes = List.of(
        new Note("первая тема", "первая заметка"),
        new Note("первая тема", "вторая заметка"),
        new Note("первая тема", "третья заметка"),
        new Note("вторая тема", "первая заметка"),
        new Note("вторая тема", "вторая заметка"),
        new Note("вторая тема", "третья заметка"),
        new Note("третья тема", "первая заметка"),
        new Note("третья тема", "вторая заметка"),
        new Note("третья тема", "третья заметка")
);

Map<String, Set<String>> noteContentBySubjects = notes.stream()
        .collect(groupingBy(
                Note::subject, 
                TreeMap::new, 
                mapping(Note::note, toCollection(TreeSet::new))));
                
noteContentBySubjects.forEach((k, v) -> System.out.printf("%s | %s%n", k, v));
