package myservlets;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Lazy;

import lombok.Cleanup;


import static myservlets.ServiceServletData.*;

import using_spring.SpringConfig;
import using_spring.WebNotesAPI;
import using_spring.WebNotes;
import using_spring.Note;

import modes.Modes;

import java.util.Set;
import java.util.TreeSet;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import java.util.function.BiConsumer;


@Component
@Lazy
public class ServiceServlet {
    private record IdAndContent(int ID, String CONTENT) {}
    private record LmbHelper(Note note, String fragment) {}
    

    private final HttpServletRequest request;
    private final WebNotesAPI webNotes;
          
          
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
    
      
    @Autowired
    public ServiceServlet(HttpServletRequest request, WebNotesAPI webNotes) {
        this.request = request;
        this.webNotes = webNotes;
        
        MODE = request.getParameter(PARAM_MODE); 
        SUBJECT = request.getParameter(PARAM_SUBJECT); 
        CREATED_NOTE = request.getParameter(PARAM_CREATED_NOTE); 
        SELECTED_NOTE = request.getParameter(PARAM_SELECTED_NOTE); 
        EDITED_NOTE = request.getParameter(PARAM_EDITED_NOTE); 
        DELETED_NOTE = SELECTED_NOTE;
        
        initMode();  
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
        webNotes.updateNote((EDITED_NOTE != null) ? EDITED_NOTE : NULL_CONTENT, ID);
    }
    
    
    private void deleteNote(int ID) { webNotes.deleteNote(ID); }
    
     
    private boolean isFillNotesBySelectedSubjectsError() {
        return getSelectedSubjects().isEmpty();
    }
    
    
    private void fillNotesBySelectedSubjectsError(StringBuilder sb) {
        sb.append(UNSELECTEDSUBJFMT.formatted(UNSUBJECT, UNSUBJECT));
    }
    
    
    private void fillNotesBySelectedSubjects(StringBuilder sb) {
        sb.append(NOTES_BY_SELECTED_SUBJECTS_TITLE);
                
        Map<String, Set<Note>> availableRecords = 
            webNotes.getNotes(getSelectedSubjects());
            
        availableRecords.forEach((key, value) -> {
                value.stream()
                     .map(el -> RECORD_FORMAT.formatted(
                                        el.subject(),
                                        el.note()))              
                     .forEach(sb::append);
                     
                sb.append(HR); 
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
        sb.append(UNSELECTEDSUBJFMT.formatted(UNSUBJECT, UNSUBJECT));
        isUnselectedSubject = true;
    }
    
       
    private boolean isFillChangeNote() {
        return (SELECTED_NOTE != null) && !isUnselectedNote;
    }
    
    
    private void fillChangeNote(StringBuilder sb) {
        sb.append(HIDDENSELECTEDNOTEFMT.formatted(SELECTED_NOTE));         
        IdAndContent idAndContent = getIdAndContent();
        String FRAGMENT = idAndContent.CONTENT();         
        sb.append(SELECTEDFRAGMENTFMT.formatted(FRAGMENT));
    }
    
    
    private void fillChangeNoteError(StringBuilder sb) {
        sb.append(FRAGMENTFMT.formatted(UNFRAGMENT, UNFRAGMENT));
        isUnselectedNote = true;
    }
    
    
    private void fillChangeNoteDefault(StringBuilder sb) {
        Map<String, Set<Note>> availableRecords = 
            webNotes.getNotes((SUBJECT != null) ? SUBJECT : NULL_CONTENT);
                
        sb.append(iterate(availableRecords, RADIOFMT));              
        
        if (isAddingSubmitButton()) {
            sb.append(SUBMIT_SELECT);
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
        
        final String CONTENT = (ID != -1) ? webNotes.getNoteFragment(ID) : NULL_CONTENT; 
        
        return new IdAndContent(ID, CONTENT);
    }
    
    
    private boolean isFillContent() {
        return (!isUnselectedSubject 
                ^ (SELECTED_NOTE != null) 
                ^ !isUnselectedNote) 
                        && Modes.NOTE.equals(mode);
    }
    
    
    private void fillUpdateContent(StringBuilder sb, String CONTENT) {
        sb.append(TEXTAREA_OPEN);
        sb.append(CONTENT); 
        sb.append(TEXTAREA_CLOSE);
        sb.append(SUBMIT_UPDATE);
    }
    
    
    private void fillDeleteContent(StringBuilder sb, String CONTENT) {
        sb.append(DIV_OPEN); 
        sb.append(CONTENT); 
        sb.append(DIV_CLOSE); 
        sb.append(DOUBLE_BR);   
        sb.append(SUBMIT_DELETE);
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
                .map(el -> new LmbHelper(el, webNotes.getNoteFragment(el.id())))
                .map(el -> RADIOFMT.formatted(
                                el.note().id(), 
                                (counter++ == 0) ? CHECKED_ON : CHECKED_OFF,
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
                    
                       String checkboxStatus = (wrapper.length > 0) ? wrapper[0] : CHECKBOX_OFF;
                       boolean isCheckboxOn = CHECKBOX_ON.equals(checkboxStatus); 
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
                        generateErrorMessages(sb, UNSUBJECT, UNCONTENT); 
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
                final int ID = getIdAndContent().ID();
            
                if (isEditMode()) {   
                    updateNote(ID);      
                } else if (isFillContent()) {
                    fillUpdateContent(sb, webNotes.getNoteContent(ID));
                } 
            }
            
            
            case DELETE -> {
                final int ID = getIdAndContent().ID();
            
                if (isDeleteMode()) { 
                    deleteNote(ID);
                } else if (isFillContent()) { 
                    fillDeleteContent(sb, webNotes.getNoteContent(ID)); 
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
                    sb.append(H3_OPEN); 
                    sb.append(SUBJECT_AMOUNTFMT.formatted(allSubjectsAmount));
                    sb.append(NEW_LINE); 
                    sb.append(NOTES_AMOUNTFMT.formatted(allNotesAmount));
                    sb.append(NEW_LINE);
                    sb.append(H3_CLOSE);
                    sb.append(AVAILABLE_SUBJECTS_LIST);
                }
                
                getAvailableSubjects().stream().forEach(subject -> { 
                    String checked = (! isCancelHighlightMode())
                        && (isHighlightAllMode() || getSelectedSubjects().contains(subject)) 
                                ? CHECKED_ON : CHECKED_OFF; 
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
                if (isRandomNote()) {
                    Note randomNote = webNotes.getRandomNote();
                    sb.append(RANDOM_TITLE); 
                    sb.append(RECORD_FORMAT.formatted(
                            randomNote.subject(), randomNote.note()));
                }
            }
            
            
            case DEFAULT -> {
                getAvailableSubjects()
                        .stream()
                        .map(this::getOptionFormattedLine)
                        .forEach(sb::append);
            }
        }
    }
    
    
    private boolean isAddingExtraInfo() { return (! getAvailableSubjects().isEmpty()); }
}