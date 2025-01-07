# *****************************************************************************
# showCachedRenames             Отображение кэш-ных результатов переименования
# *****************************************************************************
# showCachedResults             Отображение общих кэшированных результатов
# *****************************************************************************
# showServiceMsg                Отображение служебных сообщений
# *****************************************************************************
# showVerbose                   Отображение доп. сведений о состоянии
# *****************************************************************************
# showParentName                Отображение имени сценария, запустившего движок
# *****************************************************************************
# showMenu                      Отображение меню, если режим работы некорректен
# *****************************************************************************


showCachedRenames()    # 1
{   
    local currentRenameType="$1"
       
    defPrintMessageAttr "$currentRenameType"
    printCachedRenames
}


showCachedResults()    # 2
{
    printCachedWarnings
    
    showCachedRenames "$STATE_RENAME_FAIL"
    showCachedRenames "$STATE_RENAME_OK"
}


showServiceMsg()    # 3
{     
    # Заданный цвет шрифта
    currentFontColor="$1"
    
    # Заданное сообщение
    currentServiceMessage="$2"
    
       
    isHideMessage && return 
    
    setMsgFontColor "$currentFontColor"
    
    echo -e "$currentServiceMessage" >&2
    
    setMsgFontColor "$DEFAULT_FONT_COLOR"
}


showVerbose()    # 4
{     
    (( isEnableVerbose == "$OFF" )) && return
    
    (( isEnableCaching == "$ON" )) && showCachedResults
    
    printGeneralState
}


showMenu()    # 7
{      
    PS3=$'\t\t\t\t\tВыберите режим работы [1-9]: '
    
    select menuMode in "${!MODELIST[@]}" "$QUIT"
    
    do 

        case "$menuMode" in
        
            "$QUIT" ) 
            
                printCachedWarnings
                exit "$EXIT_OK" 
            ;;
        
            "$MENUADDSUFFIX"  | \
            "$MENUADDPREFIX"  | \
            "$MENUCUTSUFFIX"  | \
            "$MENUCUTPREFIX"  | \
            "$MENUTOUPPER"    | \
            "$MENUTOLOWER"    | \
            "$MENUTOUPFIRST"  | \
            "$MENUTOLOWFIRST" )
            
                executeMode="${MODELIST["$menuMode"]}"
                return "$EXIT_OK"
            ;;
            
            * )
            
                makeWarning "Некорректный ввод!"
            ;;
        
        esac
        
    done
}

