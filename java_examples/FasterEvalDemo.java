public class FasterEvalDemo {
    private static final String GREETING = "Hello, World!";
    private static final String ASK = "World, how are you?";
    private static final String BYE = "Bye, World!";
    private static final String TS = "\t\t\t";
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
        AND("AND"), OR("OR"), XOR("XOR");
        private String title;
        Operators(String s) { title = s; }
        public String getTitle() { return TS + '\t' + title; }
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
            
            public Params(boolean f, boolean s, boolean t) {
                first = f;
                second = s;
                third = t;
            }
            
            public String toString() {
                return "(" + first + ", " + second + ", " + third + "):";
            }
            
            public boolean getFirst() { return first; }
            public boolean getSecond() { return second; }
            public boolean getThird() { return third; }
        }
        
        private Params params;
    
        MatrixBools(boolean first, boolean second, boolean third) {
            params = new Params(first, second, third);    
        }
        
        public Params getParams() { return params; }
    }
    
    private static class Composite {
        private MatrixBools.Params params; 
        private Operators operator;
        
        public Composite(MatrixBools.Params p, Operators op) {
            params = p;
            operator = op;
        } 
           
        public MatrixBools.Params getParams() { return params; }
        public Operators getOperator() { return operator; }
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
        Composite andArgs = new Composite(params, Operators.AND);
        Composite orArgs = new Composite(params, Operators.OR);
        Composite xorArgs = new Composite(params, Operators.XOR);
        
        Dispatcher theAnd = getDispatcher(andArgs);
        Dispatcher theOr = getDispatcher(orArgs);
        Dispatcher theXor = getDispatcher(xorArgs);
        
        System.out.println(RESULT + getTripleX(theAnd, theOr, theXor));
    }
    
    private static Dispatcher getDispatcher(Composite params) {
        Dispatcher result = null;
        
        Operators operator = params.getOperator();
        String title = operator.getTitle();
        
        MatrixBools.Params matrix = params.getParams();
        boolean first = matrix.getFirst();
        boolean second = matrix.getSecond();
        boolean third = matrix.getThird();
        
        switch (operator) {
            case AND:
                result = (messager) -> {
                    System.out.println(title);
                    return
                        messager.message(GREETING, first) && 
                        messager.message(ASK, second) && 
                        messager.message(BYE, third);
                };
                break;
                                	
            case OR:
                result = (messager) -> {
                    System.out.println(title);
                    return
                        messager.message(GREETING, first) || 
                        messager.message(ASK, second) || 
                        messager.message(BYE, third);
                };
                break;
                
            case XOR:
                result = (messager) -> {
                    System.out.println(title);
                    return
                        messager.message(GREETING, first) ^ 
                        messager.message(ASK, second) ^ 
                        messager.message(BYE, third);
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