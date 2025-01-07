package dumbell;


import static dumbell.Constants.*;
import static dumbell.DumbBell.Represent;
import static java.util.Objects.requireNonNull;
import static java.util.stream.IntStream.range;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;
import java.util.function.Function;


class Filler {

    static void fillTwoDiskComboResultPart(List<Represent> represents,
                                           double[] allWeights) {  
        requireNonNull(represents);  
        requireNonNull(allWeights);
         
        fillTwoItems(allWeights, (iElement, jElement) -> represents.add(new Represent(
                iElement + jElement, 
                STR."\{iElement} + \{jElement}"
        )));
    }
    
    
    static void fillTwoItems(double[] source, BiConsumer<Double, Double> func) {
        requireNonNull(source);
        requireNonNull(func);
              
        IntConsumer secondLoop = i -> 
                range(i + 1, source.length)
                .forEach(j -> func.accept(source[i], source[j]));
        
        range(0, source.length - 1).forEach(secondLoop);
    }
    
      
    static void fillThreeDiskComboResultPart(List<Represent> represents,
                                             double[] allWeights) {
        requireNonNull(represents);
        requireNonNull(allWeights);
                
        Function<int[], Represent> toRepresent = iJK -> 
                new Represent(
                        allWeights[iJK[I_IDX]] + 
                        allWeights[iJK[J_IDX]] + 
                        allWeights[iJK[K_IDX]], 
                        
                        STR."""
                        \{allWeights[iJK[I_IDX]]} + \
                        \{allWeights[iJK[J_IDX]]} + \
                        \{allWeights[iJK[K_IDX]]}\
                        """
                );
                                          
        BiConsumer<Integer, Integer> thirdLoop = (i, j) -> 
                range(j + 1, allWeights.length)
                .mapToObj(k -> new int[] { i, j, k })
                .map(toRepresent)
                .forEach(represents::add);
                
        IntConsumer secondLoop = i -> 
                range(i + 1, allWeights.length - 1)
                .forEach(j -> thirdLoop.accept(i, j));
                
        range(0, allWeights.length - 2).forEach(secondLoop);
    }
    
         
    static void fillFourDiskComboResultPart(List<Represent> represents,
                                            double[] allWeights) {
        requireNonNull(represents);
        requireNonNull(allWeights);
        
        Function<int[], Represent> toRepresent = iJKN -> 
                new Represent(
                        allWeights[iJKN[I_IDX]] + 
                        allWeights[iJKN[J_IDX]] + 
                        allWeights[iJKN[K_IDX]] + 
                        allWeights[iJKN[N_IDX]], 
                        
                        STR."""
                        \{allWeights[iJKN[I_IDX]]} + \
                        \{allWeights[iJKN[J_IDX]]} + \
                        \{allWeights[iJKN[K_IDX]]} + \
                        \{allWeights[iJKN[N_IDX]]}\
                        """
                );
                
        Consumer<int[]> fourthLoop = iJK ->
                range(iJK[K_IDX] + 1, allWeights.length)
                .mapToObj(n -> new int[] { iJK[I_IDX], iJK[J_IDX], iJK[K_IDX], n })
                .map(toRepresent)
                .forEach(represents::add);
                
        BiConsumer<Integer, Integer> thirdLoop = (i, j) ->
                range(j + 1, allWeights.length - 1)   
                .forEach(k -> fourthLoop.accept(new int[] { i, j, k }));    
                
        IntConsumer secondLoop = i ->
                range(i + 1, allWeights.length - 2)
                .forEach(j -> thirdLoop.accept(i, j));
                
        range(0, allWeights.length - 3).forEach(secondLoop);
    }
    
    
    static List<Represent> getFilledAsymmetricComboContainer(double[] resultSums, 
                                                             double gripWeight) {
        requireNonNull(resultSums);
        
        var asymmetricCombo = new ArrayList<Represent>();   
        fillTwoItems(resultSums, (iElement, jElement) -> asymmetricCombo.add(new Represent(
                iElement + jElement + gripWeight,
                STR."\{iElement} + \{jElement}"
        )));           
        Collections.sort(asymmetricCombo);    
        
        return asymmetricCombo;
    }
}