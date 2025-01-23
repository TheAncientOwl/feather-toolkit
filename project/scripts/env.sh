#!/bin/bash

# » console colors
export DARK_RED="\033[31m"
export DARK_GREEN="\033[32m"
export YELLOW="\033[93m"
export DARK_GRAY="\033[90m"
export LIGHT_GRAY="\033[37m"
export LIGHT_GREEN="\033[92m"
export DARK_AQUA="\033[36m"
export LIGHT_RED="\033[91m"
export RESET="\033[0m"

# » Project tag
export TAG="${DARK_GRAY}[${YELLOW}FeatherToolkit${DARK_GRAY}]${RESET} » "

# » Utilities
function print() {
    printf "$1\n${RESET}"
}

function feather_print() {
    print "${TAG}$1"
}
