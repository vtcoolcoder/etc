# ***************************************************************************
# ***************************************************************************
#                         ГЛОБАЛЬНЫЕ КОНСТАНТЫ
# ***************************************************************************
# ***************************************************************************


# ***************************************************************************
#                             ЦВЕТА ШРИФТА
# ***************************************************************************

DEFAULT_FONT_COLOR="\033[1;37m"    # белый
SUCCESS_FONT_COLOR="\033[1;32m"    # зелёный
WARNING_FONT_COLOR="\033[1;33m"    # жёлтый
ERROR_FONT_COLOR="\033[1;31m"      # красный

# ***************************************************************************
#                             СОСТОЯНИЯ ФЛАГОВ
# ***************************************************************************

declare -i -r ON=1     # включен
declare -i -r OFF=0    # отключен

# ***************************************************************************
#                             КОДЫ ЗАВЕРШЕНИЯ
# ***************************************************************************

declare -i -r EXIT_OK=0      # успех
declare -i -r EXIT_FAIL=1    # неудача

# ***************************************************************************
#                             СЛУЖЕБНЫЕ
# ***************************************************************************

declare -r UNSET="Не задан"
declare -r NOSOURCE="$UNSET источник!"
declare -r NOMODE="$UNSET поддерживаемый режим работы!"
declare -r QUANTITY_RENAMED_FILES="Количество переименованных файлов:"

# ***************************************************************************

# количество аргументов
declare -i -r TOTAL_FILES=$#

# массив аргументов
declare -r ARGV="$@"

# ***************************************************************************
#                             СОСТОЯНИЯ ПЕРЕИМЕНОВАНИЯ
# ***************************************************************************

declare -r STATE_RENAME_OK="OK"
declare -r STATE_RENAME_FAIL="ОШИБКА"

# ***************************************************************************
#                             ФОРМАТЫ ВЫВОДА
# ***************************************************************************

declare -r PFORMAT_RENAMING="Переименование: '%s' -> '%s'\t%s"
declare -r PFORMAT_ISDIR="'%s' — директория!"
declare -r PFORMAT_NOFILE="Файл: '%s' не существует!"
declare -r PFORMAT_GENERAL_STATE="$QUANTITY_RENAMED_FILES %s из %s"

# ***************************************************************************
#                             РЕЖИМЫ РАБОТЫ                                
# ***************************************************************************
#                 ( ОПРЕДЕЛЯЮТ МЕТОД ПЕРЕИМЕНОВАНИЯ ФАЙЛА )
# ***************************************************************************

declare -r ADDED="добавляемый"
declare -r CUTTED="вырезаемый"
declare -r SUFFIX="суффикс"
declare -r PREFIX="префикс"

# ***************************************************************************

declare -r RNM="RENAMETO"
declare -r CAS="CASE"
declare -r FLT="FIRSTLETTER"

# ***************************************************************************

# вырезать суффикс 
declare -r CUTSUFFIX="$CUTTED $SUFFIX"

# вырезать префикс
declare -r CUTPREFIX="$CUTTED $PREFIX"

# добавить суффикс
declare -r ADDSUFFIX="$ADDED $SUFFIX"

# добавить префикс
declare -r ADDPREFIX="$ADDED $PREFIX"

# ***************************************************************************

# нормализовать буквы в строчные
declare -r TOLOWER="${RNM}LOWER$CAS"

# нормализовать буквы в прописные
declare -r TOUPPER="${RNM}UPPER$CAS"

# сделать первую букву строчной
declare -r TOLOWFIRST="${TOLOWER}$FLT"

# сделать первую букву прописной
declare -r TOUPFIRST="${TOUPPER}$FLT"


# ***************************************************************************
# ***************************************************************************



# ***************************************************************************
# ***************************************************************************
#                         ГЛОБАЛЬНЫЕ ПЕРЕМЕННЫЕ
# ***************************************************************************
# ***************************************************************************


# ***************************************************************************
#                             КОНТЕЙНЕРЫ СОСТОЯНИЙ
# ***************************************************************************

# переименования (ассоциативные массивы),
# где ключ — "имя файла-источника", значение — "имя файла-назначения"

declare -A okRenames=()            # успехи
declare -A errorRenames=()         # ошибки

# ***************************************************************************

# предупреждающих сообщений (индекс. массив)

declare -a warningMessages=()

# ***************************************************************************
#                              БУФЕРЫ НОРМАЛИЗАЦИИ
#                              ВСЕХ БУКВ ИМЕНИ ФАЙЛА
# ***************************************************************************

