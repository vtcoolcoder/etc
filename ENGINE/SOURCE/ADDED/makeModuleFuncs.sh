       
       
makeModuleConsts()
{
    cat << _EOF_
# ***************************************************************************
# ***************************************************************************
#                         ГЛОБАЛЬНЫЕ КОНСТАНТЫ
# ***************************************************************************
# ***************************************************************************


# ***************************************************************************
#                             ЦВЕТА ШРИФТА
# ***************************************************************************

declare -g -r DEFAULT_FONT_COLOR="\033[1;37m"    # белый
declare -g -r SUCCESS_FONT_COLOR="\033[1;32m"    # зелёный
declare -g -r WARNING_FONT_COLOR="\033[1;33m"    # жёлтый
declare -g -r ERROR_FONT_COLOR="\033[1;31m"      # красный
declare -g -r TRACING_FONT_COLOR="\033[1;35m"    # сиреневый

# ***************************************************************************
#                             СОСТОЯНИЯ ФЛАГОВ
# ***************************************************************************

declare -g -i -r ON=1     # включен
declare -g -i -r OFF=0    # отключен

# ***************************************************************************
#                             КОДЫ ЗАВЕРШЕНИЯ
# ***************************************************************************

declare -g -i -r EXIT_OK=0      # успех
declare -g -i -r EXIT_FAIL=1    # неудача

# ***************************************************************************
#                             СЛУЖЕБНЫЕ
# ***************************************************************************

declare -r PRFPF=": '%s'"
declare -r RNMBASE="переименован"
declare -r WNOT="не"
declare -r FILE="файл"
declare -r QUANTITY_RENAMED_FILES="Количество \${RNMBASE}ных \${FILE}ов:"

declare -g -r UNSET="\${WNOT^} задан"
declare -g -r NOSOURCE="\$UNSET источник!"
declare -g -r NOMODE="\$UNSET поддерживаемый режим работы!"

# ***************************************************************************
#                             СОСТОЯНИЯ ПЕРЕИМЕНОВАНИЯ
# ***************************************************************************

declare -g -r STATE_RENAME_OK="OK"
declare -g -r STATE_RENAME_FAIL="ОШИБКА"

# ***************************************************************************
#                             ФОРМАТЫ ВЫВОДА
# ***************************************************************************

declare -g -r PFORMAT_RENAMING="\${RNMBASE^}ие\$PRFPF -> '%s'\t%s"
declare -g -r PFORMAT_ISDIR="\${FILE^}\$PRFPF — директория!"
declare -g -r PFORMAT_NOFILE="\${FILE^}\$PRFPF \$WNOT существует!"
declare -g -r PFORMAT_GENERAL_STATE="\$QUANTITY_RENAMED_FILES %s из %s"
declare -g -r PFORMAT_TRACING="%b\tТрассировка: %s\t\t%b\n"

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
declare -g -r CUTSUFFIX="\$CUTTED \$SUFFIX"

# вырезать префикс
declare -g -r CUTPREFIX="\$CUTTED \$PREFIX"

# добавить суффикс
declare -g -r ADDSUFFIX="\$ADDED \$SUFFIX"

# добавить префикс
declare -g -r ADDPREFIX="\$ADDED \$PREFIX"

# ***************************************************************************

# нормализовать буквы в строчные
declare -g -r TOLOWER="\${RNM}LOWER\$CAS"

# нормализовать буквы в прописные
declare -g -r TOUPPER="\${RNM}UPPER\$CAS"

# сделать первую букву строчной
declare -g -r TOLOWFIRST="\${TOLOWER}\$FLT"

# сделать первую букву прописной
declare -g -r TOUPFIRST="\${TOUPPER}\$FLT"


# ***************************************************************************
#                     МЕНЮ: ВАРИАНТЫ ВЫБОРА РЕЖИМА РАБОТЫ 
# ***************************************************************************

declare -r MENUFILENAME="имени файла"
declare -r MENUFIX="фикс"
declare -r MENULETTER="букв"
declare -r MENUADD="Добавить"
declare -r MENUCUT="Вырезать"
declare -r MENUPREFIX="пре\$MENUFIX"
declare -r MENUSUFFIX="суф\$MENUFIX"
declare -r MENUNORM="Нормализовать"
declare -r MENUNORMALIZEALL="\$MENUNORM все \${MENULETTER}ы \$MENUFILENAME"
declare -r MENUNORMALIZEFIRST="\$MENUNORM первую \${MENULETTER}у \$MENUFILENAME"
    
declare -r MENULOWCASE="в строчн"
declare -r MENUUPCASE="в прописн"

# ***************************************************************************
        
declare -g -r MENUADDSUFFIX="\$MENUADD \$MENUSUFFIX к \$MENUFILENAME"
declare -g -r MENUADDPREFIX="\$MENUADD \$MENUPREFIX к \$MENUFILENAME"
declare -g -r MENUCUTSUFFIX="\$MENUCUT \$MENUSUFFIX из \$MENUFILENAME"
declare -g -r MENUCUTPREFIX="\$MENUCUT \$MENUPREFIX из \$MENUFILENAME"
    
declare -g -r MENUTOUPPER="\$MENUNORMALIZEALL \${MENUUPCASE}ые"
declare -g -r MENUTOLOWER="\$MENUNORMALIZEALL \${MENULOWCASE}ые"
declare -g -r MENUTOUPFIRST="\$MENUNORMALIZEFIRST \${MENUUPCASE}ую"
declare -g -r MENUTOLOWFIRST="\$MENUNORMALIZEFIRST \${MENULOWCASE}ую"

# ***************************************************************************
#                         КОНТЕЙНЕР РЕЖИМОВ РАБОТЫ
# ***************************************************************************

declare -g -A -r MODELIST=( ["\$MENUADDSUFFIX"]="\$ADDSUFFIX"
                            ["\$MENUADDPREFIX"]="\$ADDPREFIX"
                            ["\$MENUCUTSUFFIX"]="\$CUTSUFFIX"
                            ["\$MENUCUTPREFIX"]="\$CUTPREFIX"
                            ["\$MENUTOUPPER"]="\$TOUPPER"
                            ["\$MENUTOLOWER"]="\$TOLOWER"
                            ["\$MENUTOUPFIRST"]="\$TOUPFIRST"
                            ["\$MENUTOLOWFIRST"]="\$TOLOWFIRST" ) 
                            
# ***************************************************************************
                            
declare -g -r QUIT="Выйти"
                            
# ***************************************************************************
#      РЕГУЛЯРНЫЕ ВЫРАЖЕНИЯ ДЛЯ ПРОВЕРКИ КОРРЕКТНОСТИ ВВЕДЁННЫХ ДАННЫХ
# ***************************************************************************

declare -r RE_WORD='[-._A-Za-z0-9'
declare -r RE_RU='АаБбВвГгДдЕеЁёЖжЗзИиЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЪъЫыЬьЭэЮюЯя'
declare -r RE_END=']+\$'
declare -r REGEXPR_BASE="\${RE_WORD}\${RE_RU}\$RE_END"

declare -g -r REGEXPR_ADDED_PREFIX="^[^-]\$REGEXPR_BASE"
declare -g -r REGEXPR_ADDED_SUFFIX="^\$REGEXPR_BASE"

# ***************************************************************************
# 
# ***************************************************************************

declare -r SETTED_VALUE_NO_MATCH="Заданное значение не соответствует регулярному выражению:"

declare -g -r SUFX_NO_MATCH_TO_REGEXP="\$( printf "%s\n%s\n" \
   \
    "\$SETTED_VALUE_NO_MATCH" "\$REGEXPR_ADDED_SUFFIX" )"

declare -g -r PRFX_NO_MATCH_TO_REGEXP="\$( printf "%s\n%s\n" \
   \
    "\$SETTED_VALUE_NO_MATCH" "\$REGEXPR_ADDED_PREFIX" )"

# ***************************************************************************
# ***************************************************************************
_EOF_
}        

 
       
