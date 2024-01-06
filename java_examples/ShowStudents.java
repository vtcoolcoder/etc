public class ShowStudents {
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
        System.out.println();
        Student.show(STUDENTS);
    }
}