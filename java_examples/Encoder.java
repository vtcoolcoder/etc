package encoder;


import java.util.*;


public class Encoder
{
    private static final int FAIL = 1;
    private static final String FT = "\t\t\t\t";
    private static final String NFT = "\n" + FT;
    
    private static final String NO_KEY = "Не задан ключ шифрования!";
    private static final String CONTENT = FT + "СОДЕРЖИМОЕ:";
    private static final String KEY = NFT + "КЛЮЧ:";
    private static final String RESULT = NFT + "РЕЗУЛЬТАТ:";
    private static final String DECODING = NFT + "ПОВТОРНАЯ РАСШИФРОВКА:";
        

    private String key;
    private String content;
    private String encoded;
    private String decoded;
  
    private boolean isShowVerbose = false;
  
  
    public static void main(String[] args)
    {         
        //new Encoder(args).showResult();    
        System.out.print(new Encoder(args));                
    }
   
    
    public Encoder(String[] args)
    {
        key = getKey(args); 
        content = getContent();
        encoded = encode(content);
    }
    
    
    public Encoder(String[] args, boolean isShowVerbose)
    {
        this(args);     
        this.isShowVerbose = isShowVerbose;
    }
    
    
    public String toString()
    {
        return encoded;
    }
    
    
    private String getKey(String[] args)
    {
        StringBuilder key = new StringBuilder();
        
        if (isEqualsZero(args))
        {
            System.err.println(NO_KEY);
            System.exit(FAIL);
        } 
        else
        {        
            for (String keyPart : args)
            {
                key.append(keyPart + "\n");
            }
        } 
        
        return key.toString();
    }
    
    
    private boolean isEqualsZero(String[] args)
    {
        return (args.length == 0);
    }
    
    
    private String getContent()
    {
        StringBuilder buffer = new StringBuilder();
        Scanner aScanner = new Scanner(System.in);
        String encoded = null;
              
        while (true)
        {                   
            try
            {
                encoded = aScanner.nextLine() + "\n";
                int length = encoded.length();
                
                if (isNotEqualsZero(length))
                {
                    if (isSlice(length, encoded))
                    {
                        encoded = encoded.substring(0, length - 2);
                    }
                    
                    buffer.append(encoded);   
                }
            }
            catch (NoSuchElementException ex)     
            {
                if (isNull(encoded))
                {
                    break;
                }
            } 
            finally
            {
                encoded = null;
            }      
        } 
        
        return buffer.toString();
    }
    
    
    private boolean isNotEqualsZero(int length)
    {
        return (length != 0);
    }
    
    
    private boolean isSlice(int length, String encoded)
    {
        return (length > 1) && (encoded.charAt(length - 2) == 0);
    }
    
    
    private boolean isNull(String str)
    {
        return (str == null);
    }


    private String encode(String encoded)
    {
        char[] aKey = key.toCharArray();
        char[] anEncoded = encoded.toCharArray();
        StringBuilder buffer = new StringBuilder();
        char currentSymbol = 0;
        
        for (char encodedPart: anEncoded)
        {   
            for (char keyPart : aKey)
            {
                currentSymbol = (char)(encodedPart ^ keyPart);
            }
                     
            buffer.append("" + currentSymbol);           
        }
        
        return buffer.toString();
    }
    
       
    private void showResult()
    {
        if (isShowVerbose)
        {
            System.err.println(CONTENT);  
            System.err.print(content);        
            
            System.err.println(KEY);  
            System.err.println(key);  
            
            System.err.println(RESULT);
        }
              
        System.out.print(encoded);
        
        if (isShowVerbose)
        {
            decoded = encode(encoded);
            System.err.print(DECODING + "\n");
            System.err.print(decoded);   
        }
    }
}