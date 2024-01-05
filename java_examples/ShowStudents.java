public class ShowStudents {
    public record Student(
            int id,
            String name,
            String surname,
            int age,
            int height,
            int weight) 
    {
        private static int counter = 0;
        
        public Student(
                String name,
                String surname,
                int age,
                int height,
                int weight)
        {
            this(++counter, name, surname, age, height, weight);
        }
    
    
        @Override
        public String toString() {
            final String FORMAT = """
            id: %d
            Имя: %s
            Фамилия: %s
            Возраст: %d
            Рост: %d
            Вес: %d
            """;
            
            return String.format(FORMAT, id, name, surname, age, height, weight);
        }
        
        
        public static int getAmount() { return counter; }
        
        
        public static void showInfo() {
            System.out.println("Всего студентов: " + counter);
        }
        
        
        public static void show(Student... students) {
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }


    private static final Student[] STUDENTS = {
            new Student("Аня", "Романова", 21, 170, 55),
            new Student("Диана", "Липатова", 19, 175, 60),
            new Student("Любовь", "Орлова", 20, 165, 50),
            new Student("Жанна", "Закирова", 18, 180, 65),
            new Student("Марина", "Сарычева", 22, 175, 55),
            new Student("Олеся", "Григорьева", 23, 170, 50),
            new Student("Ксения", "Андрюшина", 24, 165, 50),
            new Student("Елизавета", "Максимова", 25, 175, 55),
            new Student("Юлия", "Жукова", 26, 180, 60),
    };


    public static void main(String[] args) {
        Student.showInfo();
        Student.show(STUDENTS);
    }
}