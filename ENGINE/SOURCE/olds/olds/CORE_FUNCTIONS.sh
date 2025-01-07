# *****************************************************************************
# *****************************************************************************
#                                 ФУНКЦИИ
# *****************************************************************************
# *****************************************************************************


# *****************************************************************************
#                                 ПРОТОТИПЫ
# *****************************************************************************

# *****************************************************************************
#-6 printGeneralState             Отображение итоговой статистики
# *****************************************************************************
#-5 showCachedResults             Отображение кэшированных результатов
# *****************************************************************************
#-4 isHideMessage                 Скрыть служебное сообщение состояния?
# *****************************************************************************
#-3 setMsgFontColor               Устанавливает цвет шрифта
# *****************************************************************************
#-2 isChangeFontColor             Менять ли цвет шрифта?
# *****************************************************************************
#-1 showServiceMsg                Отображает служебные сообщения
# *****************************************************************************
#0  isArgs                        Заданы ли параметры сценария?
# *****************************************************************************
#1  isSupportMode                 Задан ли поддерживаемый режим работы?
# *****************************************************************************
#2  setValue                      Задаёт изменяющее значение
# *****************************************************************************
#3  isValue                       Задано ли изменяющее значение?
# *****************************************************************************
#4  defPrepareCheckedFinalStep    Если режим работы "TOLOWER" | "TOUPPER", 
#                                 игнорирует задание изменяющего значения     
# *****************************************************************************
#5  isPrepareChecked              Начальные проверки пройдены?
# *****************************************************************************
#6  isFileExist                   Файл существует?
# *****************************************************************************
#7  isDirectory                   Файл — директория?
# *****************************************************************************
#8  encodingMetaSymbols           Перекодирует метасимволы удаляемого аффикса
#                                 для регулярного выражения
#
#                                 Необходимо дорабатывать, поскольку багует на 
#                                 некоторых символах
# *****************************************************************************
#9  defRegExpr                    Определяет регулярное выражение для grep
# *****************************************************************************
#10 isMatchFilenameWithRegExpr    Содержится ли удаляемый аффикс в имени файла?
# *****************************************************************************
#11 defFileCheckedFinalStep               Продолжать проверку?
# *****************************************************************************
#12 isFileChecked                 Файл проверен?
# *****************************************************************************
#13 defFileDestination                Определяет имя файла-назначения
# *****************************************************************************
#14 isFileRenamed                 Переименование успешно?
# *****************************************************************************
#15 printRenameState              Отображает сообщение о состоянии переимен.                          
# *****************************************************************************
#16 cacheRenameState              Кэширует результат переименования                               
# *****************************************************************************
#17 defResultDestination                     Получает итоговый результат:
#                                 кэширование | отображение сообщ. состояния
# *****************************************************************************
#18 tryRenameFile                 Пытается переименовать файл
# *****************************************************************************
#19 showVerbose                   Отображает доп. сведения о состоянии
# *****************************************************************************
#20 parseArgs                     Обрабатывает параметры сценария
# *****************************************************************************
#21 main                          Главная функция сценария
# *****************************************************************************
#22 init                          Инициализация              
# *****************************************************************************


# *****************************************************************************
#                                 ОПРЕДЕЛЕНИЯ
# *****************************************************************************


printGeneralState()    #-9
{
    local msg="$(printf "$PFORMAT_GENERAL_STATE" \
                         \
                          "$countRenamedFiles" "$TOTAL_FILES")"
                      
    showServiceMsg "$DEFAULT_FONT_COLOR" "$msg"
}


printCachedWarnings()    #-8
{
    for msg in "${warningMessages[@]}"; do
    
        showServiceMsg "$WARNING_FONT_COLOR" "$msg"
        
    done
}


defPrintMessageAttr()    #-7
{
    local modeFlag="$1"
    renamingMarker="$modeFlag"
    
    declare -A currentContainer=()
    currentFontColor=""
       
    case "$modeFlag" in
    
        "$STATE_RENAME_OK" )
            currentContainer=$okRenames
            currentFontColor="$SUCCESS_FONT_COLOR"
        ;;
        
        "$STATE_RENAME_FAIL" )
            currentContainer=$errorRenames
            currentFontColor="$ERROR_FONT_COLOR"
        ;;
        
    esac
}


printCachedRenames()    #-6
{
    for k in "${!currentContainer[@]}"; do
     
        local msg="$(printf "$PFORMAT_RENAMING" \
                         \
                          "$k" "${currentContainer["$k"]}" "$renamingMarker")"
                          
        showServiceMsg "$currentFontColor" "$msg"
                         
    done
}


