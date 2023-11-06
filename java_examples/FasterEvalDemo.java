public class FasterEvalDemo {
    private static final String TS = "\t\t\t";
    private static final String THE_AND = TS + "AND";
    private static final String THE_OR = TS + "OR";
    private static final String THE_XOR = TS + "XOR";
    
    @FunctionalInterface
    private interface Boolizable {
        boolean func(String msg, boolean flag);
    }
    
    @FunctionalInterface
    private interface Dispatcher {
        boolean dispatch(Boolizable a);
    }
    
    private enum Messages {
        GREETING("Hello, World!"),
        ASK("World, how are you?"),
        BYE("Bye, World!");
        
        private String message;
        
        Messages(String msg) { message = msg; }
        public String getMessage() { return message; }
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
            showTestedMessages(cached);
            System.out.println();
        }   
    }
    
    private static void showTestedMessages(MatrixBools.Params params) {             
        String msgA = Messages.GREETING.getMessage();
        String msgB = Messages.ASK.getMessage();
        String msgC = Messages.BYE.getMessage();
        
        boolean flagA = params.first;
        boolean flagB = params.second;
        boolean flagC = params.third;
        
        Dispatcher theAnd = (a) -> {
            System.out.println(THE_AND);
            return
                a.func(msgA, flagA) && 
                a.func(msgB, flagB) && 
                a.func(msgC, flagC);
        };
                
        Dispatcher theOr = (a) -> {
            System.out.println(THE_OR);
            return 
                a.func(msgA, flagA) || 
                a.func(msgB, flagB) || 
                a.func(msgC, flagC);
        };
                
        Dispatcher theXor = (a) -> {
            System.out.println(THE_XOR);
            return
                a.func(msgA, flagA) ^ 
                a.func(msgB, flagB) ^ 
                a.func(msgC, flagC);
        };
        
        getTripleX(theAnd, theOr, theXor);
    }
    
    private static void getTripleX(Dispatcher ... dsps) {
        for (Dispatcher dsp : dsps) {
            System.out.println(
                TS + dsp.dispatch(FasterEvalDemo::message) + '\n');
        }                  
    }
    
    private static boolean message(String msg, boolean flag) {
        System.out.println(msg);
        return flag;
    }
}