import java.io.*;

public class Template
{
    private static final String FILENAME = "charSet.txt";
    
    public static void main(String[] args) throws Exception
    {
        FileOutputStream f = new FileOutputStream(FILENAME);
        
        for (char i = 33; i <= 1000; i++)
        {
            System.out.print(i);
            f.write(i);
        }
        
        f.close();
    }
}