/* 
 * Написать много перегруженных конструкторов, а для создания объекта лучше написать билдер
 * Также написать конструктор по умолчанию
 * В конструкторах производить валидацию входных аргументов, проверив объекты сначала на null
 * Сделать возможность передачи в конструкторе таких параметров:
 *     вес грифа с замками;
 *     массив со всеми уникальными имеющимися парами дисков -- double[];
 *     мапу с уточнением сколько имеется пар для данного веса -- Map<Double, Integer>;
 *     возможно, количество сторон (1 или 2) -- enum SideAmount { ONE, TWO }; 
 *     возможно, количество дисков с каждой стороны по умолчанию -- enum DiscAmount { ONE, TWO, THREE, FOUR };
 *     массив с названиями цветов фона для строк таблицы с данными -- String[];
*/
package dumbell;


import static java.util.Objects.requireNonNull;
import static java.util.stream.IntStream.range;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.EnumMap;

import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.BiConsumer;
import java.util.function.DoubleConsumer;
import java.util.function.Predicate;
import java.util.function.Function;

import java.util.stream.Collectors;
import java.util.stream.DoubleStream;


public class DumbBellRunner {
                                                
    public static void main(String... args) {   
        var dumbBell = new DumbBell();
        
        dumbBell.setDiskAmount(args);        
        dumbBell.showInfo();
        Shower.separateOutputByLines();
        dumbBell.showBaseData();    
    }
}