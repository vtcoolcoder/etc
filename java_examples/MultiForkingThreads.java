public class MultiForkingThreads {
    @FunctionalInterface
    private interface Tryable {
        void trying() throws Exception;
    }


    private static final int MAGIC = 2323;

    private static final String ZERO = "";
    private static final String ONE = "\t";
    private static final String TWO = "\t\t";
    private static final String THREE = "\t\t\t";
    
    private static final String MAIN = "метода main";
    private static final String CHTHRFRM = " дочернего потока от ";
    private static final String SPR = " суперпотока";
    private static final String FST = "первого";
    private static final String SEC = "второго";
    
    private static final String FSUPER = FST + SPR;
    private static final String FSUBFROMFSUPER = FST + CHTHRFRM + FSUPER;
    private static final String FSUBFROMFSUBFROMFSUPER = FST + CHTHRFRM + FSUBFROMFSUPER;
    private static final String SSUBFROMFSUBFROMFSUPER = SEC + CHTHRFRM + FSUBFROMFSUPER;
    private static final String SSUBFROMFSUPER = SEC + CHTHRFRM + FSUPER;
    private static final String FSUBFROMSSUBFROMFSUPER = FST + CHTHRFRM + SSUBFROMFSUPER;
    private static final String SSUBFROMSSUBFROMFSUPER = SEC + CHTHRFRM + SSUBFROMFSUPER;
    
    private static final String SSUPER = SEC + SPR;
    private static final String FSUBFROMSSUPER = FST + CHTHRFRM + SSUPER;
    private static final String FSUBFROMFSUBFROMSSUPER = FST + CHTHRFRM + FSUBFROMSSUPER;
    private static final String SSUBFROMFSUBFROMSSUPER = SEC + CHTHRFRM + FSUBFROMSSUPER;
    private static final String SSUBFROMSSUPER = SEC + CHTHRFRM + SSUPER;
    private static final String FSUBFROMSSUBFROMSSUPER = FST + CHTHRFRM + SSUBFROMSSUPER;
    private static final String SSUBFROMSSUBFROMSSUPER = SEC + CHTHRFRM + SSUBFROMSSUPER;
    

    public static void main(String[] args) throws Exception {
        createTasks(MAIN, ZERO, () -> { 
            createTasks(FSUPER, ONE, () -> {    
                createTasks(FSUBFROMFSUPER, TWO, () -> {   
                    createTasks(FSUBFROMFSUBFROMFSUPER, THREE, MultiForkingThreads::justSleep);
                            
                    createTasks(SSUBFROMFSUBFROMFSUPER, THREE, MultiForkingThreads::justSleep);
                });
                       
                        
                createTasks(SSUBFROMFSUPER, TWO, () -> {  
                    createTasks(FSUBFROMSSUBFROMFSUPER, THREE, MultiForkingThreads::justSleep);
                            
                    createTasks(SSUBFROMSSUBFROMFSUPER, THREE, MultiForkingThreads::justSleep);
                });
            });
            
            
            
            createTasks(SSUPER, ONE, () -> {
                createTasks(FSUBFROMSSUPER, TWO, () -> { 
                    createTasks(FSUBFROMFSUBFROMSSUPER, THREE, MultiForkingThreads::justSleep);
                            
                    createTasks(SSUBFROMFSUBFROMSSUPER, THREE, MultiForkingThreads::justSleep);
                });
                
                        
                createTasks(SSUBFROMSSUPER, TWO, () -> {    
                    createTasks(FSUBFROMSSUBFROMSSUPER, THREE, MultiForkingThreads::justSleep);
                            
                    createTasks(SSUBFROMSSUBFROMSSUPER, THREE, MultiForkingThreads::justSleep);
                });
            });
        });
    }
    
    
    private static void createTasks(final String item, 
                                    final String offset, 
                                    final Runnable func) {
        final Runnable TASK = () -> wrapping(item, offset, func);
        doWork(new Thread(TASK));
    }
    
    
    private static void wrapping(final String item, 
                                 final String offset, 
                                 final Runnable func) {
        System.out.println(offset + "Старт " + item + "...");    
        func.run();
        System.out.println(offset + "Окончание " + item + "...");
    }
    
    
    private static void doWork(final Thread thread) {
        thread.start();  
        justSleep();  
        trying(() -> thread.join());
    }
    
    
    private static void justSleep() { trying(() -> Thread.sleep(MAGIC)); }
    
    
    private static void trying(final Tryable func) {
        try {
            func.trying();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}