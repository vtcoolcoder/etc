#!/usr/bin/env bash


scriptName="$(basename $0)"

declare -r -i FAIL=1
declare -r -i SUCCESS=0

declare -r ENCRYPT_SCRIPT_NAME=jencrypt
declare -r DECRYPT_SCRIPT_NAME=jdecrypt

declare -r -i ENCRYPT=1
declare -r -i DECRYPT=0

declare -r ENCRYPT_DEFAULT_FILE=encrypt.dat
declare -r DECRYPT_DEFAULT_FILE=decrypt.dat


if [[ "$scriptName" == "$ENCRYPT_SCRIPT_NAME" ]] 
then
    mode="$ENCRYPT"
    
elif [[ "$scriptName" == "$DECRYPT_SCRIPT_NAME" ]]
then
    mode="$DECRYPT"
    
else
    {
    echo 'Неверное имя скрипта!'
    echo "Допустимые имена: $ENCRYPT_SCRIPT_NAME | $DECRYPT_SCRIPT_NAME"
    } >&2
    exit $FAIL
fi


usage()
{
    local src='source_file' 
    local dest=' [destination_file]'
    local info="Правила использования: $scriptName key_file "
    
    if (( mode == DECRYPT ))
    then
        info+="[$src]"
    else
        info+="$src"
    fi
    
    info+="$dest"
    echo "$info" >&2
    
    exit $FAIL
}


checkParam()
{
    if [[ -z "$1" ]]
    then
        echo "Не задан $2" >&2
        return $FAIL
    else
        return $SUCCESS
    fi
}


checkFile()
{
    if [[ ! -e "$1" ]]
    then
        echo "Файл $1 не существует!" >&2
        return $FAIL
    else
        return $SUCCESS
    fi
}


checkParam "$1" 'файл-ключ!' || usage
key="$1"


if (( mode == ENCRYPT ))
then
    checkParam "$2" 'файл-источник!' || usage 
    source="$2"
    destination="${3-$ENCRYPT_DEFAULT_FILE}"   
    
elif (( mode == DECRYPT ))
then     
    source="${2-$ENCRYPT_DEFAULT_FILE}"
    destination="${3-$DECRYPT_DEFAULT_FILE}"    
fi


checkFile $key || exit $FAIL
checkFile $source || exit $FAIL


java Encoder "$(< $key)" < $source > $destination 
