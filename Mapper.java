import static Ganteli.Represent;

import java.util.stream.DoubleStream;
import java.util.function.Predicate;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;


public class Mapper {

    public String toIntOrFractString(double number) {
        return (0 == number % 1) 
                ? String.valueOf((int) number) 
                : String.valueOf(number);
    }
    
    
    public double[] toTheSameDiskSequence(Ganteli ganteli) {
        return DoubleStream.generate(ganteli::getWeight)
                .limit(ganteli.getTheSameDiskAmount())
                .toArray();
    }
    
    
    public List<Represent> toFilledAsymmetricComboContainer(double[] resultSums) {
        var asymmetricCombo = new ArrayList<Represent>();   
        fillTwoItems(resultSums, (iElement, jElement) -> asymmetricCombo.add(new Represent(
                iElement + jElement + GRIF_WEIGHT,
                STR."\{toIntOrFractString(jElement)} + \{toIntOrFractString(iElement)}"
        )));           
        Collections.sort(asymmetricCombo);    
        return asymmetricCombo;
    }
    
    
    public int[] toValidUniqueDiskAmountNumbers(String[] args) {
        Predicate<String> isNumber = str -> {
            try {
                Integer.valueOf(str);
                return true;
            } catch (NumberFormatException _) {
                return false;
            }
        };
        
        return Arrays.stream(args)
                .filter(isNumber)
                .mapToInt(Integer::valueOf)
                .sorted()
                .distinct()
                .filter(Ganteli::isDiskAmountBelongToValidRange)
                .toArray();
    }
}