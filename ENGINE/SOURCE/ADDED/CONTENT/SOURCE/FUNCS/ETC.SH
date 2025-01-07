# *****************************************************************************
# makeWarning                     Обрабатывает предупреждающие сообщения
# *****************************************************************************
# encodingMetaSymbols             Перекодирует метасимволы удаляемого аффикса
#                                 для регулярного выражения
#
#                                 Необходимо дорабатывать, поскольку багует на 
#                                 некоторых символах
# *****************************************************************************
# cacheRenameState                Кэширует результат переименования
# *****************************************************************************
# tryRenameFile                   Пытается переименовать файл
# *****************************************************************************
# parseArgs                       Обрабатывает параметры сценария
# *****************************************************************************

makeWarning()    # 1
{   
    # Если включен флаг кэширования, 
    # кэшировать предупреждающие сообщения в индекс. массив,
    # иначе отобразить текущее предупреждающее сообщение 

    local tmpMessage="$1"
    
    showTracingMessage "makeWarning( $tmpMessage )"

    if (( isEnableCaching == "$ON" ))
    
    then

        warningMessages+=("$tmpMessage")
    
    else

        showServiceMsg "$WARNING_FONT_COLOR" "$tmpMessage"
    
    fi
}

encodingMetaSymbols()    # 2
{   
    showTracingMessage "encodingMetaSymbols()"
    
    # Необходимо расширить множество кодируемых метасимволов

    fmtRegExpr="${currentAffix//./\.}"   # '.' -> '\.'
    fmtRegExpr="${fmtRegExpr//\*/.*}"     # '*' -> '.*'
    fmtRegExpr="${fmtRegExpr//\?/.}"      # '?' -> '.'            
}

cacheRenameState()    # 3
{   
    showTracingMessage "cacheRenameState( $renameStatus )"
    
    if (( renameStatus == "$EXIT_OK" ))
    
    then
    
        okRenames["$sourceFile"]="$destinationFile"
        
    else
    
        errorRenames["$sourceFile"]="$destinationFile"
            
    fi
}

tryRenameFile()    # 4
{ 
    showTracingMessage "tryRenameFile( $sourceFile )"
    
    isFileRenamed
    renameStatus="$?"
   
    defResultDestination
}

parseArgs()    # 5
{   
    showTracingMessage "parseArgs( ${ARGV[@]} )"

    for sourceFile
    
    do 
    
        # Вырезать путь к файлу, оставив только его имя
        basenameSourceFile="${sourceFile##*/}"
    
        isFileChecked && tryRenameFile; 
        
    done
    
    # Не удалось переименовать ни одного файла
    (( countRenamedFiles == 0 )) && return "$EXIT_FAIL"
    
    return "$EXIT_OK"   
}
