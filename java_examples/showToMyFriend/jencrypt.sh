#!/usr/bin/env bash

key="$1"
source="$2"
[[ -n "$3" ]] && destination='encrypt.dat'
destination="$3"

checkFile()
{
    if [[ ! -e "$1" ]]
    then
        echo "Файл $1 не существует!" >&2
        exit 1
    fi
}

checkFile $key
checkFile $source

java Encoder "$(< $key)" < $source > $destination 2> /dev/null
