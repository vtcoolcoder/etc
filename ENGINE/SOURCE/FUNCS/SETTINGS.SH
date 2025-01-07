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
    
    isChangeFontColor && echo -n -e "$1" >&2
}


setValue()    # 2
{
    showTracingMessage "setValue()"
    
    # Сделать первую букву строки прописной
    local promtString="${executeMode^}"
    
    # Определить итоговую строку приглашения к вводу значения аффикса
    promtString+=': '
    
    read -e -i "$defaultAffixValue" -p "$promtString" currentAffix
}


setValidValue()    # 3
{
    showTracingMessage "setValidValue()"

    local currentRegExpr="$1"
    local currentWarnMsg="$2"
    local currentFlag="$3"
        
    if [[ "$currentFlag" == "NOADDING" ]]
    
    then
    
        local currentMode='[[ -n "$currentAffix" ]]'
        
    else
    
        local currentMode='egrep "$currentRegExpr" <<< "$currentAffix" &> /dev/null'
        
    fi
      
    eval 'until ' $currentMode '; do makeWarning "$currentWarnMsg"; setValue; done'
}



