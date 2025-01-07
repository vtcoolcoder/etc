package dumbell.constants;


import static dumbell.properties.Properties.getProperties;

import dumbell.enums.*;
import java.util.Objects;
import java.util.Arrays;
import java.util.stream.DoubleStream;


public interface DefaultGripData {

    String[] COLORS = DefaultColors.getColors();
    double[] ALL_WEIGHTS = generateAllWeights();
    double GRIP_WEIGHT = getProperties().gripWeight();
    int SIDE_AMOUNT = getProperties().sideAmount();
    int DISK_AMOUNT = getProperties().diskAmount();
    int MIN_DISK_AMOUNT = getProperties().minDiskAmount();
    int MAX_DISK_AMOUNT = getProperties().maxDiskAmount();
    
    
    private static double[] generateAllWeights() {
        return Arrays.stream(DefaultWeights.values())
                .map(DefaultGripData::getTheSameDiskSequence)
                .flatMapToDouble(Arrays::stream)
                .toArray();
    }
    
    
    private static double[] getTheSameDiskSequence(DefaultWeights weight) {
        Objects.requireNonNull(weight);
        return DoubleStream.generate(weight::getWeight)
                .limit(weight.getTheSameDiskAmount())
                .toArray();
    }
}