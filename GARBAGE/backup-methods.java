    private void parse() {    
        for (int i = 0; i < clSize; i++) {         
            String currentClArg = commandLineArgs[i];
            
            if (Params.isParamName(currentClArg)) {
                String currentParamName = Params.getParamName(currentClArg);
                
                if (isMatchToCustomParam(currentParamName)) {
                    Set<String> currentParamNameValues = new LinkedHashSet<>();   
                       
                    if (! (i < clSize - 1)) { break; }  
                    nextValue = commandLineArgs[i + 1];       
                    i += fillCurrentParamNameValues(currentParamNameValues, i);
                    result.put(currentParamName, currentParamNameValues);
                    
                } else {
                    throw new MismatchParamException();
                }
            }
        }  
    }
    
    
    private boolean isMatchToCustomParam(String param) {
        return customParams.contains(param);
    }
    
    
    //if (! (counter < clSize - 1)) { return; }  
    
    
    