makeModuleVars()
{
    cat << _EOF_
# ***************************************************************************
# ***************************************************************************
#                         ГЛОБАЛЬНЫЕ ПЕРЕМЕННЫЕ
# ***************************************************************************
# ***************************************************************************


# ***************************************************************************
#                              СЛУЖЕБНЫЕ
# ***************************************************************************

# Текущий цвет шрифта
declare -g currentFontColor=""

# Текущий аффикс
declare -g currentAffix=""

# Файл-источник
declare -g sourceFile=""

# Имя файла-источника без указания пути
declare -g basenameSourceFile=""

# Имя файла-назначения
declare -g destinationFile=""

# Маркер переименования (успешно/неудачно)
declare -g renamingMarker=""

# Регулярное выражение для проверки наличия удаляемых аффиксов
declare -g fmtRegExpr=""

# Состояние переименования (успешно/неудачно)
declare -g renameStatus=""

# ***************************************************************************
#                             КОНТЕЙНЕРЫ СОСТОЯНИЙ
# ***************************************************************************

# переименования (ассоциативные массивы),
# где ключ — "имя файла-источника", значение — "имя файла-назначения"

declare -g -A okRenames=()            # успехи
declare -g -A errorRenames=()         # ошибки

# ***************************************************************************

# предупреждающих сообщений (индекс. массив)

declare -g -a warningMessages=()

# ***************************************************************************
#                              БУФЕРЫ НОРМАЛИЗАЦИИ
#                              ВСЕХ БУКВ ИМЕНИ ФАЙЛА
# ***************************************************************************

declare -g -l mvToLowerCase=""    # в строчные
declare -g -u mvToUpperCase=""    # в прописные

# ***************************************************************************
#                              ТЕКУЩИЙ РЕЖИМ РАБОТЫ
# ***************************************************************************
#
#               +-----------+-----------+-----------+------------+
#               | CUTSUFFIX | CUTPREFIX | ADDSUFFIX | ADDPREFIX  |
#               +-----------+-----------+-----------+------------+
#               | TOUPPER   | TOLOWER   | TOUPFIRST | TOLOWFIRST |
#               +-----------+-----------+-----------+------------+

declare -g executeMode=""

# ***************************************************************************
#                              ЗНАЧЕНИЯ ПО УМОЛЧАНИЮ
# ***************************************************************************

# изменяющего аффикса
declare -g defaultAffixValue=""

# ***************************************************************************

# счётчика успешно переименованных файлов
declare -g -i countRenamedFiles=0

# ***************************************************************************
#                               ФЛАГИ
# ***************************************************************************

# флага вкл/откл отображения доп. сообщений о состоянии
declare -g -i isEnableVerbose="\$ON"

# флага вкл/откл цветной подсветки сообщений
declare -g -i isEnableColors="\$ON"

# флага вкл/откл кэширования результатов
declare -g -i isEnableCaching="\$OFF"

# флага вкл/откл трассировки
declare -g -i isEnableTracing="\$OFF"

    
# ***************************************************************************
# ***************************************************************************
_EOF_
}        

 
       
