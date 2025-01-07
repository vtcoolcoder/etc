//package squarequation;

class ModeSelector {
    private SetModeList mode;
    
    SetModeList getMode() {  
        SetModeList result = null;
        
        for (SetModeList currentValue : SetModeList.values()) {   
            if (isValid(currentValue.getIndex())) {
                result = currentValue;
                break;
            }   
        }
        
        return result;
    }
}