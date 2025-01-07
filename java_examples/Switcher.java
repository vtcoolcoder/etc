import java.util.*;

public class Switcher
{
    Switcher()
    {
        initSelector();
    }
    
    private static final String FIRST = "Первый";
    private static final String SECOND = "Второй";
    private static final String THIRD = "Третий";
    private static final String FOURTH = "Четвёртый";
    private static final String FIFTH = "Пятый";
    private static final String SIXTH = "Шестой";
    
    private static final String[] SELECTING_LIST =
    {
        FIRST,
        SECOND,
        THIRD,
        FOURTH,
        FIFTH,
        SIXTH
    };
    
    private class Methoder
    {
        private String name;
        
        Methoder(String name)
        {
            this.name = name;
        }
        
        public void show() 
        {
            System.out.println(name);   
        }
    }
    
    private LinkedHashMap<String, Methoder> selector =
        new LinkedHashMap<String, Methoder>();
        
    private void initSelector()
    {       
        for (int i = 0; i < SELECTING_LIST.length; i++)
        {
            selector.put(SELECTING_LIST[i], 
                new Methoder(SELECTING_LIST[i]));
        }
    }  
    
    private void selectDoing(String switched)
    {        
        Iterator iter = selector.entrySet().iterator();
        
        while (iter.hasNext())  
        {
            Map.Entry entry = (Map.Entry)iter.next();         
            String key = (String)entry.getKey();
            
            if (switched.equals(key))
            {             
                getItem(key).show();
            }       
        }
    }
    
    private Methoder getItem(String key)
    {
        return selector.get(key);
    }  
    
    public static void main(String[] args)
    {
        Switcher aSwitcher = new Switcher();
        
        for (int i = SELECTING_LIST.length - 1; i >=0; i--)
        {
            aSwitcher.selectDoing(SELECTING_LIST[i]);
        }   
    }
}