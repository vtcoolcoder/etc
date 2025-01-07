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
declare -g -r CUTSUFFIX="$CUTTED $SUFFIX"

# вырезать префикс
declare -g -r CUTPREFIX="$CUTTED $PREFIX"

# добавить суффикс
declare -g -r ADDSUFFIX="$ADDED $SUFFIX"

# добавить префикс
declare -g -r ADDPREFIX="$ADDED $PREFIX"

# ***************************************************************************

# нормализовать буквы в строчные
declare -g -r TOLOWER="${RNM}LOWER$CAS"

# нормализовать буквы в прописные
declare -g -r TOUPPER="${RNM}UPPER$CAS"

# сделать первую букву строчной
declare -g -r TOLOWFIRST="${TOLOWER}$FLT"

# сделать первую букву прописной
declare -g -r TOUPFIRST="${TOUPPER}$FLT"


# ***************************************************************************
# ***************************************************************************

showMenu()
{  
    PS3="Выберите режим работы: "
    
    select menuMode in "${!modeList[@]}"
    
    do 
        executeMode="${modeList["$menuMode"]}"
        
        isSupportMode && return 0
        
        return 1
        
    done
}

showMenu



