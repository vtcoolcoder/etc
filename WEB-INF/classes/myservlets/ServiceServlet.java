package myservlets;


import myjdbc.WebNotesAPI;
import myjdbc.WebNotes;
import myjdbc.Note;
import modes.Modes;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;
import java.util.function.BiConsumer;


public class ServiceServlet {
    private record IdAndContent(int ID, String CONTENT) {}
    private record LmbHelper(Note note, String fragment) {}
    

    private static final int SUBSTRLIMIT = 69;
  
    private static final String TEMPLATEFMT = "\n<p>Вы не %%s %%%%s%s.<br>Пожалуйста, %%s %%%%s%s.</p>\n"; 
    private static final String NOTEFMT = TEMPLATEFMT.formatted(" заметки", " заметки"); 
    private static final String UNSELECTEDSUBJFMT = NOTEFMT.formatted("выбрали", "выберите");
    private static final String ERRCREATEFMT = NOTEFMT.formatted("задали", "задайте");
    private static final String FRAGMENTFMT = TEMPLATEFMT.formatted("", "").formatted("выбрали", "выберите");
    
    private static final String CHECKBOXFMT = 
    """
    \t\t\
    <label>
        <input type="checkbox" name="%s" %s> %s
    </label> (<b><i>%d</i></b>)<br>
    """;
    
    private static final String HIGHLIGHT_TEMPLATE =
    """
    \t\t\
    <br><input type="submit" name="mode" value="%s"><br>
    """; 
    
    private static final String RANDOM_NOTEFMT =
    """
    <br><input type="submit" name="mode" value="%s"><br>
    """.formatted(Modes.Consts.RANDOM);
    
    private static final String HIGHLIGHTALLFMT = HIGHLIGHT_TEMPLATE.formatted(Modes.Consts.HIGHLIGHTALL);
    private static final String CANCEL_HIGHLIGHTFMT = HIGHLIGHT_TEMPLATE.formatted(Modes.Consts.CANCEL_HIGHLIGHTALL);
    
    private static final String RECORD_FORMAT = 
    """
    
    <h3>
        <b>Тема:</b> <i><u>%s</u></i> <b>| Заметка:</b>
    </h3>
    <div>
        %s
    </div><hr>
    """;

    private static final String OPTFMT = 
    """
    \t\
    <option value="%s">%s</option>
    """;
    
    
    private static final String RADIOFMT = 
    """
    <h3>
        <label>
            <input type="radio" name="selectedNote" value="%d" %s><br><b>Фрагмент:</b><br>
            <i>%s</i>.......
        </label><hr>
    </h3>
    """;
    
    private static final String HIDDENSUBJFMT = 
    """
    <input type="hidden" name="subject" value="%s">
    """;
    
    private static final String SELECTEDSUBJFMT = 
    """
    <h2>
        <b>Выбранная тема: </b><i><u>%s</u></i>
    </h2>
    """;
    
    private static final String HIDDENSELECTEDNOTEFMT = 
    """
    <input type="hidden" name="selectedNote" value="%s">
    """;
    
    private static final String SELECTEDFRAGMENTFMT = 
    """
    <h2>
        <b>Выбранный фрагмент:</b><br><i><u>%s</u></i> .......
    </h2>
    """;
    
    private static final String TEMPLATE_AMOUNTFMT = 
    """
    <b>Всего %s:</b> <i>%%d</i><br>
    """;
    
    private static final String SUBJECT_AMOUNTFMT = TEMPLATE_AMOUNTFMT.formatted("тем");
    
    private static final String NOTES_AMOUNTFMT = TEMPLATE_AMOUNTFMT.formatted("заметок");
    
    private static final String STATISTICS =
    """
    <h2>Статистика</h2>
    """;
    
    private static final String AVAILABLE_SUBJECTS_LIST =
    """
    <h2>Список доступных тем</h2>
    """;
    

    private HttpServletRequest request;
          
    private String MODE;
    private String SUBJECT;
    private String CREATED_NOTE;
    private String SELECTED_NOTE;
    private String EDITED_NOTE;
    private String DELETED_NOTE;
    
    private Modes mode;
    private boolean isUnselectedSubject = false;
    private boolean isUnselectedNote = false;
    private int counter = 0;
    
