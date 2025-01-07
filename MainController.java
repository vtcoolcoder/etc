package controllers;


import static java.util.stream.Collectors.toMap;
import static java.util.Collections.EMPTY_MAP;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import jdbc_template.DAO;
import jdbc_template.Config;
import jdbc_template.Note;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.ApplicationContext; 


@Controller
public class MainController {
    private final ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
    private final DAO dao = context.getBean(DAO.class); 
    
       
    @GetMapping
    public String home(Model model) {
        model.addAttribute("title", "Домашняя");
        model.addAttribute("message", "Выберите темы");  
        model.addAttribute("subjectsAmount", dao.getAllSubjectsAmount());
        model.addAttribute("notesAmount", dao.getAllNotesAmount()); 
        model.addAttribute("allSubjects", dao.getNotesBySubjectAmount());
        return "index.html";
    }
    
    
    @GetMapping("/create")
    public String creating(Model model) {
        model.addAttribute("title", "Создание заметки");
        model.addAttribute("message", "Выберите уже существующую тему или создайте новую");
        model.addAttribute("allSubjects", dao.getAllSubjects());
        return "create.html";
    }
    
    
    @PostMapping("/create")
    public String tryCreatingNote(@RequestParam String subject, @RequestParam String note) {
        dao.addNote(new Note(subject, note));
        return "redirect:/create";
    }
    
    
    @GetMapping("/selecting-notes-by-id")
    public String selectById(Model model) {
        model.addAttribute("title", "Выбор ID");
        model.addAttribute("message", "Выберите фрагмент заметки"); 
        model.addAttribute("noteFragmentById", dao.getNoteFragmentToId());
        return "select-by-id.html";   
    }
    
    
    @GetMapping("/reading/notes")
    public String selectNoteById(@RequestParam(required = false) String id) {     
        try {
            return "forward:/reading/notes/%d".formatted(Integer.parseInt(id));
        } catch (Exception e) {
            return "forward:/selecting-notes-by-id";
        }           
    }
    
    
    @GetMapping("/reading/notes/{id}")
    public String noteById(@PathVariable String id, Model model) {   
        try {
            int intId = Integer.parseInt(id);
            if (dao.getAllId().contains(intId)) {
                model.addAttribute("title", "Чтение заметок");
                model.addAttribute("message", "Заметка");   
                model.addAttribute("noteById", dao.getNoteContent(intId));
                return "note-by-id.html";
            } else {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            return "forward:/selecting-notes-by-id";
        }      
    }
    
    
    @GetMapping("/reading/notes-by-selected-subjects")
    public String selectedNotes(
            @RequestParam(required = false) String[] subjects,
            Model model
    ) {
        model.addAttribute("title", "Чтение заметок");
        model.addAttribute("message", "Заметки по выбранным темам");
        model.addAttribute("notesBySelectedSubjects", 
                subjects != null ? dao.getNotesBySubjects(subjects) : new String[0]);
        return "selected-notes.html";
    }
    

    @GetMapping("/reading/all-notes")
    public String allNotes(Model model) {
        model.addAttribute("title", "Чтение заметок");
        model.addAttribute("message", "Все заметки");
        model.addAttribute("notes", dao.getAllNotes());
        return "all-notes.html";
    } 
    
    
    @GetMapping("/update")
    public String updating(
            Model model,
            @RequestParam(required = false) String subject,
            @RequestParam(required = false) Integer id
    ) {
        model.addAttribute("title", "Обновление заметок");
        model.addAttribute("message", "Обновить заметку");
        model.addAttribute("allSubjects", dao.getAllSubjects());
        model.addAttribute("noteFragmentsBySelectedSubject", subject != null 
                ? dao.getIdAndNoteFragmentBySubject(subject) 
                : EMPTY_MAP);
                
        if (id != null) {
            model.addAttribute("noteId", id);
            model.addAttribute("content", dao.getNoteContent(id));
        }        
        
        return "update.html";
    }
    
    
    @PostMapping("/update")
    public String tryUpdatingNote(
            @RequestParam Integer updatedNoteId, 
            @RequestParam String updatedNoteContent
    ) {
        dao.updateNote(updatedNoteContent, updatedNoteId);
        return "redirect:/update";
    }
    
    
    @GetMapping("/delete")
    public String deleting(
            Model model,
            @RequestParam(required = false) String subject,
            @RequestParam(required = false) Integer id
    ) {
        model.addAttribute("title", "Удаление заметок");
        model.addAttribute("message", "Удалить заметку");
        model.addAttribute("allSubjects", dao.getAllSubjects());
        model.addAttribute("noteFragmentsBySelectedSubject", subject != null 
                ? dao.getIdAndNoteFragmentBySubject(subject) 
                : EMPTY_MAP);
                
        if (id != null) {
            model.addAttribute("noteId", id);
            model.addAttribute("content", dao.getNoteContent(id));
        }        
        
        return "delete.html";
    }
    
    
    @PostMapping("/delete")
    public String tryDeletingNote(@RequestParam Integer deletedNoteId) {
        dao.deleteNote(deletedNoteId);
        return "redirect:/delete";
    }
}
