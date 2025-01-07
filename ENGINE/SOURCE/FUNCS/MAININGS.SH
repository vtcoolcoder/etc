# ******************************************************************************
# main                        Главная функция
# ******************************************************************************
# init                        Инициализация
# ******************************************************************************
# initParams                  Инициализация базовых параметров
# ******************************************************************************
# resetParams                 Сброс значений базовых параметров
# ******************************************************************************
# updateArgv                  Обновление значений аргументов главной функции
# ******************************************************************************


main()    # 1
{   
    showTracingMessage "main()"
    
    if isPreparelyChecked 
    
    then
    
        parseArgs "${ARGV[@]}"
        local parseStatus="$?"
    
        showVerbose
                       
        return "$parseStatus"
    
    else
    
        showVerbose 
        return "$EXIT_FAIL"
        
    fi
}


init()    # 2
{   
    showParentName
    
    if isInitAsCore
    
    then
    
        while showMenu
    
        do
        
            main "${ARGV[@]}"
        
            updateArgv
        
            resetParams
        
        done
        
    else
        
        main "${ARGV[@]}" || exit "$EXIT_FAIL"
        exit "$EXIT_OK"
        
    fi
}


resetParams()    # 4
{
    showTracingMessage "resetParams()"
    
    unset okRenames
    declare -g -A okRenames=()
    
    unset errorRenames
    declare -g -A errorRenames=() 
    
    unset warningMessages
    declare -g -a warningMessages=() 
    
    
    countRenamedFiles=0
    
    currentFontColor=""
    currentAffix=""
    sourceFile=""
    basenameSourceFile=""
    destinationFile=""
    renamingMarker=""
    fmtRegExpr=""
    renameStatus=""
    executeMode=""
}


updateArgv()    # 5
{
    showTracingMessage "updateArgv()"
    
    if (( "${#okRenames[@]}" != 0 || "${#errorRenames[@]}" != 0 ))
    
    then
    
        unset ARGV
        declare -g -a ARGV=()
        
        (( "${#okRenames[@]}" != 0 )) && ARGV=("${okRenames[@]}")
        (( "${#errorRenames[@]}" != 0 )) && ARGV+=("${!errorRenames[@]}")
        
    fi
}