makeModuleCheckings()
{
    cat << _EOF_
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
    showTracingMessage "isHideMessage( \$isEnableVerbose, \$renameStatus )"
    
    # Определение условия скрытия сообщения:
    #     если переименование успешно 
    #     и выключен флаг отображения доп. сообщений о состоянии
    
    if (( isEnableVerbose == "\$OFF" && renameStatus == "\$EXIT_OK" )) 
    
    then
    
        return "\$EXIT_OK"
        
    fi
    
    return "\$EXIT_FAIL"
}


isChangeFontColor()    # 2
{
    showTracingMessage "isChangeFontColor( \$isEnableColors, \$currentFontColor )"
    
    # Определение условия смены цвета шрифта:
    #     если включен флаг цветного отображения сообщений
    #     и заданный цвет шрифта не дефолтный   
    
    if (( isEnableColors == "\$ON" ))
    
    then
    
        if [[ "\$currentFontColor" != "\$DEFAULT_FONT_COLOR" ]]
        
        then
        
            return "\$EXIT_OK"
            
        fi
        
    fi
    
    return "\$EXIT_FAIL"
}


isFileMatchToRegExp()    # 3
{   
    encodingMetaSymbols
    defRegExpr
        
    showTracingMessage "isFileMatchToRegExp( \$fmtRegExpr, \$basenameSourceFile )"
                                
    grep "\$fmtRegExpr" <<< "\$basenameSourceFile" &> /dev/null
    
    return "\$?"
}


