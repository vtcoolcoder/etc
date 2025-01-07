package dumbell.enums;


public enum SideAmount { 

    ONE(1), 
    TWO(2); 

    private int sideAmount;
    
    
    SideAmount(int sideAmount) { this.sideAmount = sideAmount; }   
    
    
    public int getSideAmount() { return sideAmount; }
    
    
    @Override
    public String toString() { return STR."\{sideAmount}"; }
}