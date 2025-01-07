import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.StringJoiner;


@Getter
@Builder
@RequiredArgsConstructor
@ToString
public final class TestImmutableBuilder {
    private static final StringJoiner JOINER = new StringJoiner(" | ")
            .add("Имя: %s")
            .add("Фамилия: %s")
            .add("Возраст: %d");
            
    private static final String SHOWFMT = "" + JOINER + "\n";
    
    private static final TestImmutableBuilderBuilder OBJ = builder().age(23).surname("Surname").name("Name");
    
    
    private final String name;
    private final String surname;
    private final int age;


    public static void main(String[] args) {
        System.out.println(OBJ);
        
        TestImmutableBuilder.builder()
                .name("Geralt")
                .surname("Vedmak")
                .age(46)
                .build().show();
                      
        new TestImmutableBuilder("The Witcher", "Warrior", 23).show();
    }
    
    
    private void show() {
        System.out.println(this);
        System.out.printf(SHOWFMT, 
                getName(),
                getSurname(),
                getAge());
    }
}