isFileRenamed()    # 4
{   
    defFileDestination
       
    showTracingMessage "isFileRenamed( \$sourceFile, \$destinationFile )"
        
    mv "\$sourceFile" "\$destinationFile" &> /dev/null && ((++countRenamedFiles))
        
    return "\$?"
}


isSetSupportMode()    # 5
{
    showTracingMessage "isSetSupportMode( \$executeMode )"
    
    case "\$executeMode" in
      
        "\$CUTSUFFIX"  | \
        "\$CUTPREFIX"  | \
        "\$ADDSUFFIX"  | \
        "\$ADDPREFIX"  | \
        "\$TOLOWER"    | \
        "\$TOUPPER"    | \
        "\$TOLOWFIRST" | \
        "\$TOUPFIRST"  )
            
                return "\$EXIT_OK"
        ;;
        
        * )
            # Прочие режимы работы (в разработке)
            
            makeWarning "\$NOMODE"
            showMenu
            return "\$?"
            
            #return "\$EXIT_FAIL"
        ;;
        
    esac
}


isSetArgs()    # 6
{
    showTracingMessage "isSetArgs( \$TOTAL_FILES )"
    
    [[ "\$TOTAL_FILES" != 0 ]] && return "\$EXIT_OK"
    
    makeWarning "\$NOSOURCE"
    
    return "\$EXIT_FAIL"
}


isSetValue()    # 7
{        
    setValue     # возможно, уже не нужна здесь
    
    showTracingMessage "isSetValue( \$currentAffix )"
    
    case "\$executeMode" in
    
        "\$ADDSUFFIX"  )
        
            setValidValue "\$REGEXPR_ADDED_SUFFIX" "\$SUFX_NO_MATCH_TO_REGEXP" ""
             
            return "\$EXIT_OK"
        
        ;;
        
        "\$ADDPREFIX"  )
            
            setValidValue "\$REGEXPR_ADDED_PREFIX" "\$PRFX_NO_MATCH_TO_REGEXP" ""
             
            return "\$EXIT_OK"
        
        ;;
        
        * )
            setValidValue "" "\$UNSET \$executeMode!" "NOADDING"
         
            return "\$EXIT_OK"
             
    esac   
}


isPreparelyChecked()    # 8
{   
    showTracingMessage "isPreparelyChecked()"
    
    if ! isSetSupportMode
    
    then
    
        showMenu
        
    fi && isSetArgs && defPrepareCheckedFinalStep
    
    return "\$?"
}


isFileExist()    # 9
{   
    showTracingMessage "isFileExist( \$sourceFile )"

    [[ -e "\$sourceFile" ]] && return "\$EXIT_OK"
    
    makeWarning "\$(printf "\$PFORMAT_NOFILE" "\$sourceFile")"
                                            
    return "\$EXIT_FAIL"
}


isFileDirectory()    # 10
{   
    showTracingMessage "isFileDirectory( \$sourceFile )"

    [[ -d "\$sourceFile" ]] || return "\$EXIT_OK"
    
    makeWarning "\$(printf "\$PFORMAT_ISDIR" "\$sourceFile")"
        
    return "\$EXIT_FAIL"
}


isFileChecked()    # 11
{   
    showTracingMessage "isFileChecked( \$sourceFile )"

    isFileExist && isFileDirectory && defFileCheckedFinalStep
    
    return "\$?"
}


isInitAsCore()    # 12
{
    [[ "\${0##*/}" == "core" ]]
    return "\$?"
}
_EOF_
}        

 
       
