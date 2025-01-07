# ******************************************************************************
#                                 CHECKINGS
# ******************************************************************************

declare -F isHideMessage
declare -F isChangeFontColor
declare -F isMatchFilenameWithRegExpr
declare -F isFileRenamed
declare -F isSupportMode
declare -F isArgs
declare -F isValue
declare -F isPrepareChecked
declare -F isFileExist
declare -F isDirectory
declare -F isFileChecked

# ******************************************************************************
#                                 SETTINGS
# ******************************************************************************

declare -F setMsgFontColor
declare -F setValue

# ******************************************************************************
#                                 DEFININGS
# ******************************************************************************

declare -F defPrintMessageAttr          
declare -F defResultDestination         
declare -F defFileCheckedFinalStep      
declare -F defPrepareCheckedFinalStep   
declare -F defRegExpr                   
declare -F defFileDestination   

# ******************************************************************************
#                                 ETC
# ******************************************************************************     

declare -F makeWarning                   
declare -F encodingMetaSymbols             
declare -F cacheRenameState                
declare -F tryRenameFile                  
declare -F parseArgs  

# ******************************************************************************
#                                 PRINTINGS
# ******************************************************************************                     

declare -F printGeneralState         
declare -F printCachedWarnings        
declare -F printCachedRenames         
declare -F printRenameState 

# ******************************************************************************
#                                 SHOWINGS
# ******************************************************************************          

declare -F showCachedRenames             
declare -F showCachedResults             
declare -F showServiceMsg                
declare -F showVerbose    

# ******************************************************************************
#                                 MAININGS
# ******************************************************************************               

declare -F main
declare -F init

# ****************************************************************************** 
# ****************************************************************************** 



