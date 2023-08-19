

public class Tester
{
    public static void main(String[] args)
    {      
        for (double i = -2.0; i <= 2.0; i += 0.333)
		{
			for (double j = -2.0; j <= 2.0; j += 0.333)
			{
				for (double k = -2.0; k <= 2.0; k += 0.333)
				{
						NewerSqrEqual.ConstructorParams params =
							new NewerSqrEqual.ConstructorParams();
				        params.setA(i);
				        params.setB(j);
				        params.setC(k);
				        params.setIsShowAbcd(true);
				        params.setIsShowGreeting(false);
				        
				        System.out.println(
				        	new NewerSqrEqual(params).getResult());
				        System.out.println();
				}
			}
		}

        System.out.println(
        	new NewerSqrEqual().getResult());     
    }
}