makeModuleDefings()
{
    cat << _EOF_
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
    renamingMarker="\$1"
    
    showTracingMessage "defPrintMessageAttr( \$renamingMarker )"
       
    case "\$renamingMarker" in
    
        "\$STATE_RENAME_OK" )
            
            defCurrentContainer
            currentFontColor="\$SUCCESS_FONT_COLOR"
        ;;
        
        "\$STATE_RENAME_FAIL" )
        
            defCurrentContainer
            currentFontColor="\$ERROR_FONT_COLOR"
        ;;
        
    esac
}


defResultDestination()    # 2
{  
    showTracingMessage "defResultDestination( \$isEnableCaching )"
    
    if (( isEnableCaching == "\$ON" ))
    
    then
    
        cacheRenameState
        
    else
    
        isInitAsCore && cacheRenameState
        
        printRenameState
        
    fi
}


defFileCheckedFinalStep()    # 3
{
    showTracingMessage "defFileCheckedFinalStep( \$executeMode )"
    
    case "\$executeMode" in
    
        "\$CUTSUFFIX" | \
        "\$CUTPREFIX" )  
          
            isFileMatchToRegExp || return "\$EXIT_FAIL"
        ;;
        
    esac
    
    return "\$EXIT_OK"
}


defPrepareCheckedFinalStep()    # 4
{ 
    showTracingMessage "defPrepareCheckedFinalStep( \$executeMode )"
    
    case "\$executeMode" in
        
        "\$CUTSUFFIX" | \
        "\$CUTPREFIX" | \
        "\$ADDSUFFIX" | \
        "\$ADDPREFIX" )
        
            isSetValue
        ;;
        
    esac
    
    return "\$?"
}


defRegExpr()    # 5
{     
    showTracingMessage "defRegExpr( \$executeMode )"
    
    case "\$executeMode" in
    
        "\$CUTSUFFIX" )  
          
            # Добавление якоря \$ в регулярное выражение, 
            # чтобы искать совпадения в конце имени файла
            fmtRegExpr+='\$'
        ;;                
        
        "\$CUTPREFIX" )    
        
            # Добавление якоря ^ в регулярное выражение, 
            # чтобы искать совпадения в начале имени файла
            fmtRegExpr="^\$fmtRegExpr"
        ;;
        
    esac
}


defFileDestination()    # 6
{   
    showTracingMessage "defFileDestination( \$sourceFile )"
    
    # Вырезать имя файла, оставив только путь
    pathToSourceFile="\${sourceFile%\$basenameSourceFile}"
    
    
    case "\$executeMode" in
    
        "\$CUTSUFFIX" ) # Удалить суффикс из имени файла  
         
            basenameDestinationFile="\${basenameSourceFile%\$currentAffix}"
        ;;
        
        "\$CUTPREFIX" ) # Удалить префикс из имени файла  
        
            basenameDestinationFile="\${basenameSourceFile#\$currentAffix}"
        ;;
        
        "\$ADDSUFFIX" ) # Добавить суффикс к имени файла  
        
            basenameDestinationFile="\${basenameSourceFile}\$currentAffix"
        ;;
        
        "\$ADDPREFIX" ) # Добавить префикс к имени файла   
        
            basenameDestinationFile="\${currentAffix}\$basenameSourceFile"
        ;;
        
        "\$TOUPPER" ) # Нормализовать имя файла до верхнего регистра 
        
            mvToUpperCase="\$basenameSourceFile"
            basenameDestinationFile="\$mvToUpperCase"
        ;;
        
        "\$TOLOWER" ) # Нормализовать имя файла до нижнего регистра
        
            mvToLowerCase="\$basenameSourceFile"
            basenameDestinationFile="\$mvToLowerCase"
        ;;
        
        "\$TOUPFIRST" ) # Сделать первую букву имени файла — прописной
        
            basenameDestinationFile="\${basenameSourceFile^}"
        ;;
        
        "\$TOLOWFIRST" ) # Сделать первую букву имени файла — строчной
        
            basenameDestinationFile="\${basenameSourceFile,}"
        ;;
        
    esac
    
    
    # Полное имя файла-назначения: путь исходного файла + имя производного файла
    destinationFile="\${pathToSourceFile}\$basenameDestinationFile"
}


