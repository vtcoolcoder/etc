import java.util.*;
import java.io.*;

import static java.lang.System.out;

public class BooleanMatrix implements Serializable
{  
    Content content = new Content();
    
    BooleanMatrix()
    {
        out.println(content);   
    }

    private enum Lines
    {  
        EMPTY_LINE(),
        
        NEW_LINE()
        {
            public String getLine()
            {
                return "\n";
            }      
        },
        
        FILLED_LINE()
        {
            public String getLine()
            {
                return repeatLine("*", 90);
            }
        };
        
        public String getLine()
        {
            return "";
        }      
        
        private static String repeatLine(String source, int repeater)
        {
            StringBuilder result = new StringBuilder();
            
            for (int i = 1; i <= repeater; i++)
            {
                result.append(source);
            }
            
            return result.toString();
        }
    };
    
    private Lines lines;
    
    private static class Content implements Serializable
    {
        private static final String[] KEYS =
        {
            "true AND true == ",
            "true AND false == ",
            "false AND true == ",
            "false AND false == ",
            "true OR true == ",
            "true OR false == ",
            "false OR true == ",
            "false OR false == ",
            "true XOR true == ",
            "true XOR false == ",
            "false XOR true == ",
            "false XOR false == ",
            "NOT (true AND true) == ",
            "NOT (true AND false) == ",
            "NOT (false AND true) == ",
            "NOT (false AND false) == ",
            "NOT (true OR true) == ",
            "NOT (true OR false) == ",
            "NOT (false OR true) == ",
            "NOT (false OR false) == ",
            "NOT (true XOR true) == ",
            "NOT (true XOR false) == ",
            "NOT (false XOR true) == ",
            "NOT (false XOR false) == "
        };   
            
        private static final boolean[] VALUES = 
        {
            (true  && true  ),
            (true  && false ),
            (false && true  ),
            (false && false ),
            (true  || true  ),
            (true  || false ),
            (false || true  ),
            (false || false ),
            (true  ^ true   ),
            (true  ^ false  ),
            (false ^ true   ),
            (false ^ false  ),
            (! (true  && true )),
            (! (true  && false)),
            (! (false && true )),
            (! (false && false)),
            (! (true  || true )),
            (! (true  || false)),
            (! (false || true )),
            (! (false || false)),
            (! (true  ^ true  )),
            (! (true  ^ false )),
            (! (false ^ true  )),
            (! (false ^ false ))
        };
            
        private static final String[] BOOLEAN_MATRIX = 
        {
            KEYS[0] + VALUES[0],
            KEYS[1] + VALUES[1],
            KEYS[2] + VALUES[2],
            KEYS[3] + VALUES[3],
            Lines.EMPTY_LINE.getLine(),
            KEYS[4] + VALUES[4],
            KEYS[5] + VALUES[5],
            KEYS[6] + VALUES[6],
            KEYS[7] + VALUES[7],
            Lines.EMPTY_LINE.getLine(),
            KEYS[8] + VALUES[8],
            KEYS[9] + VALUES[9],
            KEYS[10] + VALUES[10],
            KEYS[11] + VALUES[11],
            Lines.EMPTY_LINE.getLine(),
            KEYS[12] + VALUES[12],
            KEYS[13] + VALUES[13],
            KEYS[14] + VALUES[14],
            KEYS[15] + VALUES[15],
            Lines.EMPTY_LINE.getLine(),
            KEYS[16] + VALUES[16],
            KEYS[17] + VALUES[17],
            KEYS[18] + VALUES[18],
            KEYS[19] + VALUES[19],
            Lines.EMPTY_LINE.getLine(),
            KEYS[20] + VALUES[20],
            KEYS[21] + VALUES[21],
            KEYS[22] + VALUES[22],
            KEYS[23] + VALUES[23]
        };    
            
        private static LinkedHashMap<String, Boolean> matrix = 
            new LinkedHashMap<String, Boolean>();
            
        private enum ContentModes 
        {
            MATRIX_MAP_MODE, 
            MATRIX_SOURCE_MODE
        };
        
        private ContentModes contentMode = ContentModes.MATRIX_MAP_MODE;  
        
        private enum NewLineModes 
        {
            NE_MATRIX_LIMIT, 
            EQ_MATRIX_LIMIT, 
            MOD_BY_4
        };  
            