showCachedResults()    #-5
{
    printCachedWarnings
    
    defPrintMessageAttr "$STATE_RENAME_FAIL"
    printCachedRenames
    
    defPrintMessageAttr "$STATE_RENAME_OK"
    printCachedRenames
}


isHideMessage()    #-4
{
# Определение условия скрытия сообщения:
#     если переименование успешно 
#     и выключен флаг отображения доп. сообщений о состоянии
    
    if (( isEnableVerbose == "$OFF" )); then
    
        if [[ "$currentFontColor" == "$SUCCESS_FONT_COLOR" ]]; then
        
            return "$EXIT_OK"
        fi
        
    fi
    
    return "$EXIT_FAIL"
}


setMsgFontColor()    #-3
{
#   +--------------------+--------------------+
#   | DEFAULT_FONT_COLOR | SUCCESS_FONT_COLOR |
#   +--------------------+--------------------+   
#   | WARNING_FONT_COLOR | ERROR_FONT_COLOR   |
#   +--------------------+--------------------+

    local tempFontColor="$1"
    
    echo -n -e "$tempFontColor" >&2
}


isChangeFontColor()    #-2
{
# Определение условия смены цвета шрифта:
#     если включен флаг цветного отображения сообщений
#     и заданный цвет шрифта не дефолтный   
    
    if (( isEnableColors == "$ON" )); then
    
        if [[ "$currentFontColor" != "$DEFAULT_FONT_COLOR" ]]; then
        
            return "$EXIT_OK"
            
        fi
        
    fi
    
    return "$EXIT_FAIL"
}


showServiceMsg()    #-1
{     
    # Заданный цвет шрифта
    currentFontColor="$1"
    
    # Заданное сообщение
    currentServiceMessage="$2"
    
    isHideMessage && return
    
    isChangeFontColor && setMsgFontColor "$currentFontColor"
    
    # Выводимое сообщение
    echo "$currentServiceMessage" >&2
    
    isChangeFontColor && setMsgFontColor "$DEFAULT_FONT_COLOR"
}


makeWarning()
{
# Если включен флаг кэширования, 
# кэшировать сообщения в индекс. массив — $warningMessages,
# иначе передать управление showServiceMsg()

    local tmpMessage="$1"

    if isEnableCaching; then

        warningMessages+="$tmpMessage"
    
    else

        showServiceMsg "$WARNING_FONT_COLOR" "$tmpMessage"
    
    fi
}


isArgs()    #0
{
    [[ "$TOTAL_FILES" != 0 ]] && return "$EXIT_OK"
    
    makeWarning "$NOSOURCE"
    
    return "$EXIT_FAIL"
}


isSupportMode()    #1
{
    # Попробовать создать константный массив из констант режимов работы
    # и в цикле for перебирать все значения массива для сравнения
    
    case "$executeMode" in
      
        "$CUTSUFFIX" | "$CUTPREFIX" | "$ADDSUFFIX" | "$ADDPREFIX" | \
           \
            "$TOLOWER" | "$TOUPPER" | "$TOLOWFIRST" | "$TOUPFIRST" )
            
                return "$EXIT_OK"
        ;;
        
        * )
            # Прочие режимы работы (в разработке)
            
            makeWarning "$NOMODE"
            
            return "$EXIT_FAIL"
        ;;
        
    esac
}


setValue()    #2
{
    # Сделать первую букву строки прописной
    local promtString="${executeMode^}"
    
    # Определить итоговую строку приглашения к вводу значения аффикса
    promtString+=': '
    
    read -e -i "$defaultAffixValue" -p "$promtString" changeAffix
}


isValue()    #3
{
    setValue

    [[ -n "$changeAffix" ]] && return "$EXIT_OK"
    
    makeWarning "$UNSET $executeMode!"
    
    return "$EXIT_FAIL"
}


defPrepareCheckedFinalStep()    #4
{ 
    case "$executeMode" in
        
        "$CUTSUFFIX" | "$CUTPREFIX" | "$ADDSUFFIX" | "$ADDPREFIX" )
            isValue
        ;;
        
    esac
    
    return "$?"
}


isPrepareChecked()    #5
{   
    isSupportMode && isArgs $ARGV && defPrepareCheckedFinalStep
    
    return "$?"
}


isFileExist()    #6
{   
    [[ -e "$sourceFile" ]] && return "$EXIT_OK"
    
    local msg="$(printf "$PFORMAT_NOFILE" "$sourceFile")"
    
    makeWarning "$msg"
                                            
    return "$EXIT_FAIL"
}


