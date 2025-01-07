//package squarequation;

enum SetModeList implements Flags {    
    ALL_EQ_ZERO(ZERO, ZERO, ZERO), 
    ALL_NE_ZERO(OTHER, OTHER, OTHER), 
    ONLY_FIRST_EQ_ZERO(ZERO, OTHER, OTHER), 
    ONLY_FIRST_NE_ZERO(OTHER, ZERO, ZERO),
    ONLY_SECOND_EQ_ZERO(OTHER, ZERO, OTHER), 
    ONLY_SECOND_NE_ZERO(ZERO, OTHER, ZERO),
    ONLY_THIRD_EQ_ZERO(OTHER, OTHER, ZERO), 
    ONLY_THIRD_NE_ZERO(ZERO, ZERO, OTHER);     
    
    private boolean[] mode;
    
    SetModeList(boolean a, boolean b, boolean c) { mode = new boolean[] {a, b, c}; }  
    public boolean[] getMode() { return mode; }   
}