declare -l mvToLowerCase=""    # в строчные
declare -u mvToUpperCase=""    # в прописные

# ***************************************************************************
#                              ТЕКУЩИЙ РЕЖИМ РАБОТЫ
# ***************************************************************************

#               +-----------+-----------+-----------+------------+
#               | CUTSUFFIX | CUTPREFIX | ADDSUFFIX | ADDPREFIX  |
#               +-----------+-----------+-----------+------------+
#               | TOUPPER   | TOLOWER   | TOUPFIRST | TOLOWFIRST |
#               +-----------+-----------+-----------+------------+

executeMode=""

# ***************************************************************************
#                              ЗНАЧЕНИЯ ПО УМОЛЧАНИЮ
# ***************************************************************************

# изменяющего аффикса
defaultAffixValue=""

# ***************************************************************************

# счётчика успешно переименованных файлов
declare -i countRenamedFiles=0

# флага вкл/откл отображения доп. сообщений о состоянии
declare -i isEnableVerbose="$OFF"

# флага вкл/откл цветной подсветки сообщений
declare -i isEnableColors="$OFF"

# флага вкл/откл кэширования результатов
declare -i isEnableCaching="$OFF"


# ***************************************************************************
# ***************************************************************************



# Движок для сценариев: 
# "addprefix", "addsuffix", "cutprefix", "cutsuffix", "upper", "lower",
# "upfirst", "lowfirst"

# Если данный сценарий вызывается сам по себе, выводить меню с приглашением
# к вводу для выбора режима работы.

# Можно попробовать сделать так, чтобы можно было использовать данный сценарий
# в конвейерах.

# Проверять корректность ввода регулярному выражению.

# Добавить цветную подсветку сообщений о состоянии    (OK)
# И возможность её вкл/откл                           (OK)

# Добавить режимы работы:

#     переименование:
#         всех букв в строчные/прописные          ааа/ААА     (OK)
#         первой буквы в строчную/прописную       аАА/Ааа     (OK)
#         отдельных буквы по шаблону              аАа/АаА


# Помимо переименования добавить команды:
# ln -s (создание симлинок)

# Доработать функцию printCachedStates()
# Если вкючен флаг кэширования, не запускать функцию

# ******************************************************************************
# ******************************************************************************

#IMPORT_COMMON_PATH="$HOME/bin/ENGINE/SOURCE/"
#IMPORT_ATTRS_PATH="${IMPORT_COMMON_PATH}ATTRIBUTES/"
#IMPORT_FUNCS_PATH="${IMPORT_COMMON_PATH}FUNCS/"

#COMM_SUFFIX=".SH"
#FUNC_SUFFIX="INGS$COMM_SUFFIX"

# ******************************************************************************

tryImportFile()
{
    local importFile="$1"
    
    if [[ -e "$importFile" ]]; then

        source "$importFile"
    
    else

        echo "Импортируемый файл '$importFile' не найден!" >&2
        exit 1
    
    fi
}

# ******************************************************************************

#tryImportFile "${IMPORT_ATTRS_PATH}CONSTS$COMM_SUFFIX"
#tryImportFile "${IMPORT_ATTRS_PATH}VARS$COMM_SUFFIX"


#tryImportFile "${IMPORT_FUNCS_PATH}PROTOTYPES$COMM_SUFFIX"

#tryImportFile "${IMPORT_FUNCS_PATH}ETC$COMM_SUFFIX"
#tryImportFile "${IMPORT_FUNCS_PATH}MAIN$FUNC_SUFFIX"
#tryImportFile "${IMPORT_FUNCS_PATH}CHECK$FUNC_SUFFIX"
#tryImportFile "${IMPORT_FUNCS_PATH}SETT$FUNC_SUFFIX"
#tryImportFile "${IMPORT_FUNCS_PATH}DEF$FUNC_SUFFIX"
#tryImportFile "${IMPORT_FUNCS_PATH}PRINT$FUNC_SUFFIX"
#tryImportFile "${IMPORT_FUNCS_PATH}SHOW$FUNC_SUFFIX"

# ******************************************************************************
# ******************************************************************************