isDirectory()    #7
{   
    [[ -d "$sourceFile" ]] || return "$EXIT_OK"
    
    local msg="$(printf "$PFORMAT_ISDIR" "$sourceFile")"
    
    makeWarning "$msg"
        
    return "$EXIT_FAIL"
}


encodingMetaSymbols()    #8
{   
    # Необходимо расширить множество кодируемых метасимволов

    fmtRegExpr="${changeAffix//./\\.}"    # '.' -> '\.'
    fmtRegExpr="${fmtRegExpr//\*/.*}"     # '*' -> '.*'
    fmtRegExpr="${fmtRegExpr//\?/.}"      # '?' -> '.'            
}


defRegExpr()    #9
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


isMatchFilenameWithRegExpr()    #10
{   
    encodingMetaSymbols
    defRegExpr
    
    grep "$fmtRegExpr" <<< "$basenameSourceFile" &> /dev/null
    return "$?"
}


defFileCheckedFinalStep()    #11
{
    case "$executeMode" in
    
        "$CUTSUFFIX" | "$CUTPREFIX" )    
            isMatchFilenameWithRegExpr || return "$EXIT_FAIL"
        ;;
        
    esac
    
    return "$EXIT_OK"
}


isFileChecked()    #12
{     
    isFileExist && isDirectory && defFileCheckedFinalStep
    
    return "$?"
}


defFileDestination()    #13
{   
    # Лучше для каждого режима написать отдельную функцию
    
    # Вырезать путь к файлу, оставив только его имя
    basenameSourceFile="${sourceFile##*/}"
    
    # Вырезать имя файла, оставив только путь
    pathToSourceFile="${sourceFile%$basenameSourceFile}"
    
    
    case "$executeMode" in
    
        "$CUTSUFFIX" ) # Удалить суффикс из имени файла   
            basenameDestinationFile="${basenameSourceFile%$changeAffix}"
        ;;
        
        "$CUTPREFIX" ) # Удалить префикс из имени файла  
            basenameDestinationFile="${basenameSourceFile#$changeAffix}"
        ;;
        
        "$ADDSUFFIX" ) # Добавить суффикс к имени файла  
            basenameDestinationFile="${basenameSourceFile}$changeAffix"
        ;;
        
        "$ADDPREFIX" ) # Добавить префикс к имени файла   
            basenameDestinationFile="${changeAffix}$basenameSourceFile"
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
    
    
    # Имя файла-назначения: путь исходного файла + имя производного файла
    destinationFile="${pathToSourceFile}$basenameDestinationFile"
}


isFileRenamed()    #14
{   
    defFileDestination
    
    mv "$sourceFile" "$destinationFile" &> /dev/null && ((++countRenamedFiles))
        
    return "$?"
}


printRenameState()    #15
{    
    if (( renameStatus == "$EXIT_OK" )); then 
    
        local stateRename="$STATE_RENAME_OK"
        local fontColor="$SUCCESS_FONT_COLOR"
        
    else
    
        local stateRename="$STATE_RENAME_FAIL" 
        local fontColor="$ERROR_FONT_COLOR"
        
    fi
    
    
    local msg="$(printf "$PFORMAT_RENAMING" \
                     \
                      "$sourceFile" "$destinationFile" "$stateRename")"
    
    showServiceMsg "$fontColor" "$msg"
}


cacheRenameState()    #16
{   
    if (( renameStatus == "$EXIT_OK" )); then
    
        okRenames["$sourceFile"]="$destinationFile"
        
    else
    
        errorRenames["$sourceFile"]="$destinationFile"
        
    fi
}


defResultDestination()    #17
{  
    if (( isEnableCaching == "$ON" )); then
    
        cacheRenameState
        
    else
    
        printRenameState
        
    fi
}


tryRenameFile()    #18
{ 
    isFileRenamed
    renameStatus="$?"
   
    defResultDestination
}


showVerbose()    #19
{  
    (( isEnableCaching == "$ON" )) && showCachedResults
    
    printGeneralState
}


parseArgs()    #20
{   
    for sourceFile; do isFileChecked && tryRenameFile; done
    
    # Не удалось переименовать ни одного файла
    (( countRenamedFiles == 0 )) && return "$EXIT_FAIL"
    
    return "$EXIT_OK"   
}


main()    #21
{   
    isPrepareChecked || return "$EXIT_FAIL"
    
    parseArgs $ARGV
    local parseStatus="$?"
    
    (( isEnableVerbose == "$ON" )) && showVerbose
                       
    return "$parseStatus"
}


init()    #22
{
    main $ARGV || exit "$EXIT_FAIL"
    exit "$EXIT_OK"
}

                                                                                
# *****************************************************************************
# *****************************************************************************
