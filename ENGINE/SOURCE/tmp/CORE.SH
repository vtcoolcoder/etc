# Движок для сценариев: 
# "addprefix", "addsuffix", "cutprefix", "cutsuffix", "upper", "lower",
# "upfirst", "lowfirst"

# Если данный сценарий вызывается сам по себе, выводить меню с приглашением
# к вводу для выбора режима работы.

# Можно попробовать сделать так, чтобы можно было использовать данный сценарий
# в конвейерах.

# Проверять корректность ввода регулярному выражению.

# Добавить цветную подсветку сообщений о состоянии    (OK)
# И возможность её вкл/откл                           (OK)

# Добавить режимы работы:

#     переименование:
#         всех букв в строчные/прописные          ааа/ААА     (OK)
#         первой буквы в строчную/прописную       аАА/Ааа     (OK)
#         отдельных буквы по шаблону              аАа/АаА


# Помимо переименования добавить команды:
# ln -s (создание симлинок)

# Доработать функцию printCachedStates()
# Если вкючен флаг кэширования, не запускать функцию

# ******************************************************************************
# ******************************************************************************

#IMPORT_COMMON_PATH="$HOME/bin/ENGINE/SOURCE/"
#IMPORT_ATTRS_PATH="${IMPORT_COMMON_PATH}ATTRIBUTES/"
IMPORT_FUNCS_PATH="${IMPORT_COMMON_PATH}FUNCS/"

COMM_SUFFIX=".SH"
FUNC_SUFFIX="INGS$COMM_SUFFIX"

# ******************************************************************************

tryImportFile()
{
    local importFile="$1"
    
    if [[ -e "$importFile" ]]; then

        source "$importFile"
    
    else

        echo "Импортируемый файл '$importFile' не найден!" >&2
        exit 1
    
    fi
}

# ******************************************************************************

#tryImportFile "${IMPORT_ATTRS_PATH}CONSTS$COMM_SUFFIX"
#tryImportFile "${IMPORT_ATTRS_PATH}VARS$COMM_SUFFIX"


tryImportFile "${IMPORT_FUNCS_PATH}PROTOTYPES$COMM_SUFFIX"

tryImportFile "${IMPORT_FUNCS_PATH}ETC$COMM_SUFFIX"
tryImportFile "${IMPORT_FUNCS_PATH}MAIN$FUNC_SUFFIX"
tryImportFile "${IMPORT_FUNCS_PATH}CHECK$FUNC_SUFFIX"
tryImportFile "${IMPORT_FUNCS_PATH}SETT$FUNC_SUFFIX"
tryImportFile "${IMPORT_FUNCS_PATH}DEF$FUNC_SUFFIX"
tryImportFile "${IMPORT_FUNCS_PATH}PRINT$FUNC_SUFFIX"
tryImportFile "${IMPORT_FUNCS_PATH}SHOW$FUNC_SUFFIX"

# ******************************************************************************
# ******************************************************************************



