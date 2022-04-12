#!/bin/bash

function check_utils {
    for util in "${@}"
    do
        command -v ${util} >/dev/null 2>&1 || { echo >&2 "The utility ${util} is not installed."; exit 1; }
    done
}

function join_by {
    local d=${1-} f=${2-}
    if shift 2; then
        printf %s "$f" "${@/#/$d}"
    fi
}

function usage {
    cat <<EOF
SYNOPSIS
    ${0} -n|--name <name> [OPTION]...

DESCRIPTION
    Create an app configuration for a Liferay Remote App. Execute from the root of a remote app project and ensure the project is built.

OPTIONS
    -h, --help
        this help message

    -n, --name <name>
        the friendly name of the application

    -e, --customElementName <customElementName>
        the name of the custom element when <type> is customElement

    -u, --iFrameURL <iFrameURL>
        the URL to use when <type> is iframe

    -d, --description <description>
        a description of the application

    -t, --type <type>
        accepted values are 'customElement' and 'iframe'

    -i, --instanceable
        sets <instanceable> to true, otherwise false

    -c, --portletCategoryName <portletCategoryName>
        the name of the category in which to place the resulting widget

    -f, --friendlyURLMapping <friendlyURLMapping>
        an alphanumeric string used in urls for the app

    -p, --property <property>
        repeatable. the value must take the form of a 'key=value' pair

EOF
}

function write_app_config {
    if [[ ! -e build/asset-manifest.json ]]; then
        echo "can't find required file 'build/asset-manifest.json' in directory. Ensure the script is called from the root of a remote app project."
        usage
        exit 4
    fi

    CSS_FILES=
    JS_FILES=

    if [[ "$t" == "customElement" ]];then
        CSS_FILES=$(jq -c "[.files[] | \"${REMOTE_APP_HOST}\" + select(endswith(\".css\"))]" build/asset-manifest.json)
        JS_FILES=$(jq -c "[.files[] | \"${REMOTE_APP_HOST}\" + select(endswith(\".js\"))]" build/asset-manifest.json)

        # If we want comma delimited string value use bellow instead
        #CSS_FILES=$(jq -c "[.files[] | "http://localhost:8090" + select(endswith(".css"))] | join(",")' build/asset-manifest.json)
        #JS_FILES=$(jq -c "[.files[] | "http://localhost:8090" + select(endswith(".js"))] | join(",")' build/asset-manifest.json)
    fi

    cat <<EOF
    name="${n}"
    description="${d}"
    type="${t}"
    iFrameURL="${u}"
    elementName="${e}"
    webComponentUrl=${JS_FILES}
    webComponentCssUrl=${CSS_FILES}
    friendlyURLMapping="${f}"
    instanceable="${i}"
    portletDisplayCategory="${c}"
    portletServiceProperties="$(join_by $'\\n' ${p[@]})"
EOF
}

# More safety, by turning some bugs into errors.
# Without `errexit` you don’t need ! and can replace
# PIPESTATUS with a simple $?, but I don’t do that.
set -o errexit -o pipefail -o noclobber -o nounset

# -allow a command to fail with !’s side effect on errexit
# -use return value from ${PIPESTATUS[0]}, because ! hosed $?
! getopt --test > /dev/null
if [[ ${PIPESTATUS[0]} -ne 4 ]]; then
    echo 'I’m sorry, `getopt --test` failed in this environment.'
    exit 1
fi

