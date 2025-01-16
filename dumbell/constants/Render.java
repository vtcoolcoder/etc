package dumbell.constants;


import static dumbell.properties.Properties.PROPERTIES;


public interface Render {
    
    String SPACES = " ".repeat(23);
    String BASE_TR = PROPERTIES.getProperties().baseTr(); 
    String ASYMMETRIC_TR = PROPERTIES.getProperties().asymmetricTr();  
    String BEGIN_TEMPLATE = PROPERTIES.getProperties().beginTemplate(); 
    String ASYMMETRIC_TEMPLATE = PROPERTIES.getProperties().asymmetricTemplate();      
    String ENDING_TEMPLATE = PROPERTIES.getProperties().endingTemplate(); 
}