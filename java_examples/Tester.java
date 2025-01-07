

public class Tester
{
    public static void main(String[] args)
    {      
        NewerSqrEqual.ConstructorParams params = 
            new NewerSqrEqual.ConstructorParams();
        
        params.setA(666);
        params.setB(999);
        params.setC(-333);
        params.setIsShowAbcd(true);
        params.setIsShowGreeting(false);
        
        System.out.println(new NewerSqrEqual(params).getResult());
        System.out.println(new NewerSqrEqual().getResult());     
    }
}