# *****************************************************************************
# isHideMessage                 Скрыть служебное сообщение состояния?
# *****************************************************************************
# isChangeFontColor             Менять ли цвет шрифта?
# *****************************************************************************
# isMatchFilenameWithRegExpr    Содержится ли удаляемый аффикс в имени файла?
# *****************************************************************************
# isFileRenamed                 Переименование успешно?
# *****************************************************************************
# isSupportMode                 Задан ли поддерживаемый режим работы?
# *****************************************************************************
# isArgs                        Заданы ли параметры сценария?
# *****************************************************************************
# isValue                       Задано ли изменяющее значение?
# *****************************************************************************
# isPrepareChecked              Начальные проверки пройдены?
# *****************************************************************************
# isFileExist                   Файл существует?
# *****************************************************************************
# isDirectory                   Файл — директория?
# *****************************************************************************
# isFileChecked                 Файл проверен?
# *****************************************************************************


isHideMessage()    # 1
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


isChangeFontColor()    # 2
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


isMatchFilenameWithRegExpr()    # 3
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


isSupportMode()    # 5
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


isArgs()    # 6
{
    [[ "$TOTAL_FILES" != 0 ]] && return "$EXIT_OK"
    
    makeWarning "$NOSOURCE"
    
    return "$EXIT_FAIL"
}


isValue()    # 7
{
    setValue

    [[ -n "$changeAffix" ]] && return "$EXIT_OK"
    
    makeWarning "$UNSET $executeMode!"
    
    return "$EXIT_FAIL"
}


isPrepareChecked()    # 8
{   
    isSupportMode && isArgs $ARGV && defPrepareCheckedFinalStep
    
    return "$?"
}


isFileExist()    # 9
{   
    [[ -e "$sourceFile" ]] && return "$EXIT_OK"
    
    local msg="$(printf "$PFORMAT_NOFILE" "$sourceFile")"
    
    makeWarning "$msg"
                                            
    return "$EXIT_FAIL"
}


isDirectory()    # 10
{   
    [[ -d "$sourceFile" ]] || return "$EXIT_OK"
    
    local msg="$(printf "$PFORMAT_ISDIR" "$sourceFile")"
    
    makeWarning "$msg"
        
    return "$EXIT_FAIL"
}


isFileChecked()    # 11
{     
    isFileExist && isDirectory && defFileCheckedFinalStep
    
    return "$?"
}



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


defPrintMessageAttr()    # 1
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


defResultDestination()    # 2
{  
    if (( isEnableCaching == "$ON" )); then
    
        cacheRenameState
        
    else
    
        printRenameState
        
    fi
}


defFileCheckedFinalStep()    # 3
{
    case "$executeMode" in
    
        "$CUTSUFFIX" | "$CUTPREFIX" )    
            isMatchFilenameWithRegExpr || return "$EXIT_FAIL"
        ;;
        
    esac
    
    return "$EXIT_OK"
}


defPrepareCheckedFinalStep()    # 4
{ 
    case "$executeMode" in
        
        "$CUTSUFFIX" | "$CUTPREFIX" | "$ADDSUFFIX" | "$ADDPREFIX" )
            isValue
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



# ******************************************************************************
# makeWarning                     Обрабатывает предупреждающие сообщения
# ******************************************************************************
# encodingMetaSymbols             Перекодирует метасимволы удаляемого аффикса
#                                 для регулярного выражения
#
#                                 Необходимо дорабатывать, поскольку багует на 
#                                 некоторых символах
# ******************************************************************************
# cacheRenameState                Кэширует результат переименования
# ******************************************************************************
# tryRenameFile                   Пытается переименовать файл
# ******************************************************************************
# parseArgs                       Обрабатывает параметры сценария
# ******************************************************************************


makeWarning()    # 1
{
# Если включен флаг кэширования, 
# кэшировать предупреждающие сообщения в индекс. массив,
# иначе отобразить текущее предупреждающее сообщение 

    local tmpMessage="$1"

    if (( isEnableCaching == "$ON" )); then

        warningMessages+="$tmpMessage"
    
    else

        showServiceMsg "$WARNING_FONT_COLOR" "$tmpMessage"
    
    fi
}


encodingMetaSymbols()    # 2
{   
    # Необходимо расширить множество кодируемых метасимволов

    fmtRegExpr="${changeAffix//./\\.}"    # '.' -> '\.'
    fmtRegExpr="${fmtRegExpr//\*/.*}"     # '*' -> '.*'
    fmtRegExpr="${fmtRegExpr//\?/.}"      # '?' -> '.'            
}


cacheRenameState()    # 3
{   
    if (( renameStatus == "$EXIT_OK" )); then
    
        okRenames["$sourceFile"]="$destinationFile"
        
    else
    
        errorRenames["$sourceFile"]="$destinationFile"
        
    fi
}


