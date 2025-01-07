class TestNamedParams
{
    static void test(String str, int i, double d)
    {
        System.out.println(str + i + " " + d);
        str = new String("Hello");
        i = 9;
        d = 6.9;
    }
    
    public static void main(String[] args)
    {
        String s = "Greeting ";
        int i = 6;
        double d = 9.6;
        
        test(s, i, d);
        test(s, i, d);
        
        //System.out.println(s + i + " " + d);
    }
}