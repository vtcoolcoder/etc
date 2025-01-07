public class Commons
{
    private static final int NUMBER = 54;

    private static final String PREFIX = "class java.lang.";
    private static final String BYTE = PREFIX + "Byte";
    private static final String SHORT = PREFIX + "Short";
    private static final String INTEGER = PREFIX + "Integer";
    private static final String LONG = PREFIX + "Long";
    private static final String DOUBLE = PREFIX + "Double";
    private static final String FLOAT = PREFIX + "Float";
    private static final String STRING = PREFIX + "String";
    private static final String CHAR = PREFIX + "Character";

    
    public static void main(String[] args)
    {
        System.out.println(add((byte)(NUMBER), (byte)(NUMBER)));
        System.out.println(add((short)(NUMBER), (short)(NUMBER)));
        System.out.println(add((int)(NUMBER), (int)(NUMBER))); 
        System.out.println(add((long)(NUMBER), (long)(NUMBER))); 
        System.out.println(add((double)(NUMBER), (double)(NUMBER)));
        System.out.println(add((float)(NUMBER), (float)(NUMBER))); 
        System.out.println(add("" + NUMBER, "" + NUMBER));
        System.out.println(add((char)(NUMBER), (char)(NUMBER)));  
    }
    
    
    private static <T> T add(T a, T b)
    {
        switch ("" + a.getClass())
        {
            case BYTE: return (T)((byte)((byte)a + (byte)b));
            case SHORT: return (T)((short)((short)a + (short)b));
            case INTEGER: return (T)((int)((int)a + (int)b));
            case LONG: return (T)((long)((long)a + (long)b));
            case DOUBLE: return (T)((double)((double)a + (double)b));
            case FLOAT: return (T)((float)((float)a + (float)b));
            case CHAR: return (T)((char)((char)a + (char)b));
            case STRING: return (T)((String)((String)a + (String)b));
            default: throw new IllegalArgumentException();
        }
    } 
}