public class FasterEvalDemo {
    private static final String GREETING = "Hello, World!";
    private static final String ASK = "World, how are you?";
    private static final String BYE = "Bye, World!";
    private static final String TS = "\t\t\t";
    private static final String THE_AND = TS + "AND";
    private static final String THE_OR = TS + "OR";
    private static final String THE_XOR = TS + "XOR";
    private static final String RESULT = TS + "RESULT: ";       
    
    @FunctionalInterface
    private interface Messager {
        boolean message(String msg, boolean flag);
    }
    
    @FunctionalInterface
    private interface Dispatcher {
        boolean dispatch(Messager messager);
    }
    
    private enum Operators {
        AND, OR, XOR
    }

    private enum MatrixBools {
        TTT(true, true, true),
        FFF(false, false, false),
        TTF(true, true, false),
        FFT(false, false, true),
        TFT(true, false, true),
        FTF(false, true, false),
        FTT(false, true, true),
        TFF(true, false, false);
        
        private static class Params {
            private boolean first;
            private boolean second;
            private boolean third;
            
            public String toString() {
                return "(" + first + ", " + second + ", " + third + "):";
            }
        }
        
        private Params params;
    
        MatrixBools(boolean f, boolean s, boolean t) {
            params = new Params();
            params.first = f;
            params.second = s;
            params.third = t;
        }
        
        public Params getParams() { return params; }
    }

    public static void main(String[] args) {
        for (MatrixBools item : MatrixBools.values()) {
            MatrixBools.Params cached = item.getParams();
            System.out.println(cached);
            showMessages(cached);
            System.out.println();
        }   
    }
    
    private static void showMessages(MatrixBools.Params params) {
        Dispatcher theAnd = getDispatcher(params, THE_AND, Operators.AND);
        Dispatcher theOr = getDispatcher(params, THE_OR, Operators.OR);
        Dispatcher theXor = getDispatcher(params, THE_XOR, Operators.XOR);
        
        System.out.println(RESULT + getTripleX(theAnd, theOr, theXor));
    }
    
    private static Dispatcher 
    getDispatcher(MatrixBools.Params params, String title, Operators op) {
        Dispatcher result = null;
        
        switch (op) {
            case AND:
                result = (messager) -> {
                    System.out.println(title);
                    return
                        messager.message(GREETING, params.first) && 
                        messager.message(ASK, params.second) && 
                        messager.message(BYE, params.third);
                };
                break;
                
            case OR:
                result = (messager) -> {
                    System.out.println(title);
                    return
                        messager.message(GREETING, params.first) || 
                        messager.message(ASK, params.second) || 
                        messager.message(BYE, params.third);
                };
                break;
                
            case XOR:
                result = (messager) -> {
                    System.out.println(title);
                    return
                        messager.message(GREETING, params.first) ^ 
                        messager.message(ASK, params.second) ^ 
                        messager.message(BYE, params.third);
                };
                break;           
        }
    
        return result;
    }
    
    private static boolean getTripleX(Dispatcher ... dsps) {
        boolean result = true;
        
        for (Dispatcher dsp : dsps) {
            boolean cached = dsp.dispatch(FasterEvalDemo::message);
            result &= cached;
            System.out.println(TS + cached + '\n');
        }
        
        return result;                  
    }
    
    private static boolean message(String msg, boolean flag) {
        System.out.println(msg);
        return flag;
    }
}