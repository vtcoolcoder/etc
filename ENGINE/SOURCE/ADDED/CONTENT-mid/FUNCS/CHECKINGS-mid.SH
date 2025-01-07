# *****************************************************************************
# isHideMessage                 Скрыть служебное сообщение состояния?
# *****************************************************************************
# isChangeFontColor             Менять ли цвет шрифта?
# *****************************************************************************
# isFileMatchToRegExp           Содержится ли удаляемый аффикс в имени файла?
# *****************************************************************************
# isFileRenamed                 Переименование успешно?
# *****************************************************************************
# isSetSupportMode              Задан ли поддерживаемый режим работы?
# *****************************************************************************
# isSetArgs                     Заданы ли параметры сценария?
# *****************************************************************************
# isSetValue                    Задано ли изменяющее значение?
# *****************************************************************************
# isPreparelyChecked            Начальные проверки пройдены?
# *****************************************************************************
# isFileExist                   Файл существует?
# *****************************************************************************
# isFileDirectory               Файл — директория?
# *****************************************************************************
# isFileChecked                 Файл проверен?
# *****************************************************************************
# isInitAsCore                  Запущен непосредственно — движок?
# *****************************************************************************


isHideMessage()    # 1
{  
    # Определение условия скрытия сообщения:
    #     если переименование успешно 
    #     и выключен флаг отображения доп. сообщений о состоянии
    
    if (( isEnableVerbose == "$OFF" && renameStatus == "$EXIT_OK" )) 
    
    then
    
        return "$EXIT_OK"
        
    fi
    
    return "$EXIT_FAIL"
}


isChangeFontColor()    # 2
{   
    # Определение условия смены цвета шрифта:
    #     если включен флаг цветного отображения сообщений
    #     и заданный цвет шрифта не дефолтный   
    
    if (( isEnableColors == "$ON" ))
    
    then
    
        if [[ "$currentFontColor" != "$DEFAULT_FONT_COLOR" ]]
        
        then
        
            return "$EXIT_OK"
            
        fi
        
    fi
    
    return "$EXIT_FAIL"
}


isFileMatchToRegExp()    # 3
{   
    encodingMetaSymbols
    defRegExpr
                                      
    grep "$fmtRegExpr" <<< "$basenameSourceFile" &> /dev/null    
    return "$?"
}


isFileRenamed()    # 4
{   
    defFileDestination
              
    mv "$sourceFile" "$destinationFile" &> /dev/null && ((++countRenamedFiles))
        
    return "$?"
}


isSetSupportMode()    # 5
{  
    case "$executeMode" in
      
        "$CUTSUFFIX"  | \
        "$CUTPREFIX"  | \
        "$ADDSUFFIX"  | \
        "$ADDPREFIX"  | \
        "$TOLOWER"    | \
        "$TOUPPER"    | \
        "$TOLOWFIRST" | \
        "$TOUPFIRST"  )
            
                return "$EXIT_OK"
        ;;
        
        * )
            # Прочие режимы работы (в разработке)
            
            makeWarning "$NOMODE"
            showMenu
            return "$?"
            
            #return "$EXIT_FAIL"
        ;;
        
    esac
}


isSetArgs()    # 6
{    
    [[ "$TOTAL_FILES" != 0 ]] && return "$EXIT_OK"
    
    makeWarning "$NOSOURCE"
    
    return "$EXIT_FAIL"
}


isSetValue()    # 7
{        
    setValue     # возможно, уже не нужна здесь
      
    case "$executeMode" in
    
        "$ADDSUFFIX"  )
        
            setValidValue "$REGEXPR_ADDED_SUFFIX" "$SUFX_NO_MATCH_TO_REGEXP" ""
             
            return "$EXIT_OK"
        
        ;;
        
        "$ADDPREFIX"  )
            
            setValidValue "$REGEXPR_ADDED_PREFIX" "$PRFX_NO_MATCH_TO_REGEXP" ""
             
            return "$EXIT_OK"
        
        ;;
        
        * )
            setValidValue "" "$UNSET $executeMode!" "NOADDING"
         
            return "$EXIT_OK"
             
    esac   
}


isPreparelyChecked()    # 8
{       
    if ! isSetSupportMode
    
    then
    
        showMenu
        
    fi && isSetArgs && defPrepareCheckedFinalStep
    
    return "$?"
}


isFileExist()    # 9
{   
    [[ -e "$sourceFile" ]] && return "$EXIT_OK"
    
    makeWarning "$(printf "$PFORMAT_NOFILE" "$sourceFile")"
                                            
    return "$EXIT_FAIL"
}


isFileDirectory()    # 10
{   
    [[ -d "$sourceFile" ]] || return "$EXIT_OK"
    
    makeWarning "$(printf "$PFORMAT_ISDIR" "$sourceFile")"
        
    return "$EXIT_FAIL"
}


isFileChecked()    # 11
{   
    isFileExist && isFileDirectory && defFileCheckedFinalStep
    
    return "$?"
}


isInitAsCore()    # 12
{
    [[ "${0##*/}" == "core" ]]
    return "$?"
}