defCurrentContainer()    # 7
{
    showTracingMessage "defCurrentContainer()"

    if [[ "\$renamingMarker" == "\$STATE_RENAME_OK" ]]
    
    then
        
        declare -g -n refToContainer=okRenames
               
    else
        
        declare -g -n refToContainer=errorRenames       
    
    fi    
}
_EOF_
}        

 
       
makeModuleEtc()
{
    cat << _EOF_
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

    local tmpMessage="\$1"
    
    showTracingMessage "makeWarning( \$tmpMessage )"

    if (( isEnableCaching == "\$ON" ))
    
    then

        warningMessages+=("\$tmpMessage")
    
    else

        showServiceMsg "\$WARNING_FONT_COLOR" "\$tmpMessage"
    
    fi
}


encodingMetaSymbols()    # 2
{   
    showTracingMessage "encodingMetaSymbols()"
    
    # Необходимо расширить множество кодируемых метасимволов

    fmtRegExpr="\${currentAffix//./\\.}"   # '.' -> '\.'
    fmtRegExpr="\${fmtRegExpr//\*/.*}"     # '*' -> '.*'
    fmtRegExpr="\${fmtRegExpr//\?/.}"      # '?' -> '.'            
}


cacheRenameState()    # 3
{   
    showTracingMessage "cacheRenameState( \$renameStatus )"
    
    if (( renameStatus == "\$EXIT_OK" ))
    
    then
    
        okRenames["\$sourceFile"]="\$destinationFile"
        
    else
    
        errorRenames["\$sourceFile"]="\$destinationFile"
        
    fi
}


tryRenameFile()    # 4
{ 
    showTracingMessage "tryRenameFile( \$sourceFile )"
    
    isFileRenamed
    renameStatus="\$?"
   
    defResultDestination
}


parseArgs()    # 5
{   
    showTracingMessage "parseArgs( \${ARGV[@]} )"

    for sourceFile
    
    do 
    
        # Вырезать путь к файлу, оставив только его имя
        basenameSourceFile="\${sourceFile##*/}"
    
        isFileChecked && tryRenameFile; 
        
    done
    
    # Не удалось переименовать ни одного файла
    (( countRenamedFiles == 0 )) && return "\$EXIT_FAIL"
    
    return "\$EXIT_OK"   
}
_EOF_
}        

 
       
makeModuleMainings()
{
    cat << _EOF_
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
    
        parseArgs "\${ARGV[@]}"
        local parseStatus="\$?"
    
        showVerbose
                       
        return "\$parseStatus"
    
    else
    
        showVerbose 
        return "\$EXIT_FAIL"
        
    fi
}


init()    # 2
{   
    showParentName
    
    if isInitAsCore
    
    then
    
        while showMenu
    
        do
        
            main "\${ARGV[@]}"
        
            updateArgv
        
            resetParams
        
        done
        
    else
        
        main "\${ARGV[@]}" || exit "\$EXIT_FAIL"
        exit "\$EXIT_OK"
        
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
    
    if (( "\${#okRenames[@]}" != 0 || "\${#errorRenames[@]}" != 0 ))
    
    then
    
        unset ARGV
        declare -g -a ARGV=()
        
        (( "\${#okRenames[@]}" != 0 )) && ARGV=("\${okRenames[@]}")
        (( "\${#errorRenames[@]}" != 0 )) && ARGV+=("\${!errorRenames[@]}")
        
    fi
}
_EOF_
}        

 
       
