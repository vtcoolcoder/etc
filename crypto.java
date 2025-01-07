
import javax.crypto.*;
import javax.crypto.spec.*;
var bytes = "Doloress".repeat(21).getBytes(); // нужен ключ длиной 168 символов
var spec = new SecretKeySpec(bytes, "DESede");
var key = SecretKeyFactory.getInstance("DESede").generateSecret(spec);
var cipher = Cipher.getInstance("DESede");
cipher.init(Cipher.ENCRYPT_MODE, key);
var phrase = "Действия людей не влияют на воина, потому что у него больше нет никаких ожиданий.";
var encoded = cipher.doFinal(phrase.getBytes());
var base64Encoder = Base64.getEncoder();
var base64EncodedStr = base64Encoder.encodeToString(encoded);
System.out.println(base64EncodedStr);
cipher.init(Cipher.DECRYPT_MODE, key);
var base64Decoder = Base64.getDecoder();
var decoded = cipher.doFinal(base64Decoder.decode(base64EncodedStr));
System.out.println(new String(decoded));


import static java.util.Objects.requireNonNull;

import java.security.Key;
import javax.crypto.*;
import javax.crypto.spec.*;

public class Coder {
    private static final String ALGORITHM = "DESede";
    private static final Cipher CIPHER;
    private static final int VALID_KEY_LENGTH = 168;
    private static final Base64.Encoder ENCODER = Base64.getEncoder();
    private static final Base64.Decoder DECODER = Base64.getDecoder();

    private final Key key;
    
    static {
        try {
            CIPHER = Cipher.getInstance(ALGORITHM);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
       
    public Coder(final String pwd) {    
        checkKeyValidity(pwd);
        try {
            this.key = SecretKeyFactory.getInstance(ALGORITHM)
                    .generateSecret(new SecretKeySpec(pwd.getBytes(), ALGORITHM));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }              
    }
    
    
    public String encode(final String phrase) {
        requireNonNull(phrase);
        try {
            CIPHER.init(Cipher.ENCRYPT_MODE, key);
            return ENCODER.encodeToString(CIPHER.doFinal(phrase.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }      
    }
    
    
    public String decode(final String encodedPhrase) {
        requireNonNull(encodedPhrase);
        try {
            CIPHER.init(Cipher.DECRYPT_MODE, key);
            return new String(CIPHER.doFinal(DECODER.decode(encodedPhrase)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    
    private static void checkKeyValidity(final String pwd) {
        requireNonNull(pwd);
        if (pwd.length() != VALID_KEY_LENGTH) {
            throw new IllegalArgumentException(
                    """
                    Неверная длина ключа: %d
                    Длина ключа должна быть %d символов
                    """.formatted(pwd.length(), VALID_KEY_LENGTH));
        }
    }
    
    
    public static class KeyGenerator {
        private static final int LIMIT = 168;
        private static final int DEFAULT_SHUFFLE_LIMIT = 23 * 7;
        private static final List<Character> SYMBOLS;
        private static final SplittableRandom RANDOM = new SplittableRandom();
        
        
        static {
            var bufferedSymbols = new ArrayList<Character>();
            
            for (var outer : new char[][] {{33, 126}, {200, 275}}) {
                for (var currentSymbol = outer[0]; currentSymbol < outer[1]; currentSymbol++) {
                    bufferedSymbols.add(currentSymbol);   
                }
            }
            
            SYMBOLS = bufferedSymbols;
        }
        
            
        public static String generateKey() { return generateKey(DEFAULT_SHUFFLE_LIMIT); }
          
        public static String generateKey(final int shuffleLimit) {       
            for (int i = 0; i < RANDOM.nextInt(shuffleLimit); i++) {
                Collections.shuffle(SYMBOLS, new Random(RANDOM.nextInt()));
            }
            
            return SYMBOLS.stream()
                    .map(e -> "" + e)
                    .collect(Collectors.joining(""));
        }
    }
}


var myPwd = Coder.KeyGenerator.generateKey();
var myCoder = new Coder(myPwd);
var phrase = "Действия людей не влияют на воина, потому что у него больше нет никаких ожиданий.";
var encodedPhrase = myCoder.encode(phrase);
var decodedPhrase = myCoder.decode(encodedPhrase);
System.out.println(decodedPhrase);





