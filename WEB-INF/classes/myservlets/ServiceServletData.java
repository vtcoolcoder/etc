package myservlets;


import modes.Modes;


public interface ServiceServletData {
    int SUBSTRLIMIT = 69;
    
    
    String PARAM_MODE = "mode";
    String PARAM_SUBJECT = "subject";
    String PARAM_CREATED_NOTE = "createdNote";
    String PARAM_SELECTED_NOTE = "selectedNote";
    String PARAM_EDITED_NOTE = "editedNote";
        
 
    String H3_OPEN = "<h3>";    
    String H3_CLOSE = "</h3>";
    String DIV_OPEN = "<div>";   
    String DIV_CLOSE = "</div>";  
    String BR = "<br>";
    String DOUBLE_BR = BR + BR;
    String HR = "<hr>";
    String TEXTAREA_OPEN = "<textarea name=\"editedNote\" cols=\"92\" rows=\"23\" wrap=\"hard\">";  
    String TEXTAREA_CLOSE = "</textarea><br><br>";
    
    String SUBMIT_DELETE = "<input type=\"submit\" name=\"mode\" value=\"Удалить заметку\">";
    String SUBMIT_UPDATE = "<input type=\"submit\" name=\"mode\" value=\"Редактировать заметку\">";
    String SUBMIT_SELECT = "<input type=\"submit\" name=\"mode\" value=\"Выбрать заметку\">";
    
    String UNSUBJECT = "тему";   
    String UNCONTENT = "содержимое";
    String UNFRAGMENT = "фрагмент"; 
    
    String CHECKED_ON = "checked";   
    String CHECKED_OFF = "";
    String CHECKBOX_ON = "on";  
    String CHECKBOX_OFF = "";
    
    String RANDOM_TITLE = "<h2>Случайная заметка</h2>";
    String NOTES_BY_SELECTED_SUBJECTS_TITLE = "<h2>Заметки по выбранным темам</h2>"; 
    
    String NEW_LINE = "\n";  
    String NULL_CONTENT = "";
    
      
    String TEMPLATEFMT = "\n<p>Вы не %%s %%%%s%s.<br>Пожалуйста, %%s %%%%s%s.</p>\n"; 
    String NOTEFMT = TEMPLATEFMT.formatted(" заметки", " заметки"); 
    String UNSELECTEDSUBJFMT = NOTEFMT.formatted("выбрали", "выберите");
    String ERRCREATEFMT = NOTEFMT.formatted("задали", "задайте");
    String FRAGMENTFMT = TEMPLATEFMT.formatted("", "").formatted("выбрали", "выберите");
    
    
    String CHECKBOXFMT = 
    """
    \t\t\
    <label>
    <input type="checkbox" name="%s" %s> %s
    </label> (<b><i>%d</i></b>)<br>
    """;
    
    String HIGHLIGHT_TEMPLATE =
    """
    \t\t\
    <br><input type="submit" name="mode" value="%s"><br>
    """; 
    
    String RANDOM_NOTEFMT =
    """
    <br><input type="submit" name="mode" value="%s"><br>
    """.formatted(Modes.Consts.RANDOM);
    
    String HIGHLIGHTALLFMT = HIGHLIGHT_TEMPLATE.formatted(Modes.Consts.HIGHLIGHTALL);
    String CANCEL_HIGHLIGHTFMT = HIGHLIGHT_TEMPLATE.formatted(Modes.Consts.CANCEL_HIGHLIGHTALL);
    
    String RECORD_FORMAT = 
    """
    
    <h3>
    <b>Тема:</b> <i><u>%s</u></i> <b>| Заметка:</b>
    </h3>
    <div>
    %s
    </div><hr>
    """;
    
    String OPTFMT = 
    """
    \t\
    <option value="%s">%s</option>
    """;
    
    
    String RADIOFMT = 
    """
    <h3>
    <label>
        <input type="radio" name="selectedNote" value="%d" %s><br><b>Фрагмент:</b><br>
        <i>%s</i>.......
    </label><hr>
    </h3>
    """;
    
    String HIDDENSUBJFMT = 
    """
    <input type="hidden" name="subject" value="%s">
    """;
    
    String SELECTEDSUBJFMT = 
    """
    <h2>
    <b>Выбранная тема: </b><i><u>%s</u></i>
    </h2>
    """;
    
    String HIDDENSELECTEDNOTEFMT = 
    """
    <input type="hidden" name="selectedNote" value="%s">
    """;
    
    String SELECTEDFRAGMENTFMT = 
    """
    <h2>
    <b>Выбранный фрагмент:</b><br><i><u>%s</u></i> .......
    </h2>
    """;
    
    String TEMPLATE_AMOUNTFMT = 
    """
    <b>Всего %s:</b> <i>%%d</i><br>
    """;
    
    String SUBJECT_AMOUNTFMT = TEMPLATE_AMOUNTFMT.formatted("тем");
    
    String NOTES_AMOUNTFMT = TEMPLATE_AMOUNTFMT.formatted("заметок");
    
    String STATISTICS =
    """
    <h2>Статистика</h2>
    """;
    
    String AVAILABLE_SUBJECTS_LIST =
    """
    <h2>Доступные темы</h2>
    """;    
}