makeModulePrintings()
{
    cat << _EOF_
# *****************************************************************************
# printGeneralState          Отображение итоговой статистики
# *****************************************************************************
# printCachedWarnings        Отображение кэшированных предупреждений
# *****************************************************************************
# printCachedRenames         Отображение кэшированных состояний переименования
# *****************************************************************************
# printRenameState           Отображение сообщения о состоянии переименования
# *****************************************************************************
# printSortedResults         Отображение отсортированных результатов
# *****************************************************************************


printGeneralState()    # 1
{
    showTracingMessage "printGeneralState()"
                      
    showServiceMsg "\$DEFAULT_FONT_COLOR" "\$(printf "\$PFORMAT_GENERAL_STATE" \
                                           \
                                            "\$countRenamedFiles" "\$TOTAL_FILES")" 
}


printCachedWarnings()    # 2
{
    showTracingMessage "printCachedWarnings()"
    
    printCachedResults "\$WARNING_FONT_COLOR"
}


printCachedRenames()    # 3
{
    showTracingMessage "printCachedRenames()"
    
    printCachedResults "\$currentFontColor"
}


printRenameState()    # 4
{    
    showTracingMessage "printRenameState( \$renameStatus )"
    
    if (( renameStatus == "\$EXIT_OK" )) 
    
    then 
    
        local stateRename="\$STATE_RENAME_OK"
        local fontColor="\$SUCCESS_FONT_COLOR"
        
    else
    
        local stateRename="\$STATE_RENAME_FAIL" 
        local fontColor="\$ERROR_FONT_COLOR"
        
    fi
    
    showServiceMsg "\$fontColor" "\$(printf "\$PFORMAT_RENAMING" \
                                  \
                                   "\$sourceFile" "\$destinationFile" "\$stateRename")"
}


printSortedResults() # 5
{
    showTracingMessage "printSortedResults()"
    
    local fontColor="\$1"
    local msg=""
    
    sort |& \
   \
    while read msg
    
    do
    
        showServiceMsg "\$fontColor" "\$msg"
                      
    done
}


printCachedResults()
{
    showTracingMessage "printCachedResults()"

    local currentFontColor="\$1"
    
    local inline1=""
    local inline2=""
    
    local part1='for k in '
    local part2='; do echo '
    local part3=' >&2; done |& printSortedResults "\$currentFontColor"'
    
    if [[ "\$currentFontColor" == "\$WARNING_FONT_COLOR" ]]
    
    then

        inline1='"\${warningMessages[@]}"'
        
        inline2='"\$k"'
        
    else
    
        inline1='"\${!refToContainer[@]}"'
        
        inline2='"\$(printf "\$PFORMAT_RENAMING" "\$k" "\${refToContainer["\$k"]}" '
        inline2+='"\$renamingMarker")"'
        
    fi
    
    local integration=\$part1
    integration+=\$inline1
    integration+=\$part2
    integration+=\$inline2
    integration+=\$part3
    
    
    eval \$integration
}
_EOF_
}        

 
       
makeModuleSettings()
{
    cat << _EOF_
# ******************************************************************************
# setMsgFontColor               Устанавливает цвет шрифта
# ******************************************************************************
# setValue                      Задаёт изменяющее значение
# ******************************************************************************
# setValidValue                 Установить корректное значение аффикса
# *****************************************************************************


setMsgFontColor()    # 1
{
    showTracingMessage "setMsgFontColor()"
    
    #    Множество возможных значений аргумента (цвета шрифта):
    #         +--------------------+--------------------+
    #         | DEFAULT_FONT_COLOR | SUCCESS_FONT_COLOR |
    #         +--------------------+--------------------+   
    #         | WARNING_FONT_COLOR | ERROR_FONT_COLOR   |
    #         +--------------------+--------------------+
    
    isChangeFontColor && echo -n -e "\$1" >&2
}


setValue()    # 2
{
    showTracingMessage "setValue()"
    
    # Сделать первую букву строки прописной
    local promtString="\${executeMode^}"
    
    # Определить итоговую строку приглашения к вводу значения аффикса
    promtString+=': '
    
    read -e -i "\$defaultAffixValue" -p "\$promtString" currentAffix
}


setValidValue()    # 3
{
    showTracingMessage "setValidValue()"

    local currentRegExpr="\$1"
    local currentWarnMsg="\$2"
    local currentFlag="\$3"
        
    if [[ "\$currentFlag" == "NOADDING" ]]
    
    then
    
        local currentMode='[[ -n "\$currentAffix" ]]'
        
    else
    
        local currentMode='egrep "\$currentRegExpr" <<< "\$currentAffix" &> /dev/null'
        
    fi
      
    eval 'until ' \$currentMode '; do makeWarning "\$currentWarnMsg"; setValue; done'
}
_EOF_
}        

 
       