OPTIONS=hn:e:u:d:t:ic:f:p:
LONGOPTS=help,name:,customElementName:,iFrameURL:,description:,type:,instanceable,portletCategoryName:,friendlyURLMapping:,property:
PCENCHAR=[-._0-9a-z\\xB7\\xC0-\\xD6\\xD8-\\xF6\\xF8-\\x{037D}\\x{037F}-\\x{1FFF}\\x{200C}-\\x{200D}\\x{203F}-\\x{2040}\\x{2070}-\\x{218F}\\x{2C00}-\\x{2F2F}\\x{3001}-\\x{D7FF}\\x{F900}-\\x{FDCF}\\x{FDF0}-\\x{FFFD}\\x{10000}-\\x{EFFFF}]
CUSTOM_ELEMENT_NAME_REGEX=^[a-z]${PCENCHAR}*-${PCENCHAR}*$
PROPERTY_REGEX=^[a-zA-Z0-9_.-]+=.*$
PORTLET_CATEGORY_NAME_REGEX=^[a-zA-Z0-9]+\(-[a-zA-Z0-9]+\)*$
FRIENDLY_URL_MAPPING_REGEX=^[a-zA-Z0-9]+$

# -regarding ! and PIPESTATUS see above
# -temporarily store output to be able to check for errors
# -activate quoting/enhanced mode (e.g. by writing out “--options”)
# -pass arguments only via   -- "$@"   to separate them correctly
! PARSED=$(getopt --options=$OPTIONS --longoptions=$LONGOPTS --name "$0" -- "$@")
if [[ ${PIPESTATUS[0]} -ne 0 ]]; then
    # e.g. return value is 1
    #  then getopt has complained about wrong arguments to stdout
    exit 2
fi
# read getopt’s output this way to handle the quoting right:
eval set -- "$PARSED"

n=- e= u= d= t="customElement" i=false c="remote-app" f= p=()
# now enjoy the options in order and nicely split until we see --
while true; do
    case "$1" in
        -h|--help)
            usage
            exit
            ;;
        -n|--name)
            n="$2"
            shift 2
            ;;
        -e|--customElementName)
            if ! (grep -q -P $CUSTOM_ELEMENT_NAME_REGEX <<<"$2"); then
                echo "invalid -e|--customElementName argument '$2'"
                usage
                exit 4
            fi
            e="$2"
            shift 2
            ;;
        -u|--iFrameURL)
            u="$2"
            shift 2
            ;;
        -d|--description)
            d="$2"
            shift 2
            ;;
        -t|--type)
            if [[ "$2" != "customElement" && "$2" != "iframe" ]]; then
                echo "invalid -t|--type argument '$2'"
                usage
                exit 4
            fi
            t="$2"
            shift 2
            ;;
        -i|--instanceable)
            i=true
            shift
            ;;
        -c|--portletCategoryName)
            if [[ ! $2 =~ $PORTLET_CATEGORY_NAME_REGEX ]]; then
                echo "invalid -c|--portletCategoryName argument '$2'"
                usage
                exit 4
            fi
            c="$2"
            shift 2
            ;;
        -f|--friendlyURLMapping)
            if [[ ! $2 =~ $FRIENDLY_URL_MAPPING_REGEX ]]; then
                echo "invalid -f|--friendlyURLMapping argument '$2'"
                usage
                exit 4
            fi
            f="$2"
            shift 2
            ;;
        -p|--property)
            if [[ ! $2 =~ $PROPERTY_REGEX ]]; then
                echo "invalid -p|--property argument '$2'"
                usage
                exit 4
            fi
            p+=("$2")
            shift 2
            ;;
        --)
            shift
            break
            ;;
        *)
            echo "Programming error"
            exit 3
            ;;
    esac
done

# handle non-option arguments
if [[ "$n" == "-" ]]; then
    echo "-n|--name argument required"
    usage
    exit 4
fi
if [[ "$t" == "iframe" && "$u" == "" ]]; then
    echo "-u|--iFrameURL is required when -t|--type is 'iframe'"
    usage
    exit 4
fi
if [[ "$t" == "customElement" && "$e" == "" ]]; then
    echo "-e|--customElementName is required when -t|--type is 'customElement' (default)"
    usage
    exit 4
fi

if [[ ("$t" == "iframe" && "$e" != "") || ("$t" == "customElement" && "$u" != "") ]]; then
    echo "-u|--iFrameURL cannot be used with -e|--customElementName"
    usage
    exit 4
fi

check_utils jq

write_app_config
