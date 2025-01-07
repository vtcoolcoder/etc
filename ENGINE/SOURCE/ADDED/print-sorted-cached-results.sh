

printSortedResults()
{
    local fontColor="$1"
    
    sort |& \
   \
    while read msg
    
    do
    
        showServiceMsg "$fontColor" "$msg"
                      
    done
}



