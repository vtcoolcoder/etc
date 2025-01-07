package crypto;


import static crypto.Phrases.CHAR_TO_STRING_MAPPER;
import static crypto.Phrases.STRING_TO_CHAR_MAPPER;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

import java.util.Scanner;
import java.util.stream.Stream;
import java.util.function.Consumer;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Map;


public class Stega {

    private static final String DELIMITER = "\n\n";
    
    
    public static void scanning(final String[] args) { 
        var scanner = new Scanner(System.in).useDelimiter(DELIMITER);
        var stream = scanner.tokens();
                                            
        try (scanner; stream) {
            if (isDecode(args)) {
                printDecodeContent(stream);
            } else {
                printEncodeContent(stream);
            }
        }
    }
    
    
    private static boolean isDecode(final String[] args) {
        requireNonNull(args); 
             
        return args.length > 0 
                && ("-d".equals(args[0]) || "--decode".equals(args[0]));
    }
    
    
    private static void printEncodeContent(final Stream<String> stream) {
        printContent(stream, 
                s -> s.parallel().map(Stega::getWrappingTextByEncodedPhrase)
                      .forEach(System.out::print));       
    }
    
    
    private static void printDecodeContent(final Stream<String> stream) {
        printContent(stream, s -> System.out.print(getContentByStream(s)));   
    }
    
    
    private static void printContent(
            final Stream<String> stream, 
            final Consumer<Stream<String>> action
    ) {
        requireNonNull(stream);
        requireNonNull(action);    
        action.accept(stream);
    }
    
    
    public static String getWrappingTextByEncodedPhrase(final String encodedBase64Phrase) {
        requireNonNull(encodedBase64Phrase);
        
        return encodedBase64Phrase.chars().parallel()
                .mapToObj(Stega::mapToString)
                .collect(joining(DELIMITER));
    }
        
        
    public static String getEncodedPhraseByWrappingText(final String text) {
        requireNonNull(text);
        return getContentByStream(Arrays.stream(text.split(DELIMITER)));
    }   
    
    
    private static String getContentByStream(final Stream<String> stream) {
        return stream.parallel().map(Stega::mapToChar)
                     .map(e -> "" + e)
                     .collect(joining(""));
    }
        
        
    public static String mapToString(final int key) {   
        return tryGetValueByKey(CHAR_TO_STRING_MAPPER, (char) key);
    }
        
    
    public static char mapToChar(final String key) {
        requireNonNull(key);
        return tryGetValueByKey(STRING_TO_CHAR_MAPPER, key);
    }
        
        
    private static <T, R> R tryGetValueByKey(final Map<T, R> map, final T key) {
        requireNonNull(map);
        requireNonNull(key);
                 
        if (map.containsKey(key)) {
            return map.get(key);
        } else {
            throw new NoSuchElementException(
                    "Такого ключа нет: %s".formatted(key));
        }      
    }
}