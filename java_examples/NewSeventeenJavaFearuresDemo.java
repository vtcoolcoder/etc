public class NewSeventeenJavaFearuresDemo {
    private enum Gender { 
        MALE("мужской"), 
        FEMALE("женский"); 
        
        Gender(String gender) { this.gender = gender; }
        
        public String getGender() { return gender; }
        
        private String gender;
    }
    
    
    private enum EyeColor { 
        BLUE("синий"), 
        GREEN("зелёный"), 
        GRAY("серый"), 
        BROWN("коричневый");
        
        EyeColor(String color) { this.color = color; }
        
        public String getColor() { return color; }
        
        private String color; 
    }
    
    
    private enum HairColor { 
        BLONDE("блондин"), 
        BRUNETTE("брюнет"), 
        BROWN("шатен"), 
        ORANGE("рыж"); 
        
        HairColor(String color) { this.color = color; }
        
        public String getColor(Gender gender) {
            char lastSymbol = color.charAt(color.length() - 1);
            return switch (gender) {
                case MALE -> lastSymbol == 'ж' ? color + "ий" : color;
                case FEMALE -> lastSymbol == 'ж' ? color + "ая" : color + "ка";
            };
        }
        
        private String color;
    }
    
    
    private record Student(
        int id,
        String name,
        String surname,
        Gender gender,
        byte age,
        short height,
        short weight,
        EyeColor eyeColor,
        HairColor hairColor,
        String hobby
    ) {}
    
    
    private static Student[] students = {
        new Student(
            0, 
            "Василий",
            "Иванов",
            Gender.MALE,
            (byte) 20,
            (short) 170,
            (short) 70,
            EyeColor.BLUE,
            HairColor.BLONDE,
            "бильярд"),
            
        new Student(
            1,
            "Марина",
            "Сидорова",
            Gender.FEMALE,
            (byte) 18,
            (short) 175,
            (short) 60,
            EyeColor.GREEN,
            HairColor.ORANGE,
            "макияж"),
            
        new Student(
            2,
            "Кристина",
            "Цветаева",
            Gender.FEMALE,
            (byte) 21,
            (short) 165,
            (short) 55,
            EyeColor.GRAY,
            HairColor.BROWN,
            "рисование")
    };
    
    
    public static void main(String[] args) {
        for (Student student : students) {
            System.out.println("Студент: " + 
                               showEyeDetail(student) + "\n" + 
                               getStudentInfo(student));
        }
    }
    
    
    private static String getStudentInfo(final Student student) {
        final String FORMAT = """
        ID: %d
        Имя: %s
        Фамилия: %s
        Пол: %s
        Возраст: %d
        Рост: %d
        Вес: %d
        Цвет глаз: %s
        Цвет волос: %s
        Хобби: %s
        """;
        
        return String.format(
            FORMAT, 
            student.id(),
            student.name(),
            student.surname(),
            student.gender().getGender(),
            student.age(),
            student.height(),
            student.weight(),
            student.eyeColor().getColor(),
            student.hairColor().getColor(student.gender()),
            student.hobby());
    }
    
    
    private static String showEyeDetail(final Student student) {
        final String INFO = "Глаза ";
        return switch (student.eyeColor()) {
            case BLUE, GREEN -> {
                System.err.println(INFO + "светлые");
                yield student.surname() + " " + student.name();
            }
            case GRAY, BROWN -> {
                System.err.println(INFO + "тёмные");
                yield student.surname() + " " + student.name();
            }
        };
    }
}