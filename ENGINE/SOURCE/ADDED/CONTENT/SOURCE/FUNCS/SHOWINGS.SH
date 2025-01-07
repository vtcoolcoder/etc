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
    local currentRenameType="$1"
    
    showTracingMessage "showCachedRenames( $currentRenameType )"
    
    defPrintMessageAttr "$currentRenameType"
    printCachedRenames
}

showCachedResults()    # 2
{
    showTracingMessage "showCachedResults()"

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
    
    showTracingMessage "showServiceMsg(                $currentFontColor, $currentServiceMessage )"
    
    isHideMessage && return 
    
    setMsgFontColor "$currentFontColor"
    
    echo -e "$currentServiceMessage" >&2
    
    setMsgFontColor "$DEFAULT_FONT_COLOR"
}

showVerbose()    # 4
{  
    showTracingMessage "showVerbose()"
    
    (( isEnableVerbose == "$OFF" )) && return
    
    (( isEnableCaching == "$ON" )) && showCachedResults
    
    printGeneralState
}

showTracingMessage()    # 5
{                      
    if (( isEnableTracing == "$ON" )) 
    
    then
    
        local tracingMessage="$1"
        
        local beforeFontColor=""
        local afterFontColor=""
        
        if (( isEnableColors == "$ON" ))
        
        then
        
            beforeFontColor="$TRACING_FONT_COLOR"
            afterFontColor="$DEFAULT_FONT_COLOR"
         
        fi
    
        printf "$PFORMAT_TRACING"                        "$beforeFontColor" "$tracingMessage" "$afterFontColor" >&2
            
    fi
}

showParentName()    # 6
{ 
    if (( isEnableTracing == "$ON" ))
    
    then
    
        showTracingMessage "$SIXSPACES"
        showTracingMessage "$PARENT_SCRIPT_NAME"
        showTracingMessage "$SIXSPACES"
        
    fi
}

showMenu()    # 7
{   
    showTracingMessage "showMenu()"
    
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
