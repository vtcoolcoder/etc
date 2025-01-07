package crypto;


import static java.util.Objects.requireNonNull;

import java.util.function.UnaryOperator;
import java.util.function.Consumer;
import java.util.Arrays;
import java.util.stream.Stream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.nio.file.Path;


public class Composite {    
    
    private static final int ZERO_SIZE = 0;
    private static final int FIRST_ELEMENT_IDX = 0;
    private static final int SECOND_ELEMENT_IDX = 1;
    private static final int THIRD_ELEMENT_IDX = 2;
    private static final String DEFAULT_KEY_FILENAME = "KEY";
    private static final String DEFAULT_ENCRYPTED_FILENAME = "TEMP";
    private static final String KEY = requireNonNull(KeyGenerator.generateKey());
    private static final String UNSTEGA_LONG_PARAM_NAME = "--unstega";
    private static final String UNSTEGA_SHORT_PARAM_NAME = "-u";
    private static final String UNSTEGA_SCANNER_DELIMITER = "\n\n\n";
    private static final String STEGA_SCANNER_DELIMITER = "\000";
    private static final Path PATH = Path.of(DEFAULT_ENCRYPTED_FILENAME);
    
    private static Coder coder;
    private static UnaryOperator<String> printContent;
    private static Mode mode;
    private static String[] mainArgs = new String[ZERO_SIZE];
    private static String sourceFileName = "";
    
    private enum Mode {STEGA, UNSTEGA}
    
             
    public static void main(String[] args) {
        mainArgs = args;
        init();
        saveKey();
        printContent(getStreamByCLParamsLength());        
    }
    
    
    public static String stega(final String content) {
        requireNonNull(content);
        return Stega.getWrappingTextByEncodedPhrase(coder.encode(content));
    } 
    
    
    public static String unstega(final String content) {
        requireNonNull(content);
        return coder.decode(Stega.getEncodedPhraseByWrappingText(content));
    }
    
    
    private static void saveKey() { saveKey(DEFAULT_KEY_FILENAME); }
    
    
    private static void saveKey(final String fileName) {
        if (Mode.UNSTEGA.equals(mode)) { 
            return ; 
        }
        
        requireNonNull(fileName);
        
        ((ConsumerWithException<String, ? extends Exception>)
                name -> writeIntoFile(new PrintWriter(name), KEY))
                .wrapping().accept(fileName);
    }
   
    
    private static void init() {      
        if (isUnstega()) {
            mode = Mode.UNSTEGA;
            printContent = Composite::unstega;
            coder = new Coder(mainArgs[SECOND_ELEMENT_IDX]);
        } else {
            mode = Mode.STEGA;
            printContent = Composite::stega;
            coder = new Coder(KEY);
            
            if (isSourceContentFromFile()) {
                sourceFileName = mainArgs[FIRST_ELEMENT_IDX];               
            } 
        }
    }
    
    
    private static boolean isUnstega() {        
        return mainArgs.length > 1
                && isSetUnstegaCLParam();
    }
    
    
    private static boolean isSetUnstegaCLParam() {
        return UNSTEGA_LONG_PARAM_NAME.equals(mainArgs[FIRST_ELEMENT_IDX]) 
                || UNSTEGA_SHORT_PARAM_NAME.equals(mainArgs[FIRST_ELEMENT_IDX]);
    }
    
    
    private static void printContent(final Stream<String> stream) {
        requireNonNull(stream);
        
        if (Mode.STEGA.equals(mode)) {
            printContent(stream, 
                    f -> writeIntoFile(new PrintWriter(DEFAULT_ENCRYPTED_FILENAME), f));
        } else {
            printContent(stream, System.out::print);
        }      
    }
    
    
    private static void printContent(final Stream<String> stream, final ConsumerWithException<String, ?> action) {
        requireNonNull(stream);
        requireNonNull(action);
        
        stream.map(printContent::apply).forEach(action.wrapping());
    }
    
    
    private static Stream<String> getStreamByCLParamsLength() {
        return isFileLinesStream() 
                ? getStreamFromFile(PATH, UNSTEGA_SCANNER_DELIMITER).parallel()
                : isSourceContentFromFile()
                        ? getStreamFromFile(Path.of(sourceFileName), STEGA_SCANNER_DELIMITER)
                        : Arrays.stream(getMainArgsByMode());
    }
    
    
    private static boolean isFileLinesStream() {
        return mainArgs.length == 2 && Mode.UNSTEGA.equals(mode);
    }
    
    
    private static boolean isSourceContentFromFile() {
        return mainArgs.length == 1 && Mode.STEGA.equals(mode);
    }
    
    
    private static Stream<String> getStreamFromFile(final Path path, final String delimiter) {
        return ((BiFunctionWithException<Path, String, Stream<String>, ? extends Exception>) 
                (p, d) -> new Scanner(p).useDelimiter(d).tokens())
                .wrapping().apply(path, delimiter);
    }
    
    
    private static String[] getMainArgsByMode() {
        return Mode.UNSTEGA.equals(mode) 
                ? Arrays.copyOfRange(mainArgs, THIRD_ELEMENT_IDX, mainArgs.length) 
                : mainArgs;
    }
    
    
    private static void writeIntoFile(
            //final BiConsumerWithException<PrintWriter, String, ? extends Exception> action,
            final PrintWriter writer,
            final String line
    ) {
        //action.wrapping().accept(writer, line);
        BiConsumerWithException.wrappingTryWithResources(writer, line);
    }
    
    
    /*private static void writeIntoFile(final PrintWriter writer, final String line) {
        try (writer) {
            writer.print(line);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/
}