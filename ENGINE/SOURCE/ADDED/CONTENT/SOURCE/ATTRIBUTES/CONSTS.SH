# ***************************************************************************
# ***************************************************************************
#                         ГЛОБАЛЬНЫЕ КОНСТАНТЫ
# ***************************************************************************
# ***************************************************************************

# ***************************************************************************
#                             ЦВЕТА ШРИФТА
# ***************************************************************************

declare -g -r DEFAULT_FONT_COLOR="\033[1;37m"    # белый
declare -g -r SUCCESS_FONT_COLOR="\033[1;32m"    # зелёный
declare -g -r WARNING_FONT_COLOR="\033[1;33m"    # жёлтый
declare -g -r ERROR_FONT_COLOR="\033[1;31m"      # красный
declare -g -r TRACING_FONT_COLOR="\033[1;35m"    # сиреневый

# ***************************************************************************
#                             СОСТОЯНИЯ ФЛАГОВ
# ***************************************************************************

declare -g -i -r ON=1     # включен
declare -g -i -r OFF=0    # отключен

# ***************************************************************************
#                             КОДЫ ЗАВЕРШЕНИЯ
# ***************************************************************************

declare -g -i -r EXIT_OK=0      # успех
declare -g -i -r EXIT_FAIL=1    # неудача

# ***************************************************************************
#                             СЛУЖЕБНЫЕ
# ***************************************************************************

declare -r PRFPF=": '%s'"
declare -r RNMBASE="переименован"
declare -r WNOT="не"
declare -r FILE="файл"
declare -r QUANTITY_RENAMED_FILES="Количество ${RNMBASE}ных ${FILE}ов:"

declare -g -r UNSET="${WNOT^} задан"
declare -g -r NOSOURCE="$UNSET источник!"
declare -g -r NOMODE="$UNSET поддерживаемый режим работы!"

# ***************************************************************************
#                             СОСТОЯНИЯ ПЕРЕИМЕНОВАНИЯ
# ***************************************************************************

declare -g -r STATE_RENAME_OK="OK"
declare -g -r STATE_RENAME_FAIL="ОШИБКА"

# ***************************************************************************
#                             ФОРМАТЫ ВЫВОДА
# ***************************************************************************

declare -g -r PFORMAT_RENAMING="${RNMBASE^}ие$PRFPF -> '%s'\t%s"
declare -g -r PFORMAT_ISDIR="${FILE^}$PRFPF — директория!"
declare -g -r PFORMAT_NOFILE="${FILE^}$PRFPF $WNOT существует!"
declare -g -r PFORMAT_GENERAL_STATE="$QUANTITY_RENAMED_FILES %s из %s"
declare -g -r PFORMAT_TRACING="%b\tТрассировка: %s\t\t%b\n"

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
#                     МЕНЮ: ВАРИАНТЫ ВЫБОРА РЕЖИМА РАБОТЫ 
# ***************************************************************************

declare -r MENUFILENAME="имени файла"
declare -r MENUFIX="фикс"
declare -r MENULETTER="букв"
declare -r MENUADD="Добавить"
declare -r MENUCUT="Вырезать"
declare -r MENUPREFIX="пре$MENUFIX"
declare -r MENUSUFFIX="суф$MENUFIX"
declare -r MENUNORM="Нормализовать"
declare -r MENUNORMALIZEALL="$MENUNORM все ${MENULETTER}ы $MENUFILENAME"
declare -r MENUNORMALIZEFIRST="$MENUNORM первую ${MENULETTER}у $MENUFILENAME"
    
declare -r MENULOWCASE="в строчн"
declare -r MENUUPCASE="в прописн"

# ***************************************************************************
        
declare -g -r MENUADDSUFFIX="$MENUADD $MENUSUFFIX к $MENUFILENAME"
declare -g -r MENUADDPREFIX="$MENUADD $MENUPREFIX к $MENUFILENAME"
declare -g -r MENUCUTSUFFIX="$MENUCUT $MENUSUFFIX из $MENUFILENAME"
declare -g -r MENUCUTPREFIX="$MENUCUT $MENUPREFIX из $MENUFILENAME"
    
declare -g -r MENUTOUPPER="$MENUNORMALIZEALL ${MENUUPCASE}ые"
declare -g -r MENUTOLOWER="$MENUNORMALIZEALL ${MENULOWCASE}ые"
declare -g -r MENUTOUPFIRST="$MENUNORMALIZEFIRST ${MENUUPCASE}ую"
declare -g -r MENUTOLOWFIRST="$MENUNORMALIZEFIRST ${MENULOWCASE}ую"

# ***************************************************************************
#                         КОНТЕЙНЕР РЕЖИМОВ РАБОТЫ
# ***************************************************************************

declare -g -A -r MODELIST=( ["$MENUADDSUFFIX"]="$ADDSUFFIX"
                            ["$MENUADDPREFIX"]="$ADDPREFIX"
                            ["$MENUCUTSUFFIX"]="$CUTSUFFIX"
                            ["$MENUCUTPREFIX"]="$CUTPREFIX"
                            ["$MENUTOUPPER"]="$TOUPPER"
                            ["$MENUTOLOWER"]="$TOLOWER"
                            ["$MENUTOUPFIRST"]="$TOUPFIRST"
                            ["$MENUTOLOWFIRST"]="$TOLOWFIRST" ) 
                            
# ***************************************************************************
                            
declare -g -r QUIT="Выйти"
                            
# ***************************************************************************
#      РЕГУЛЯРНЫЕ ВЫРАЖЕНИЯ ДЛЯ ПРОВЕРКИ КОРРЕКТНОСТИ ВВЕДЁННЫХ ДАННЫХ
# ***************************************************************************

declare -r RE_WORD='[-._A-Za-z0-9'
declare -r RE_RU='АаБбВвГгДдЕеЁёЖжЗзИиЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЪъЫыЬьЭэЮюЯя'
declare -r RE_END=']+$'
declare -r REGEXPR_BASE="${RE_WORD}${RE_RU}$RE_END"

declare -g -r REGEXPR_ADDED_PREFIX="^[^-]$REGEXPR_BASE"
declare -g -r REGEXPR_ADDED_SUFFIX="^$REGEXPR_BASE"

# ***************************************************************************
# 
# ***************************************************************************

declare -r SETTED_VALUE_NO_MATCH="Заданное значение не соответствует регулярному выражению:"

declare -g -r SUFX_NO_MATCH_TO_REGEXP="$( printf "%s\n%s\n"        "$SETTED_VALUE_NO_MATCH" "$REGEXPR_ADDED_SUFFIX" )"

declare -g -r PRFX_NO_MATCH_TO_REGEXP="$( printf "%s\n%s\n"        "$SETTED_VALUE_NO_MATCH" "$REGEXPR_ADDED_PREFIX" )"

# ***************************************************************************
# ***************************************************************************