    private WebNotesAPI webNotes;
    
    
    ServiceServlet(HttpServletRequest request) {
        this.request = request;
        
        MODE = request.getParameter("mode");
        SUBJECT = request.getParameter("subject");
        CREATED_NOTE = request.getParameter("createdNote");
        SELECTED_NOTE = request.getParameter("selectedNote");
        EDITED_NOTE = request.getParameter("editedNote");
        DELETED_NOTE = SELECTED_NOTE;
        
        initMode();
        
        webNotes = new WebNotes();
    }
    
    
    String getResultingContent(Mode mode) {
        return getResultingContent(this::runAction, mode);
    }
    
    
    private boolean isSubjectMode() { return Modes.SUBJECT.equals(mode); }
    private boolean isNoteMode() { return Modes.NOTE.equals(mode); }
    private boolean isCreateMode() { return Modes.CREATE.equals(mode); }
    private boolean isEditMode() { return Modes.EDIT.equals(mode); }
    private boolean isDeleteMode() { return Modes.DELETE.equals(mode); }
    private boolean isHighlightAllMode() { return Modes.HIGHLIGHTALL.equals(mode); }
    private boolean isCancelHighlightMode() { return Modes.CANCEL_HIGHLIGHTALL.equals(mode); }
    private boolean isRandomNote() { return Modes.RANDOM.equals(mode); }
    
    
    private String getCheckboxFormattedLine(String subject, String checked, int amount) { 
        return CHECKBOXFMT.formatted(subject, checked, subject, amount); 
    }
    
    
    private String getOptionFormattedLine(String el) { return OPTFMT.formatted(el, el); }
    
    
    private void updateNote(int ID) {
        webNotes.updateNote((EDITED_NOTE != null) ? EDITED_NOTE : "", ID);
    }
    
    
    private void deleteNote(int ID) { webNotes.deleteNote(ID); }
    
     
    private boolean isFillNotesBySelectedSubjectsError() {
        return getSelectedSubjects().isEmpty();
    }
    
    
    private void fillNotesBySelectedSubjectsError(StringBuilder sb) {
        sb.append(UNSELECTEDSUBJFMT.formatted("тему", "тему"));
    }
    
    
    private void fillNotesBySelectedSubjects(StringBuilder sb) {
        sb.append("<h2>Заметки по выбранным темам:</h2>");
                
        Map<String, Set<Note>> availableRecords = 
            webNotes.getNotes(getSelectedSubjects());
            
        availableRecords.forEach((key, value) -> {
                value.stream()
                     .map(el -> RECORD_FORMAT.formatted(
                                        el.subject(),
                                        el.note()))              
                     .forEach(sb::append);
                     
                sb.append("<hr>");
        });
    }
    
    
    private boolean isFillChangeSubject() {
        return (SUBJECT != null);
    }
    
    
    private void fillChangeSubject(StringBuilder sb) {
        sb.append(HIDDENSUBJFMT.formatted(SUBJECT));         
        sb.append(SELECTEDSUBJFMT.formatted(SUBJECT));
    }
    
    
    private void fillChangeSubjectError(StringBuilder sb) {
        sb.append(UNSELECTEDSUBJFMT.formatted("тему", "тему"));
        isUnselectedSubject = true;
    }
    
       
    private boolean isFillChangeNote() {
        return (SELECTED_NOTE != null) && !isUnselectedNote;
    }
    
    
    private void fillChangeNote(StringBuilder sb) {
        sb.append(HIDDENSELECTEDNOTEFMT.formatted(SELECTED_NOTE));
                
        IdAndContent idAndContent = getIdAndContent();
        
        int length = idAndContent.CONTENT().length(); 
        String FRAGMENT = idAndContent.CONTENT()
                                      .substring(0, (length >= SUBSTRLIMIT) ? SUBSTRLIMIT : length); 
                      
        sb.append(SELECTEDFRAGMENTFMT.formatted(FRAGMENT));
    }
    
    
    private void fillChangeNoteError(StringBuilder sb) {
        sb.append(FRAGMENTFMT.formatted("фрагмент", "фрагмент"));
        isUnselectedNote = true;
    }
    
    
    private void fillChangeNoteDefault(StringBuilder sb) {
        Map<String, Set<Note>> availableRecords = 
            webNotes.getNotes((SUBJECT != null) ? SUBJECT : "");
                
        sb.append(iterate(availableRecords, RADIOFMT));              
        
        if (isAddingSubmitButton()) {
            sb.append("<input type=\"submit\" name=\"mode\" value=\"Выбрать заметку\">");
        }
    }
    
    
    private boolean isAddingSubmitButton() {
        return Modes.NOTE.equals(mode) || (SUBJECT != null);
    }
    
         
    private IdAndContent getIdAndContent() {
        int ID = -1;  
        
        try { 
            ID = Integer.parseInt(SELECTED_NOTE); 
        } catch (NumberFormatException ex) {}
        
        final String CONTENT = (ID != -1) ? webNotes.getNoteContent(ID) : "";
        
        return new IdAndContent(ID, CONTENT);
    }
    
    
    private boolean isFillContent() {
        return (!isUnselectedSubject 
                ^ (SELECTED_NOTE != null) 
                ^ !isUnselectedNote) 
                        && Modes.NOTE.equals(mode);
    }
    
    
    private void fillUpdateContent(StringBuilder sb, String CONTENT) {
        sb.append("<textarea name=\"editedNote\" cols=\"92\" rows=\"23\" wrap=\"hard\">");
        sb.append(CONTENT); 
        sb.append("</textarea><br><br>");
        sb.append("<input type=\"submit\" name=\"mode\" value=\"Редактировать заметку\">");
    }
    
    
    private void fillDeleteContent(StringBuilder sb, String CONTENT) {
        sb.append("<div>");
        sb.append(CONTENT); 
        sb.append("</div><br><br>");
        sb.append("<input type=\"submit\" name=\"mode\" value=\"Удалить заметку\">");
    }
    
    
    private void initMode() {
        switch (MODE != null ? MODE : Modes.Consts.BYDEFAULT) {
            case Modes.Consts.SUBJECT -> this.mode = Modes.SUBJECT;
            case Modes.Consts.NOTE -> this.mode = Modes.NOTE;
            case Modes.Consts.EDIT -> this.mode = Modes.EDIT;
            case Modes.Consts.DELETE -> this.mode = Modes.DELETE;
            case Modes.Consts.CREATE -> this.mode = Modes.CREATE;
            case Modes.Consts.HIGHLIGHTALL -> this.mode = Modes.HIGHLIGHTALL;
            case Modes.Consts.CANCEL_HIGHLIGHTALL -> this.mode = Modes.CANCEL_HIGHLIGHTALL;
            case Modes.Consts.RANDOM -> this.mode = Modes.RANDOM;
            case Modes.Consts.BYDEFAULT -> this.mode = Modes.BYDEFAULT;
        }
    }
    
    
    private Set<String> getAvailableSubjects() { 
        return webNotes.getAllSubjects(); 
    }
    
    
    private String iterate(Map<String, Set<Note>> availableRecords, String RADIOFMT) 
    {
        StringBuilder sb = new StringBuilder();
        
        availableRecords.forEach((key, value) -> value.stream()  
                .map(el -> {
                        String fullNote = el.note();
                        int length = fullNote.length();
                
                        String beginNoteFragment = 
                                fullNote.substring(0,
                                        (length >= SUBSTRLIMIT) 
                                                ? SUBSTRLIMIT 
                                                : length);
                                                
                        return new LmbHelper(el, beginNoteFragment);
                })
                .map(el -> RADIOFMT.formatted(
                                el.note().id(), 
                                (counter++ == 0) ? "checked" : "",
                                el.fragment()))
                .forEach(sb::append));
          
        return sb.toString();
    }
    

