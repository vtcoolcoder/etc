#sed "s/showTracingMessage /\$\(includeTracing '\0/g" makeModuleFuncs.sh | sed "/includeTracing/s/\"$/\0'\)/g" 


makeContent()
{
    if [[ ! -d "CONTENT-mid" ]]
    then
        if mkdir -p "CONTENT-mid/ATTRIBUTES" "CONTENT-mid/FUNCS"
        then
    
            makeModuleConsts > "ATTRIBUTES/CONSTS-mid.SH"
            makeModuleVars > "ATTRIBUTES/VARS-mid.SH"
    
            makeModuleCheckings > "FUNCS/CHECKINGS-mid.SH"
            makeModuleDefings > "FUNCS/DEFINGS-mid.SH"
            makeModuleEtc > "FUNCS/ETC-mid.SH"
            makeModuleMainings > "FUNCS/MAININGS-mid.SH"
            makeModulePrintings > "FUNCS/PRINTINGS-mid.SH"
            makeModuleSettings > "FUNCS/SETTINGS-mid.SH"
            makeModuleShowings > "FUNCS/SHOWINGS-mid.SH"
        fi
    fi
}