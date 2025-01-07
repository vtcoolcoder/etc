#                     ЛУЧШЕ ПЕРЕСТРАХОВЫВАТЬСЯ
# перед каждым запросом устанавливать сначала кодировку соединения:
#     SET NAMES utf8mb4;
#
# при создании базы данных и таблиц -- также указывать их кодировку:
#     CREATE DATABASE 
#     IF NOT EXISTS `something...` 
#     CHARACTER SET utf8mb4
#     COLLATE utf8mb4_general_ci;
#
#     CREATE TABLE IF NOT EXISTS `something...` ( 
#         ...
#     ) CHARSET = utf8mb4 COLLATE utf8mb4_general_ci;


SET NAMES utf8mb4;


CREATE DATABASE 
IF NOT EXISTS `my_rough_notes` 
CHARACTER SET utf8mb4
COLLATE utf8mb4_general_ci;


# USE `my_rough_notes`;


CREATE TABLE IF NOT EXISTS `my_rough_notes`.`javanotes` (
    id SERIAL PRIMARY KEY,
    subject VARCHAR(100) NOT NULL,
    note TEXT NOT NULL) 
CHARSET = utf8mb4 
COLLATE utf8mb4_general_ci;


INSERT INTO `my_rough_notes`.`javanotes` (subject, note) VALUES