    private boolean isValidCreatedNote() {
        return isValidCreatedItem(SUBJECT) 
                && isValidCreatedItem(CREATED_NOTE);
    }
    
    
    private boolean isValidCreatedItem(final String content) {
        return ! (content == null 
                || content.isEmpty() 
                || content.isBlank());
    }
    
    
    private void createNote() { 
        webNotes.createNote(new Note(SUBJECT, CREATED_NOTE)); 
    }
    
    
    private void generateErrorMessages(final StringBuilder sb, final String... items) {
        for (String item : items) {
            if (! isValidCreatedItem(item)) { 
                sb.append(getErrorMsg(item)); 
            }
        }
    }
    
    
    private String getErrorMsg(final String item) { 
        return ERRCREATEFMT.formatted(item, item); 
    }
    
    
    private Set<String> getSelectedSubjects() {
			      Set<String> result = new TreeSet<>();
			      
			      request.getParameterMap()
			             .forEach((key, value) -> {
			                     String subject = key; 
                       String[] wrapper = value;
                    
                       String checkboxStatus = (wrapper.length > 0) ? wrapper[0] : "";
                       boolean isCheckboxOn = "on".equals(checkboxStatus);
                       boolean isValidSubjectName = 
                               getAvailableSubjects().contains(subject);
                
                       if (isCheckboxOn && isValidSubjectName) { 
                           result.add(subject); 
                       }
			             });
			      
			      return result;
			  }
			  
			     
    private String getResultingContent(
            BiConsumer<Mode, StringBuilder> action, 
            Mode mode) 
    {
        StringBuilder sb = new StringBuilder();      
        action.accept(mode, sb); 
        return sb.toString();
    }
    
    
    private void runAction(Mode mode, StringBuilder sb) {
        switch (mode) {
            case CREATE -> {
                if (isCreateMode()) {
                    if (isValidCreatedNote()) { 
                        createNote(); 
                    } else { 
                        generateErrorMessages(sb, "тему", "содержимое"); 
                    }
                }
            }
            
            
            case READ -> {
                if (isSubjectMode()) {
                    if (isFillNotesBySelectedSubjectsError()) {
                        fillNotesBySelectedSubjectsError(sb);
                    } else {
                        fillNotesBySelectedSubjects(sb);        
                    }
                } 
            }
           
            
            case UPDATE -> {
                IdAndContent idAndContent = getIdAndContent();
            
                if (isEditMode()) {   
                    updateNote(idAndContent.ID());      
                } else if (isFillContent()) {
                    fillUpdateContent(sb, idAndContent.CONTENT());
                } 
            }
            
            
            case DELETE -> {
                IdAndContent idAndContent = getIdAndContent();
            
                if (isDeleteMode()) { 
                    deleteNote(idAndContent.ID());
                } else if (isFillContent()) { 
                    fillDeleteContent(sb, idAndContent.CONTENT()); 
                } 
            }
            
            
            case CHANGE_NOTE -> {
                if (isNoteMode()) {
                    if (isFillChangeNote()) {        
                        fillChangeNote(sb);
                    } else {
                        fillChangeNoteError(sb);
                    }   
                } else {
                    fillChangeNoteDefault(sb);           
                }   
            }
            
            
            case CHANGE_SUBJECT -> {
                if (isSubjectMode()) {
                    if (isFillChangeSubject()) {         
                        fillChangeSubject(sb);
                    } else {
                        fillChangeSubjectError(sb);
                    }
                }
            }
            
            
            case PRELUDE -> {
                
                Map<String, Integer> notesBySubjectAmount = webNotes.getNotesBySubjectAmount();
                
                if (isAddingExtraInfo()) {
                    int allSubjectsAmount = webNotes.getAllSubjectsAmount();
                    int allNotesAmount = webNotes.getAllNotesAmount();
                    
                    sb.append(STATISTICS); 
                    sb.append("<h3>");
                    sb.append(SUBJECT_AMOUNTFMT.formatted(allSubjectsAmount));
                    sb.append("\n");
                    sb.append(NOTES_AMOUNTFMT.formatted(allNotesAmount));
                    sb.append("\n");
                    sb.append("</h3>");
                    sb.append(AVAILABLE_SUBJECTS_LIST);
                }
                
                getAvailableSubjects().stream().forEach(subject -> { 
                    String checked = (! isCancelHighlightMode())
                        && (isHighlightAllMode() || getSelectedSubjects().contains(subject)) 
                                ? "checked" : "";
                    int currentNotesAmount = notesBySubjectAmount.get(subject);
                    sb.append(getCheckboxFormattedLine(subject, checked, currentNotesAmount));
                    
                });   
                
                if (isAddingExtraInfo()) {
                    sb.append(RANDOM_NOTEFMT);
                    sb.append(HIGHLIGHTALLFMT);
                    sb.append(CANCEL_HIGHLIGHTFMT);
                }           
            }
            
            
            case RANDOM -> {
                if (isRandomNote() && isAddingExtraInfo()) {
                    Note randomNote = webNotes.getRandomNote();
                    sb.append("<h2>Случайная заметка:</h2>");
                    sb.append(RECORD_FORMAT.formatted(
                            randomNote.subject(), randomNote.note()));
                    sb.append("<hr>");
                }
            }
            
            
            case DEFAULT -> {
                getAvailableSubjects()
                        .stream()
                        .map(el -> getOptionFormattedLine(el))
                        .forEach(sb::append);
            }
        }
    }
    
    
    private boolean isAddingExtraInfo() { return (! getAvailableSubjects().isEmpty()); }
}