        private enum Limits
        {      
            MATRIX_LIMIT(),
            
            MATRIX_MAP_LIMIT()
            {
                public int getLimit()
                {
                    return KEYS.length - 1;
                }
            };
            
            public int getLimit()
            {
                return BOOLEAN_MATRIX.length - 1;
            }
        };  
        
        Content()
        {
            setMatrixMap();
        }
            
        Content(ContentModes mode)
        {
            this();
            contentMode = mode;    
        } 
        
        public String toString()
        {  
            String result = Lines.EMPTY_LINE.getLine();
            
            switch (contentMode)
            {
                case MATRIX_MAP_MODE:
                    result = getMatrixMap();
                    break;
                    
                case MATRIX_SOURCE_MODE:
                    result = getMatrixSource();
                    break;       
            }
            
            return result;
        }   
        
        private static void setMatrixMap()
        {       
            for (int i = 0; isContinueLoop(i); i++)
            {
                addValue(i);
            }
        } 
              
        private static String getMatrixMap()
        {
            StringBuilder result = new StringBuilder();
            int counter = 0;
            
            result.append(Lines.FILLED_LINE.getLine());
            result.append(Lines.NEW_LINE.getLine());
            result.append(Lines.NEW_LINE.getLine());
            
            for (String key : KEYS)
            {
                boolean value = matrix.get(key);
                result.append(key);
                result.append(value);
                
                if (! isNewLine(counter, NewLineModes.EQ_MATRIX_LIMIT))
                {
                    result.append(Lines.NEW_LINE.getLine());
                    
                    if (isNewLine(++counter, NewLineModes.MOD_BY_4))
                    {
                        result.append(Lines.NEW_LINE.getLine());
                    }    
                    
                    if (key == KEYS[11])
                    {
                        result.append(Lines.FILLED_LINE.getLine());
                        result.append(Lines.NEW_LINE.getLine());
                        result.append(Lines.NEW_LINE.getLine());
                    }
                }
            }
            
            result.append(Lines.NEW_LINE.getLine());
            result.append(Lines.NEW_LINE.getLine());
            result.append(Lines.FILLED_LINE.getLine());
            
            return result.toString();
        }
        
        private static String getMatrixSource()
        {
            StringBuilder result = new StringBuilder();
            int counter = 0;
            
            result.append(Lines.FILLED_LINE.getLine());
            result.append(Lines.NEW_LINE.getLine());
            result.append(Lines.NEW_LINE.getLine());

            for (String part : BOOLEAN_MATRIX)
            {
                result.append(part);
                
                if (isNewLine(counter++, NewLineModes.NE_MATRIX_LIMIT))
                {
                    result.append(Lines.NEW_LINE.getLine());
                }
            }
            
            result.append(Lines.NEW_LINE.getLine());
            result.append(Lines.NEW_LINE.getLine());
            result.append(Lines.FILLED_LINE.getLine());
                    
            return result.toString();
        }
        
        private static boolean isContinueLoop(int i)
        {
            return (i < KEYS.length);
        }          
        
        private static void addValue(int i)
        {   
            matrix.put(KEYS[i], VALUES[i]);
        }
        
        private static boolean isNewLine(int counter, NewLineModes selectedMode)
        {
            boolean result = false;
            
            switch (selectedMode)
            {
                case NE_MATRIX_LIMIT:
                    result = (counter != Limits.MATRIX_LIMIT.getLimit());
                    break;
                    
                case EQ_MATRIX_LIMIT:
                    result = (counter == Limits.MATRIX_MAP_LIMIT.getLimit());
                    break;
                    
                case MOD_BY_4:
                    result = (counter % 4 == 0);
                    break;
            }
            
            return result;
        }        
    }
                 
                 
    public static void main(String[] args)
    {
        BooleanMatrix matrix = new BooleanMatrix();
        
        try
        {
            FileOutputStream fs = new FileOutputStream("matrix.ser");
            ObjectOutputStream os = new ObjectOutputStream(fs);
            
            os.writeObject(matrix);
            os.close();
        }
        
        catch (Exception e)
        {
            e.printStackTrace();
        }
       
       /*   
       out.println(
           new Content(Content.ContentModes.MATRIX_SOURCE_MODE)); 
       */    
       
    }   
}