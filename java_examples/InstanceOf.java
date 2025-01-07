// Продемонстрировать применение операции instanceof
class A {
    int i;
    int j;
}

class B {
    int i;
    int j;
}

class C extends A {
    int k;
}

class D extends A {
    int k;
}

public class InstanceOf {
    public static void main(String[] args) {
        A a = new A();
        B b = new B();
        C c = new C();
        D d = new D();
        
        if (a instanceof A) {
            System.out.println("a является экземпляром А");
        }       
        if (b instanceof B) {
            System.out.println("b является экземпляром B");
        }        
        if (c instanceof C) {
            System.out.println("c является экземпляром С");
        }
        
        if (c instanceof A) {
            System.out.println("c можно привести к типу А");
        }
        if (a instanceof C) {
            System.out.println("a можно привести к типу С");
        }
        
        System.out.println();
        
        // Сравнить с порождёнными типами
        A obj;
        obj = d; // ссылка на объект d
        System.out.println("obj теперь ссылается на d");
        if (obj instanceof D) {
            System.out.println("obj является экземпляром D");
        }
        
        System.out.println();
        
        obj = c; // ссылка на объект с
        System.out.println("obj теперь ссылается на с");
        
        if (obj instanceof D) {
            System.out.println("obj можно привести к типу D");
        } else {
            System.out.println("obj нельзя привести к типу D");
        }
        
        if (obj instanceof A) {
            System.out.println("obj можно привести к типу А");
        }
        
        System.out.println();
        
        // Все объекты могут быть приведены к типу Object
        if (a instanceof Object) {
            System.out.println("a можно привести к типу Object");
        }
        if (b instanceof Object) {
            System.out.println("b можно привести к типу Object");
        }
        if (c instanceof Object) {
            System.out.println("c можно привести к типу Object");
        }
        if (d instanceof Object) {
            System.out.println("d можно привести к типу Object");
        }
    }
}