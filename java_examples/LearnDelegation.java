/* Учебный материал по теме: делегирование
 *
 * Третий вид отношений (первый -- композиция, второй -- наследование),
 * не поддерживаемый в Java напрямую, называется делегированием.
 * Он занимает промежуточное положение между наследованием и композицией:
 * экземпляр существующего класса включается в создаваемый класс (как при композиции),
 * но в то же время все методы встроенного объекта становятся доступными в новом классе
 * (как при наследовании). Например, класс SpaceShipControls имитирует модуль управления
 * космическим кораблём:
*/

public class SpaceShipControls {
    void up(int velocity) {}
    void down(int velocity) {}
    void left(int velocity) {}
    void right(int velocity) {}
    void forward(int velocity) {}
    void back(int velocity) {}
    void turboBoost() {}
}

// Для построения космического корабля можно воспользоваться наследованием:

public class SpaceShip extends SpaceShipControls {
    private String name;
    
    public SpaceShip(String name) { this.name = name; }
    public String toString() { return name; }
    
    public static void main(String[] args) {
        SpaceShip protector = new SpaceShip("NSEA Protector");
        protector.up(23);
        protector.down(23);
        protector.left(23);
        protector.right(23);
        protector.forward(23);
        protector.back(23);
        protector.turboBoost();
    }
}

/* Однако космический корабль не может рассматриваться как частный случай своего
 * управляющего модуля -- несмотря на то, что ему, к примеру, можно приказать двигаться
 * вперёд (forward()). Точнее сказать, что SpaceShip СОДЕРЖИТ SpaceShipControls,
 * и в то же время все методы последнего предоставляются классом SpaceShip.
 * Проблема решается при помощи делегирования:
*/

public class SpaceShipDelegation {
    private String name;
    private SpaceShipControls controls = new SpaceShipControls();
    
    public SpaceShipDelegation(String name) { this.name = name; }
    
    // Делегированные методы:   
    public void up(int velocity) { controls.up(velocity); }
    public void down(int velocity) { controls.down(velocity); }
    public void left(int velocity) { controls.left(velocity); }
    public void right(int velocity) { controls.right(velocity); }  
    public void forward(int velocity) { controls.forward(velocity); }
    public void back(int velocity) { controls.back(velocity); } 
    public void turboBoost() { controls.turboBoost(); }
    
    public static void main(String[] args) {
        SpaceShipDelegation protector = new SpaceShipDelegation("NSEA Protector");
        protector.up(23);
        protector.down(23);
        protector.left(23);
        protector.right(23);
        protector.forward(23);
        protector.back(23);
        protector.turboBoost();
    }
}

/* Как видите, вызовы методов переадресуются встроенному объекту controls,
 * а интерфейс остаётся таким же, как и при наследовании.
 * С другой стороны, делегирование позволяет лучше управлять происходящим,
 * потому что вы можете ограничиться небольшим подмножеством методов встроенного объекта.
 * Хотя делегирование не поддерживается языком Java, его поддержка присутствует во многих
 * средах разработки. Например, приведённый пример был автоматически сгенерирован 
 * в JetBrainsIdea IDE.
*/