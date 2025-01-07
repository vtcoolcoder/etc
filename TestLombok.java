package test_lombok;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Data;
import lombok.Value;
import lombok.Builder;

//import static test_lombok.Params.builder;


public class TestLombok {
    public static void main(String[] args) {
        generateContent(true);
        generateContent(false);
        
        check();
        
        Author author = new Author(0, "Пётр", "Зорин");
        Author secondAuthor = new Author(0, "Пётр", "Зорин");
        Author thirdAuthor = new Author(1, "Цири", "Хаоса Дитя");
        
        System.out.printf("%n%n%s%n%s%n%s%n", author, secondAuthor, thirdAuthor);
        
        System.out.printf(
                """
                author.equals(secondAuthor): %b
                author.equals(thirdAuthor): %b
                secondAuthor.equals(author): %b
                secondAuthor.equals(thirdAuthor): %b
                thirdAuthor.equals(author): %b
                thirdAuthor.equals(secondAuthor): %b
                """,
                
                author.equals(secondAuthor),
                author.equals(thirdAuthor),
                secondAuthor.equals(author),
                secondAuthor.equals(thirdAuthor),
                thirdAuthor.equals(author),
                thirdAuthor.equals(secondAuthor));
                
        System.out.println();
                
        for (Author currentAuthor : new Author[] { author, secondAuthor, thirdAuthor }) {
            System.out.printf("id: %d%nName: %s%nSurname: %s%n%n",
                    currentAuthor.getId(),
                    currentAuthor.getName(),
                    currentAuthor.getSurname());
        }
                      
        showData(Params.builder().name("GoyGaya").symbol('G').id(23).delta(23.23).on(true).build());
    }
    
    
    private static void generateContent(boolean isUsingSetters) {
        Tested tested; 
        
        if (isUsingSetters) {
            tested = new Tested();
        
            tested.setName("Виктория");
            tested.setSurname("Романова");
            tested.setAge(23);
            tested.setHeight(175);
            tested.setWeight(55);
            tested.setMale(false);
        } else {
            tested = new Tested("Виктор", "Иванов", 32, 180, 80, true);
        }
              
        show(tested);
        
        System.out.println("\n" + tested + "\n");
    }
    
    
    private static void show(Tested tested) {
        System.out.printf("Имя: %s%nФамилия: %s%nВозраст: %d%nРост: %d%nВес: %d%nПол: %s%n",
                tested.getName(),
                tested.getSurname(),
                tested.getAge(),
                tested.getHeight(),
                tested.getWeight(),
                tested.isMale() ? "мужской" : "женский"
        );
    }
    
    
    private static void check() {
        Tested tested = new Tested("Goy", "Gaya", 23, 23, 23, true);
        Tested anotherTested = new Tested("Goy", "Gaya", 23, 23, 23, true);
        
        System.out.println(tested == anotherTested);
        System.out.println(tested.hashCode() + " | " + anotherTested.hashCode());
        System.out.println(tested.equals(anotherTested));
        System.out.println(anotherTested.equals(tested));
    }
    
    
    private static void showData(Params params) {
        String name = params.getName();
        char symbol = params.getSymbol();
        int id = params.getId();
        double delta = params.getDelta();
        boolean isOn = params.isOn();
        
        System.out.printf("Name: %s%nSymbol: %c%nId: %d%nDelta: %f%nIsOn: %b%n%n", 
                name, symbol, id, delta, isOn);
    }
}


@NoArgsConstructor
@AllArgsConstructor
@Data
//@Getter
//@Setter
//@ToString
//@EqualsAndHashCode
class Tested {
    private String name;
    private String surname;
    private int age;
    private int height;
    private int weight; 
    private boolean male;   
}


@Value
class Author {
    int id;
    String name;
    String surname;
}


@Getter
@Setter
@Builder
class Params {
    private String name;
    private char symbol;
    private int id;
    private double delta;
    private boolean on;
}