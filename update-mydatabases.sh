#!/usr/bin/env zsh
# ФАЙЛ СЦЕНАРИЯ: $HOME/bin/update-mydatabases.sh


update_target()
{
	echo -n -e "$1 ...\t" >&2
	if updatedb -U "$2" -o "$3" 2> /dev/null
	then
		echo OK >&2
		return 0
	else
		echo FAIL >&2
		return 1
	fi
}


lbell()
{
	for i in {0..9}
	do
		echo $'\a' >&2
		sleep 0.33
	done
}


declare -i errorCount=0


echo "Обновление баз данных для программы locate ..." >&2


update_target "Внутренняя память" "$HOME/in/" "$HOME/.in.db"
errorCount+=$?

update_target "SD-CARD" "$HOME/sd/" "$HOME/.sd.db"
errorCount+=$?

update_target "Домашний каталог Termux" "$HOME/" "$HOME/.home.db"
errorCount+=$?

update_target "Внешний HDD" "$HOME/hdd/" "$HOME/.hdd.db"
errorCount+=$?


if [[ -n "$1" ]]
then
	lbell
fi


if (( errorCount != 4 ))
then
	exit 0
else
	exit 1
fi



