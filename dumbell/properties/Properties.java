package dumbell.properties;


import dumbell.configs.PropertiesConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import lombok.Cleanup;


public enum Properties {

    PROPERTIES;
    
    
    @Component
    public record NestedProperties(
            @Value("${BASE_TR}") String baseTr, 
            @Value("${ASYMMETRIC_TR}") String asymmetricTr,
            @Value("${BEGIN_TEMPLATE}") String beginTemplate,
            @Value("${ASYMMETRIC_TEMPLATE}") String asymmetricTemplate,
            @Value("${ENDING_TEMPLATE}") String endingTemplate,
            @Value("${GRIP_WEIGHT}") double gripWeight,
            @Value("${SIDE_AMOUNT}") int sideAmount,
            @Value("${DISK_AMOUNT}") int diskAmount,
            @Value("${MIN_DISK_AMOUNT}") int minDiskAmount,
            @Value("${MAX_DISK_AMOUNT}") int maxDiskAmount,
            @Value("${OTHERS_STRICTLY_EQUAL_SHORT}") String othersStrictlyEqualShort,
            @Value("${OTHERS_STRICTLY_EQUAL_LONG}") String othersStrictlyEqualLong) {
    
    }
    
    
    private NestedProperties properties;
    
    
    Properties() {     
        @Cleanup
        var context = new AnnotationConfigApplicationContext(PropertiesConfig.class);
        properties = context.getBean(NestedProperties.class);
    }
    
    
    public NestedProperties getProperties() { return properties; }
}

