package crypto;


import static java.util.Objects.requireNonNull;

import java.util.Base64;

import java.security.Key;
import javax.crypto.*;
import javax.crypto.spec.*;


public class Coder {
    private static final String ALGORITHM = "DESede";
    private static final Cipher CIPHER = wrappingTryCatch(() -> Cipher.getInstance(ALGORITHM));
    private static final int VALID_KEY_LENGTH = 168;
    private static final Base64.Encoder ENCODER = Base64.getEncoder();
    private static final Base64.Decoder DECODER = Base64.getDecoder();

    private final Key key;
    
    
    private record ExtendingWrappedStatementParams(
            BiConsumerWithException<Integer, Key, ? extends Exception> consumer,
            int mode,  
            SupplierWithException<?, ? extends Exception> supplier
    ) {
        public ExtendingWrappedStatementParams {
            requireNonNull(consumer);
            requireNonNull(supplier);
        }
    }
          
          
    public Coder(final String pwd) {    
        checkKeyValidity(pwd);
        this.key = wrappingTryCatch(() -> SecretKeyFactory.getInstance(ALGORITHM)
                .generateSecret(new SecretKeySpec(pwd.getBytes(), ALGORITHM)));          
    }
    
    
    public String encode(final String phrase) {
        return getContentBySelectedMode(
                phrase,
                Cipher.ENCRYPT_MODE,  
                () -> ENCODER.encodeToString(CIPHER.doFinal(phrase.getBytes())));      
    }
    
    
    public String decode(final String encodedPhrase) {
        return getContentBySelectedMode(
                encodedPhrase, 
                Cipher.DECRYPT_MODE, 
                () -> new String(CIPHER.doFinal(DECODER.decode(encodedPhrase))));
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
    
    
    private static <R> R wrappingTryCatch(final SupplierWithException<R, ? extends Exception> supplier) {
        return wrappingTryCatch(supplier, null);
    }
    
    
    private static <R> R wrappingTryCatch(
            final SupplierWithException<R, ? extends Exception> supplier, 
            final RunnableWithException<? extends Exception> runnable
    ) {
        requireNonNull(supplier);  
        
         
        
        //try {
            if (runnable != null) {
                runnable.wrapping().run();
            }
            return supplier.wrapping().get();
       // } catch (Exception e) {
       //{}     throw new RuntimeException(e);
       // }
    }
    
    
    private <R> R extendingWrappedStatement(final ExtendingWrappedStatementParams params) {   
        requireNonNull(params);  
        
        return (R) wrappingTryCatch(
                params.supplier(), 
                () -> params.consumer().accept(params.mode(), key));     
    }
    
    
    private String getContentBySelectedMode(
            final String phrase, 
            final int mode, 
            final SupplierWithException<?, ? extends Exception> supplier
    ) {
        requireNonNull(phrase);
        
        final var args = new ExtendingWrappedStatementParams(
                CIPHER::init, 
                mode, 
                supplier       
        );
        
        return extendingWrappedStatement(args);
    }   
}