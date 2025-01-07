package dumbell.constants;


import static dumbell.properties.Properties.getProperties;


public interface Render {
    
    String SPACES = " ".repeat(23);
    String BASE_TR = getProperties().baseTr(); 
    String ASYMMETRIC_TR = getProperties().asymmetricTr();  
    String BEGIN_TEMPLATE = getProperties().beginTemplate(); 
    String ASYMMETRIC_TEMPLATE = getProperties().asymmetricTemplate();      
    String ENDING_TEMPLATE = getProperties().endingTemplate(); 
}