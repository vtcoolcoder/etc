package dumbell.enums;


public enum DefaultWeights {
    
    _0_5(0.5),
    _0_75(0.75, 0),
    _1(1.0),
    _1_25(1.25),
    _1_5(1.5),
    _2(2.0),
    _2_5(2.5),
    _5(5.0);
    
    private double weight; 
    private int theSameDiskPairAmount;


    DefaultWeights(double weight, int theSameDiskPairAmount) {
        this.weight = weight;
        this.theSameDiskPairAmount = theSameDiskPairAmount;
    }   
    
    
    DefaultWeights(double weight) { 
        this.weight = weight; 
        this.theSameDiskPairAmount = 1;
    }
    
    
    public double getWeight() { return weight; }
    
    
    public int getTheSameDiskAmount() { return theSameDiskPairAmount; }
    
    
    @Override
    public String toString() {
        return STR."""
            Вес диска: \{weight}
            Количество пар: \{theSameDiskPairAmount}
            """;
    }
}