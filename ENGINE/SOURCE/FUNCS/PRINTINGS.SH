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
                      
    showServiceMsg "$DEFAULT_FONT_COLOR" "$(printf "$PFORMAT_GENERAL_STATE" \
                                           \
                                            "$countRenamedFiles" "$TOTAL_FILES")" 
}


printCachedWarnings()    # 2
{
    showTracingMessage "printCachedWarnings()"
    
    printCachedResults "$WARNING_FONT_COLOR"
}


printCachedRenames()    # 3
{
    showTracingMessage "printCachedRenames()"
    
    printCachedResults "$currentFontColor"
}


printRenameState()    # 4
{    
    showTracingMessage "printRenameState( $renameStatus )"
    
    if (( renameStatus == "$EXIT_OK" )) 
    
    then 
    
        local stateRename="$STATE_RENAME_OK"
        local fontColor="$SUCCESS_FONT_COLOR"
        
    else
    
        local stateRename="$STATE_RENAME_FAIL" 
        local fontColor="$ERROR_FONT_COLOR"
        
    fi
    
    showServiceMsg "$fontColor" "$(printf "$PFORMAT_RENAMING" \
                                  \
                                   "$sourceFile" "$destinationFile" "$stateRename")"
}


printSortedResults() # 5
{
    showTracingMessage "printSortedResults()"
    
    local fontColor="$1"
    local msg=""
    
    sort |& \
   \
    while read msg
    
    do
    
        showServiceMsg "$fontColor" "$msg"
                      
    done
}


printCachedResults()
{
    showTracingMessage "printCachedResults()"

    local currentFontColor="$1"
    
    local inline1=""
    local inline2=""
    
    local part1='for k in '
    local part2='; do echo '
    local part3=' >&2; done |& printSortedResults "$currentFontColor"'
    
    if [[ "$currentFontColor" == "$WARNING_FONT_COLOR" ]]
    
    then

        inline1='"${warningMessages[@]}"'
        
        inline2='"$k"'
        
    else
    
        inline1='"${!refToContainer[@]}"'
        
        inline2='"$(printf "$PFORMAT_RENAMING" "$k" "${refToContainer["$k"]}" '
        inline2+='"$renamingMarker")"'
        
    fi
    
    local integration=$part1
    integration+=$inline1
    integration+=$part2
    integration+=$inline2
    integration+=$part3
    
    
    eval $integration
}