makeModuleShowings()
{
    cat << _EOF_
# *****************************************************************************
# showCachedRenames             Отображение кэш-ных результатов переименования
# *****************************************************************************
# showCachedResults             Отображение общих кэшированных результатов
# *****************************************************************************
# showServiceMsg                Отображение служебных сообщений
# *****************************************************************************
# showVerbose                   Отображение доп. сведений о состоянии
# *****************************************************************************
# showTracingMessage            Отображение сообщений трассировки            
# *****************************************************************************
# showParentName                Отображение имени сценария, запустившего движок
# *****************************************************************************
# showMenu                      Отображение меню, если режим работы некорректен
# *****************************************************************************


showCachedRenames()    # 1
{   
    local currentRenameType="\$1"
    
    showTracingMessage "showCachedRenames( \$currentRenameType )"
    
    defPrintMessageAttr "\$currentRenameType"
    printCachedRenames
}


showCachedResults()    # 2
{
    showTracingMessage "showCachedResults()"

    printCachedWarnings
    
    showCachedRenames "\$STATE_RENAME_FAIL"
    showCachedRenames "\$STATE_RENAME_OK"
}


showServiceMsg()    # 3
{     
    # Заданный цвет шрифта
    currentFontColor="\$1"
    
    # Заданное сообщение
    currentServiceMessage="\$2"
    
    showTracingMessage "showServiceMsg( \$currentFontColor, \$currentServiceMessage )"
    
    isHideMessage && return 
    
    setMsgFontColor "\$currentFontColor"
    
    echo -e "\$currentServiceMessage" >&2
    
    setMsgFontColor "\$DEFAULT_FONT_COLOR"
}


showVerbose()    # 4
{  
    showTracingMessage "showVerbose()"
    
    (( isEnableVerbose == "\$OFF" )) && return
    
    (( isEnableCaching == "\$ON" )) && showCachedResults
    
    printGeneralState
}


showTracingMessage()    # 5
{                      
    if (( isEnableTracing == "\$ON" )) 
    
    then
    
        local tracingMessage="\$1"
        
        local beforeFontColor=""
        local afterFontColor=""
        
        if (( isEnableColors == "\$ON" ))
        
        then
        
            beforeFontColor="\$TRACING_FONT_COLOR"
            afterFontColor="\$DEFAULT_FONT_COLOR"
         
        fi
    
        printf "\$PFORMAT_TRACING" \
           \
            "\$beforeFontColor" "\$tracingMessage" "\$afterFontColor" >&2
            
    fi
}


showParentName()    # 6
{ 
    if (( isEnableTracing == "\$ON" ))
    
    then
    
        showTracingMessage "\$SIXSPACES"
        showTracingMessage "\$PARENT_SCRIPT_NAME"
        showTracingMessage "\$SIXSPACES"
        
    fi
}


showMenu()    # 7
{   
    showTracingMessage "showMenu()"
    
    PS3=\$'\t\t\t\t\tВыберите режим работы [1-9]: '
    
    select menuMode in "\${!MODELIST[@]}" "\$QUIT"
    
    do 

        case "\$menuMode" in
        
            "\$QUIT" ) 
            
                printCachedWarnings
                exit "\$EXIT_OK" 
            ;;
        
            "\$MENUADDSUFFIX"  | \
            "\$MENUADDPREFIX"  | \
            "\$MENUCUTSUFFIX"  | \
            "\$MENUCUTPREFIX"  | \
            "\$MENUTOUPPER"    | \
            "\$MENUTOLOWER"    | \
            "\$MENUTOUPFIRST"  | \
            "\$MENUTOLOWFIRST" )
            
                executeMode="\${MODELIST["\$menuMode"]}"
                return "\$EXIT_OK"
            ;;
            
            * )
            
                makeWarning "Некорректный ввод!"
            ;;
        
        esac
        
    done
}
_EOF_
}        

 

