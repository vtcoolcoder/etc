# *****************************************************************************
# defPrintMessageAttr          Определение атрибутов отображения кэш. результ.
# *****************************************************************************
# defResultDestination         Определение перенаправления результ. переимен.
# *****************************************************************************
# defFileCheckedFinalStep      Определение заключительного шага проверки файла
# *****************************************************************************
# defPrepareCheckedFinalStep   Определение заключительного шага предв. проверки
# *****************************************************************************
# defRegExpr                   Определение регулярного выражения для grep
# *****************************************************************************
# defFileDestination           Определение имени файла-назначения
# *****************************************************************************
# defCurrentContainer          Определение ссылки на контейнер — ошибки/успехи
# *****************************************************************************


defPrintMessageAttr()    # 1
{   
    renamingMarker="$1"
          
    case "$renamingMarker" in
    
        "$STATE_RENAME_OK" )
            
            defCurrentContainer
            currentFontColor="$SUCCESS_FONT_COLOR"
        ;;
        
        "$STATE_RENAME_FAIL" )
        
            defCurrentContainer
            currentFontColor="$ERROR_FONT_COLOR"
        ;;
        
    esac
}


defResultDestination()    # 2
{      
    if (( isEnableCaching == "$ON" ))
    
    then
    
        cacheRenameState
        
    else
    
        isInitAsCore && cacheRenameState
        
        printRenameState
        
    fi
}


defFileCheckedFinalStep()    # 3
{    
    case "$executeMode" in
    
        "$CUTSUFFIX" | \
        "$CUTPREFIX" )  
          
            isFileMatchToRegExp || return "$EXIT_FAIL"
        ;;
        
    esac
    
    return "$EXIT_OK"
}


defPrepareCheckedFinalStep()    # 4
{    
    case "$executeMode" in
        
        "$CUTSUFFIX" | \
        "$CUTPREFIX" | \
        "$ADDSUFFIX" | \
        "$ADDPREFIX" )
        
            isSetValue
        ;;
        
    esac
    
    return "$?"
}


defRegExpr()    # 5
{         
    case "$executeMode" in
    
        "$CUTSUFFIX" )  
          
            # Добавление якоря $ в регулярное выражение, 
            # чтобы искать совпадения в конце имени файла
            fmtRegExpr+='$'
        ;;                
        
        "$CUTPREFIX" )    
        
            # Добавление якоря ^ в регулярное выражение, 
            # чтобы искать совпадения в начале имени файла
            fmtRegExpr="^$fmtRegExpr"
        ;;
        
    esac
}


defFileDestination()    # 6
{      
    # Вырезать имя файла, оставив только путь
    pathToSourceFile="${sourceFile%$basenameSourceFile}"
    
    
    case "$executeMode" in
    
        "$CUTSUFFIX" ) # Удалить суффикс из имени файла  
         
            basenameDestinationFile="${basenameSourceFile%$currentAffix}"
        ;;
        
        "$CUTPREFIX" ) # Удалить префикс из имени файла  
        
            basenameDestinationFile="${basenameSourceFile#$currentAffix}"
        ;;
        
        "$ADDSUFFIX" ) # Добавить суффикс к имени файла  
        
            basenameDestinationFile="${basenameSourceFile}$currentAffix"
        ;;
        
        "$ADDPREFIX" ) # Добавить префикс к имени файла   
        
            basenameDestinationFile="${currentAffix}$basenameSourceFile"
        ;;
        
        "$TOUPPER" ) # Нормализовать имя файла до верхнего регистра 
        
            mvToUpperCase="$basenameSourceFile"
            basenameDestinationFile="$mvToUpperCase"
        ;;
        
        "$TOLOWER" ) # Нормализовать имя файла до нижнего регистра
        
            mvToLowerCase="$basenameSourceFile"
            basenameDestinationFile="$mvToLowerCase"
        ;;
        
        "$TOUPFIRST" ) # Сделать первую букву имени файла — прописной
        
            basenameDestinationFile="${basenameSourceFile^}"
        ;;
        
        "$TOLOWFIRST" ) # Сделать первую букву имени файла — строчной
        
            basenameDestinationFile="${basenameSourceFile,}"
        ;;
        
    esac
    
    
    # Полное имя файла-назначения: путь исходного файла + имя производного файла
    destinationFile="${pathToSourceFile}$basenameDestinationFile"
}


defCurrentContainer()    # 7
{
    if [[ "$renamingMarker" == "$STATE_RENAME_OK" ]]
    
    then
        
        declare -g -n refToContainer=okRenames
               
    else
        
        declare -g -n refToContainer=errorRenames       
    
    fi    
}