tryRenameFile()    # 4
{ 
    isFileRenamed
    renameStatus="$?"
   
    defResultDestination
}


parseArgs()    # 5
{   
    for sourceFile; do isFileChecked && tryRenameFile; done
    
    # Не удалось переименовать ни одного файла
    (( countRenamedFiles == 0 )) && return "$EXIT_FAIL"
    
    return "$EXIT_OK"   
}



# ******************************************************************************
# main                        Главная функция
# ******************************************************************************
# init                        Инициализация
# ******************************************************************************


main()    # 1
{   
    isPrepareChecked || return "$EXIT_FAIL"
    
    parseArgs $ARGV
    local parseStatus="$?"
    
    (( isEnableVerbose == "$ON" )) && showVerbose
                       
    return "$parseStatus"
}


init()    # 2
{
    main $ARGV || exit "$EXIT_FAIL"
    exit "$EXIT_OK"
}



# ******************************************************************************
# printGeneralState          Отображение итоговой статистики
# ******************************************************************************
# printCachedWarnings        Отображение кэшированных предупреждений
# ******************************************************************************
# printCachedRenames         Отображение кэшированных состояний переименования
# ******************************************************************************
# printRenameState           Отображение сообщения о состоянии переименования
# ******************************************************************************


printGeneralState()    # 1
{
    local msg="$(printf "$PFORMAT_GENERAL_STATE" \
                         \
                          "$countRenamedFiles" "$TOTAL_FILES")"
                      
    showServiceMsg "$DEFAULT_FONT_COLOR" "$msg"
}


printCachedWarnings()    # 2
{
    for msg in "${warningMessages[@]}"; do
    
        showServiceMsg "$WARNING_FONT_COLOR" "$msg"
        
    done
}


printCachedRenames()    # 3
{
    for k in "${!currentContainer[@]}"; do
     
        local msg="$(printf "$PFORMAT_RENAMING" \
                         \
                          "$k" "${currentContainer["$k"]}" "$renamingMarker")"
                          
        showServiceMsg "$currentFontColor" "$msg"
                         
    done
}


printRenameState()    # 4
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



# ******************************************************************************
# setMsgFontColor                     Устанавливает цвет шрифта
# ******************************************************************************
# setValue                            Задаёт изменяющее значение
# ******************************************************************************


setMsgFontColor()    # 1
{
#    Множество возможных значений цвета шрифта:
#   +--------------------+--------------------+
#   | DEFAULT_FONT_COLOR | SUCCESS_FONT_COLOR |
#   +--------------------+--------------------+   
#   | WARNING_FONT_COLOR | ERROR_FONT_COLOR   |
#   +--------------------+--------------------+

    local tempFontColor="$1"
    
    echo -n -e "$tempFontColor" >&2
}


setValue()    # 2
{
    # Сделать первую букву строки прописной
    local promtString="${executeMode^}"
    
    # Определить итоговую строку приглашения к вводу значения аффикса
    promtString+=': '
    
    read -e -i "$defaultAffixValue" -p "$promtString" changeAffix
}



# ******************************************************************************
# showCachedRenames             Отображение кэш-ных результатов переименования
# ******************************************************************************
# showCachedResults             Отображение общих кэшированных результатов
# ******************************************************************************
# showServiceMsg                Отображение служебных сообщений
# ******************************************************************************
# showVerbose                   Отображение доп. сведений о состоянии
# ******************************************************************************


showCachedRenames()
{
    local currentRenameType="$1"
    
    defPrintMessageAttr "$currentRenameType"
    printCachedRenames
}


showCachedResults()    # 1
{
    printCachedWarnings
    
    showCachedRenames "$STATE_RENAME_FAIL"
    
    showCachedRenames "$STATE_RENAME_OK"
}


showServiceMsg()    # 2
{     
    # Заданный цвет шрифта
    currentFontColor="$1"
    
    # Заданное сообщение
    currentServiceMessage="$2"
    
    isHideMessage && return 1
    
    isChangeFontColor && setMsgFontColor "$currentFontColor"
    
    # Выводимое сообщение
    echo "$currentServiceMessage" >&2
    
    isChangeFontColor && setMsgFontColor "$DEFAULT_FONT_COLOR"
}


showVerbose()    # 3
{  
    (( isEnableCaching == "$ON" )) && showCachedResults
    
    printGeneralState
}



