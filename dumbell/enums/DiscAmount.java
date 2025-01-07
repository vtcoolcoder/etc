package dumbell.enums;


public enum DiscAmount { 

    ONE(1), 
    TWO(2), 
    THREE(3), 
    FOUR(4); 
    
    private int discAmount;
    
    
    DiscAmount(int discAmount) { this.discAmount = discAmount; }
    
    
    public int getDiscAmount() { return discAmount; }
    
    
    @Override
    public String toString() { return STR."\{discAmount}"; }
}