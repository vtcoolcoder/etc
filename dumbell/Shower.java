package dumbell;


import static dumbell.DumbBell.Represent;
import static dumbell.Constants.*;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Consumer;


class Shower {

    static void showing(Consumer<List<Represent>> filling, 
                        String title, 
                        Consumer<List<Represent>> secondAction) {
        requireNonNull(filling);
        requireNonNull(secondAction);
        
        showTitle(title);
        
        var result = new ArrayList<Represent>();    
        filling.accept(result);    
                    
        secondAction.accept(result);
        
        Collections.sort(result);       
        result.forEach(e -> System.err.println(
                STR."\{e.msg()} \{SPACES} \{e.sum()} \{SPACES} \{e.getTotalWeight()}"
        ));
    }


    static void showTitle(String title) {
        requireNonNull(title);
        
        var line = "*".repeat(120);
        System.err.println(
            STR."""
            
            \{line}
            \{title}
            \{line}
            """
        );
    }
    
    
    static void separateOutputByLines() {
        System.err.println();
        showTitle("");
        System.err.println();
    }
}