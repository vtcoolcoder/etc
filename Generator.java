


public class Generator {

    public double[] generateUniqueOneSidedSums() {
        return RESULTS.stream()
                .mapToDouble(Represent::sum)
                .distinct()
                .toArray();          
    }  
    
    
    public double[] generateAllWeights() {
        return Arrays.stream(values())
                .map(Ganteli::getTheSameDiskSequence)
                .flatMapToDouble(Arrays::stream)
                .toArray();
    }
}