('модификаторы доступа', 
'Уровни доступа (в порядке возрастания ограничений):
public -- Публичный. Означает, что код из любой части программы может получить доступ
к публичным сущностям (под которыми подразумеваются классы, переменные, методы, конструкторы и т.д.)
protected -- Защищённый. Работает также, как уровень по умолчанию (доступ имеет код из того же пакета),
но позволяет дочерним классам за пределами(!) пакета наследовать защищённые сущности
default -- Уровень по умолчанию. Означает, что доступ к классам и методам может получать только тот код,
который был определён в одном с ними пакете
private -- Приватный. Означает, что доступ предоставляется только коду из того же класса. 
Помните, что приватными могут быть классы, но не объекты.
Один экземпляр Dog может видеть приватные члены другого, в то время как Cat не имеет к ним доступа'),

('модификаторы доступа',
'Список модификаторов доступа от самого строгого к самому лояльному:
    private, default, protected, public'),
    
('интерфейсы',
'Интерфейсы нужны главным образом для разделения того "что" нужно сделать
от того "как" это сделать.
В интерфейсах описывается "что" нужно сделать.
А в классах, реализующих интерфейс(ы), -- "как" это сделать.
Главное отличие абстрактного класса от интерфейса:
    в интерфейсе нельзя хранить состояние (только константы)
Ещё интерфейс -- своего рода контракт, когда нас интересует конкретная функциональность
Например, метод может принимать в параметрах ссылку типа интерфейса,
когда методу не важны конкретные типы объектов, а важна лишь функциональность объектов,
реализующих данный интерфейс
В целом, при написании кода следует стараться работать через интерфейсы для повышения его абстракции
-- это один из принципов SOLID'),

('SOLID',
'Согласно принципам SOLID -- классы следует использовать через интерфейсы'),

('строки',
'Класс StringBuilder ничем не отличается от класса StringBuffer, 
за исключением того, что он не синхронизирован, 
а следовательно, не является потокобезопасным. 
Применение класса StringBuilder даёт выигрыш в производительности.
Но в тех случаях, когда обращение к изменяемой строке происходит 
из нескольких потоков исполнения без внешней синхронизации, 
следует применять класс StringBuffer, а не StringBuilder.'),

('библиотеки',
'Есть полезные библиотеки: lombok, slf4j (для логирования)'),

('изучение',
'Хорошо проработать данные, связанные между собой, темы: 
Generics & Wildcard, Collections Framework, Stream API, Лямбда-выражения
Также нужно разобраться с аннотациями и рефлексией
А тему многопоточности пока отложить до лучших времён'),

('immutable',
'Immutable-объекты нужны, в частности, для того
чтобы гарантировать потокобезопасные операции над ними'),

('immutable',
'Все классы-оболочки примитивов и класс String -- неизменяемы:
если нам потребуется изменить значение, то придётся создать новый объект'),

('исключения',
'Исключения следует хотя бы залогировать (в лог-файле, например) максимально подробно, 
и указав контекст'),

('исключения',
'Следует стараться кидать непроверяемые (unchecked) исключения, нежели проверяемые (checked)
    Unchecked-исключения -- потомки класса RuntimeException 
(а формально и класса Error и его потомков, которые используются только виртуальной машиной)
Чтобы создать своё unchecked-исключение следует унаследоваться от класса RuntimeException
или от любого его потомка
    А checked-исключения потомки класса Exception, кроме RuntimeException
Чтобы создать своё checked-исключение следует унаследоваться от класса Exception
или от любого его потомка, кроме RuntimeException
    Преимущество unchecked-исключений в сокращении и упрощении кода 
(например, не придётся каждый раз в сигнатурах методов указывать исключение)
    Непроверяемые исключения следует кидать тогда, 
когда пользователь передаёт неправильные данные или проблемы с его стороны
    А проверяемые исключения кидаются тогда,
когда ошибки возможны в логике -- уже с нашей стороны и не зависят от внешних обстоятельств,
и необходимо предупредить пользователя об этом'),

('immutable',
'Чтобы сделать класс immutable, 
следует объявить его final (чтобы нельзя было наследоваться от него), 
а его поля -- private final 
(чтобы после начальной инициализации в конструкторе они остались неизменяемыми),
а ещё убрать все сеттеры, оставив лишь публичный конструктор,
инициализирующий все поля создаваемого объекта'),

('массивы',
'Поинтересоваться конструкторами массивов примитивных типов
(интересно как инициализировать сразу все элементы массива своими значениями 
по умолчанию: например, выражение 
    int[] arr = new int[n];
инициализирует все n элементов массива значениями по умолчанию 0,
а мне нужно иметь возможность задавать своё значение по умолчанию отличное от 0)
    и возможны ли в Java именованные параметры у методов как в Python
Ответ на вопрос об инициализации массивов:
можно вручную инициализировать элементы массива своими собственными значениями:
import java.util.Arrays;
int[] arr = new int[n];
// инициализация всех элементов массива своим значением по умолчанию
Arrays.fill(arr, myDefaultValue);
// инициализация части элементов массива, 
// начиная с индекса start включительно и заканчивая индексом stop -- НЕ включительно,
// своим значением по умолчанию
Arrays.fill(arr, start, stop, myDefaultValue);
// Более ясный практический пример из JShell:
    int[] arr = new int[9];
    arr ==> int[9] { 0, 0, 0, 0, 0, 0, 0, 0, 0 }
    Arrays.fill(arr, 0, 8, 23);
    arr ==> int[9] { 23, 23, 23, 23, 23, 23, 23, 23, 0 }
Ответ на вопрос про именованные параметры:
в Kotlin поддерживаются они, а в Java ещё не выяснил пока...'),

('break',
'Оператор break можно использовать в качестве цивилизованного аналога goto,
причём break можно использовать не только в циклах, но и для блоков кода с меткой
Но передать управление посредством break допускается лишь объемлющему коду с меткой,
содержащему данный оператор break 
(нельзя передать управление на блок кода с меткой, не содержащий оператор break)'),

('советы',
'Из нестатического метода можно вызвать статический, но не наоборот!
Следует кидать из методов runtime-исключения (unchecked) -- как правило
Если нужно вернуть из метода null, следует всегда пользоваться типом Optional
(например Optional<ExampleType> )
Возможно, если количество элементов в коллекции заранее известно,
выгоднее сразу создать коллекцию размером равным количеству этих элементов,
нежели каждый раз динамически расширять её, 
добавляя по-одному очередному элементу;'),

('finally',
'Команды в блоке finally выполняются -- ВСЕГДА,
даже если в блоке try|catch будет выполнена команда return|break|continue
Однако если в блоке try|catch будет выполнена команда System.exit(EXIT_CODE),
похоже блок finally не станет выполняться'),

('try',
'Чтобы использовать оператор try с ресурсами, 
открываемые ресурсы должны реализовывать интерфейс "Autocloseable", например:
import java.util.Scanner;
...
try (Scanner scanner = new Scanner(System.in)) {
    ...
} ...
    Если ресурсов несколько, то они закрываются в порядке обратном их открытию, например:
jshell> try (
   ...> AutoCloseable first = () -> System.out.println("First");
   ...> AutoCloseable second = () -> System.out.println("Second");
   ...> AutoCloseable third = () -> System.out.println("Third");) {}
Third
Second
First'),

('советы',
'Чтобы запретить создание объектов некого класса, 
можно сделать приватным(и) конструктор(ы) данного класса или сделать этот класс абстрактным'),

('многопоточность',
'Неконсистентное состояние -- когда данные в процессе изменения, 
но не изменённые до конца'),

('многопоточность',
'Когда поток заходит в synchronized-блок, то он захватывает монитор:
пока монитор захвачен неким потоком, 
другие потоки не могут зайти в этот synchronized-блок,
поскольку они пытаются захватить данный монитор'),

('многопоточность',
'Если нестатический метод помечен модификатором synchronized,
синхронизация идёт на объекте this,
а если статический метод помечен модификатором synchronized,
синхронизация идёт на ИмяКласса.class'),

('сериализация',
'Сериализация -- отображение объекта на массив byte[]
Она нужна, чтобы, например, сохранить состояние объекта в файл,
либо передать его по сети и т.п.
Чтобы исключить какое-либо поле от сериализации, 
нужно пометить его модификатором -- transient
    Чтобы объекты некого класса можно было сериализовать, 
    данный класс должен реализовать интерфейс -- Serializable'),

('мапы',
'Чтобы правильно использовать какой-нибудь объект в качестве ключа в мапе,
в классе данного объекта должны быть переопределены методы equals() и hashCode()
(оба эти метода в своей реализации должны рассматривать одни и те же поля класса, 
в котором они объявлены, а иначе будет не консистентное состояние)
    Для вычисления хеш-кода в хеш-функциях используются простые числа
Вероятность получить коллизии от хеш-функций должна стремиться к минимуму
(Коллизия - это ситуация, когда у нескольких объектов совпадают хеш-коды)
По сути, хеш-код -- своего рода отображение объекта на число -- в целях ускорения операций над ним
        В целом, лучше использовать базовую реализацию хеш-кода, предлагаемую вашей IDE'),

('советы',
'Следует всегда стараться избегать дублирования кода:
если фрагменты кода дублируются дословно, 
их однозначно нужно вынести в отдельный приватный метод,
а затем вызывать данный метод столько раз, 
сколько имеется дублирующихся фрагментов;
    а вот если фрагменты кода дублируются с вариациями,
их также следует постараться вынести в отдельный приватный метод,
принимающий в параметрах те части кода, 
которые различны в целом дублирующихся фрагментах и =*='),

('советы',
'При сравнении строки с константной строкой метод equals() следует вызывать на константе:
String str;
str = ...
"Text".equals(str);
    Потому что если str == null, возникнет исключение -- NullPointerException
Да и в целом, при сравнениях лучше первым операндом ставить литерал'),

('советы',
'Следует выносить все числа в константы, чтобы не было в коде т.н. -- магических чисел
Возможно, данную рекомендацию можно обобщить и на литералы прочих типов...');
