package jdbc_template;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import lombok.Cleanup;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import static java.util.Map.entry;


public class Main {

    private static final String[] CUSTOM_SUBJECTS = {
            "break", "Git", "List", 
            "Spring Framework", "try", 
            "библиотеки", "изучение", "инкапсуляция",
            "исключения", "мапы", "многопоточность",
            "модули", "сериализация", "строки"
    };
    
    private static final Map<String, String> CREATING = Map.of(
            "первая", "В лесу родилась ёлочка",
            "вторая", "В лесу она росла",
            "третья", "Зимой и летом стройная",
            "четвёртая", "Зелёная была",
            "пятая", "Зайчишка зайчик серенький",
            "шестая", "Под ёлочкой скакал",
            "седьмая", "А Ведьмак Геральт из Ривии вновь нашёл своё дитя-предназначение -- Цириллу из Цинтры..."
    );
    
    private static final Map<String, String> UPDATING = Map.of(
            CREATING.get("первая"), "В лесу обитает леший",
            CREATING.get("вторая"), "А в доме завёлся домовой",
            CREATING.get("третья"), "Неорганы разводят людиков на питательную слизь",
            CREATING.get("четвёртая"), "Бирюзовой станет хули",
            CREATING.get("пятая"), "Пил деревенский самогон",
            CREATING.get("шестая"), "Под водочкой увидел белочку",
            CREATING.get("седьмая"), "Йениффер из Венгерберга сожгла часть войска Нильфгаарда"
    );
    
    private static final List<Note> RECORDS = new ArrayList<>();
    
    private static final String[] KEYWORDS = { "lombok", "монитор", "master" };
    
    private static final Note.ExtraWrapping PARAMS = Note.ExtraWrapping.builder()
            .delimiter("|")
            .filler("=")
            .node("*")
            .separator(" ")
            .build();
            
    static {
        Note.setWrappingParams(PARAMS);
    }
    
    private static String[] CLI_ARGS;
   
    
    public static void main(String[] args) {
        CLI_ARGS = args;
        
        @Cleanup
        var context = new AnnotationConfigApplicationContext(Config.class);
        
        var dao = context.getBean(DAO.class);
        
        System.out.println(Note.customWrapping("Заметки по контенту:", 
                Arrays.toString(KEYWORDS)));
                
        Arrays.stream(KEYWORDS)
                .map(dao::getNotesByContent)
                .flatMap(List::stream)
                .forEach(System.out::println); 
                
        System.out.println();
                               
               
        reading(dao);
        
        creating(dao);
        reading(dao);
        
        updating(dao);
        reading(dao);
        
        deleting(dao);
        reading(dao);
        
    }
    
    
    private static void creating(DAO dao) {      
        CREATING.forEach(Main::addRecord);
        dao.createNote(RECORDS.toArray(new Note[0]));
    }
    
    
    private static void reading(DAO dao) {
        System.out.println(Note.customWrapping("Все темы:"));
        dao.getAllSubjects().stream().forEach(System.out::println);
        System.out.println();
        
        System.out.println(Note.customWrapping("Количество тем: ", "" + dao.getAllSubjectsAmount()));
        System.out.println(Note.customWrapping("Количество заметок: ", "" + dao.getAllNotesAmount()));
        System.out.println();
        
        System.out.println(Note.customWrapping("Количество заметок по каждой теме:"));
        dao.getNotesBySubjectAmount().forEach((subject, amount) -> 
                System.out.println(Note.customWrapping(subject, "" + amount)));
        System.out.println();        
        
        System.out.println(Note.customWrapping("Контент заметки:"));
        System.out.println(dao.getNoteContent(7));
        System.out.println();
        
        System.out.println(Note.customWrapping("Фрагмент контента заметки:"));
        System.out.println(dao.getNoteFragment(9));
        System.out.println();
        
        System.out.println(Note.customWrapping("Случайная заметка:"));
        System.out.println(dao.getRandomNote());
        System.out.println();    
        
        System.out.println(Note.customWrapping("Все заметки:"));
        dao.getAllNotes().stream().forEach(System.out::println);
        System.out.println();
        
        System.out.println(Note.customWrapping("Выбранные темы:") + "\n" + 
                Arrays.toString(selectSubjects()));
                
        System.out.println(Note.customWrapping("Заметки по выбранным темам:"));
        dao.getNotesBySubjects(selectSubjects())
                .stream().forEach(System.out::println);
                
        System.out.println();
    }
    
    
    private static void updating(DAO dao) {               
        UPDATING.forEach(dao::updateNoteByLike);
    }
    
    
    private static void deleting(DAO dao) {
        UPDATING.values().stream().forEach(dao::deleteNoteByLike);
    }
    
    
    private static void addRecord(String key, String value) {
        RECORDS.add(new Note(key, value));
    }
    
    
    private static String[] selectSubjects() {
        return CLI_ARGS.length < 1 ? CUSTOM_SUBJECTS : CLI_ARGS;
    }
}