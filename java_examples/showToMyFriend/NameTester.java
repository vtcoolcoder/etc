public class NameTester
{
    public static void main(String[] args)
    {
        for (Names name : Names.values())
        {
            System.out.println(name.getName());
        }
    }
}