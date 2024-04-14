package modes;


public enum Modes {    
    SUBJECT(Consts.SUBJECT), 
    NOTE(Consts.NOTE), 
    EDIT(Consts.EDIT), 
    DELETE(Consts.DELETE),
    CREATE(Consts.CREATE),
    BYDEFAULT(Consts.BYDEFAULT);
    
    public interface Consts {
        String SUBJECT = "Выбрать тему заметки"; 
        String NOTE = "Выбрать заметку";
        String EDIT = "Редактировать заметку";
        String DELETE = "Удалить заметку";
        String CREATE = "Добавить заметку";
        String BYDEFAULT = "";
    }
    
    private String name;
    Modes(String name) { this.name = name; }
    @Override public String toString() { return name; }
}