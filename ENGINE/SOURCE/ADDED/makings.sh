declare -g -r PREPAREEXP='s/\$/\\$/g'
declare -g -r FINALEXP='s/\\\$/\$/g'
declare -g -r PRE='-pre'
declare -g -r FINAL='-final'

#declare -g -a argv=("$@")

declare -g -a argv=()

argv+=("../ATTRIBUTES/CONSTS.SH")
argv+=("../ATTRIBUTES/VARS.SH") 

argv+=("../FUNCS/CHECKINGS.SH")
argv+=("../FUNCS/DEFINGS.SH")
argv+=("../FUNCS/ETC.SH")
argv+=("../FUNCS/MAININGS.SH")
argv+=("../FUNCS/PRINTINGS.SH")
argv+=("../FUNCS/SETTINGS.SH")
argv+=("../FUNCS/SHOWINGS.SH")

argv+=("../CORE.SH")

argv+=("../../addprefix")
argv+=("../../addsuffix")
argv+=("../../cutprefix")
argv+=("../../cutsuffix")
argv+=("../../lower")
argv+=("../../lowfirst")
argv+=("../../upfirst")
argv+=("../../upper")


modulePartMakingLoop()
{
    # "$1": expression
    # "$2": suffix
    
    for sourceFile in "${argv[@]}"
    
    do
    
        funcsSuffix="${sourceFile##*/}"
        
        case "$funcsSuffix" in
        
            "CORE.SH"   | \
            "addprefix" | \
            "addsuffix" | \
            "cutprefix" | \
            "cutsuffix" | \
            "lower"     | \
            "lowfirst"  | \
            "upfirst"   | \
            "upper"     )
            
                heredocType='_"EOF"_'
            ;;
        
            *)
                heredocType='_EOF_'
            ;;
            
        esac
        
        
        funcsSuffix="${funcsSuffix%.*}"
        declare -l lower="$funcsSuffix"
        funcsSuffix="${lower^}"
        
        echo " 
       
makeModule${funcsSuffix}()
{
    cat -s << "$heredocType"
$(if [[ "$heredocType" == '_EOF_' ]]; then sed "$1" "$sourceFile"; else cat "$sourceFile"; fi)
_EOF_
}        
"
        
    done | sed 's_| \\_| \\\\_g' > makeModule.sh
}


modulePartMakingLoop "$PREPAREEXP" "$PRE"

#modulePartMakingLoop "$FINALEXP" "$FINAL"

