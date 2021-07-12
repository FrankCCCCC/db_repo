RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color
# The ANSI color code
# https://stackoverflow.com/questions/5947742/how-to-change-the-output-color-of-echo-in-linux

hb="./hermes/"
r="./readOnly/"
rr="./readOnlyRev/"
g="./greedy/"
gr="./greedyRev/"
ng="./newGreedy/"

client="client.jar"
server="server.jar"
remove="rm ${server} ${client}"

if [ "$1" = "--help" ] || [ "$1" = "-h" ]
then
    echo -e "${GREEN}A bash scipt to move Jars to workspace."
    echo -e "The acceptable parameters: ${NC}"
    echo -e "${YELLOW}HB:${NC} Hermes Baseline"
    echo -e "${YELLOW}R:${NC} Read-Only"
    echo -e "${YELLOW}RR:${NC} Read-Only Reverse"
    echo -e "${YELLOW}G:${NC} Greedy With Dirty-Read (AgtB = 1, AwtB = -1)"
    echo -e "${YELLOW}GR:${NC} Greedy With Dirty-Read (AgtB = 1, AwtB = -1) Reverse List"
elif [ "$1" = "--remove" ] || [ "$1" = "-r" ]
then
    echo -e "${YELLOW}Remove Jars${NC}"
    $remove
elif [ "$1" = "HB" ]
then
    echo -e "${YELLOW}Hermes Baseline${NC}"
    $remove
    cp $hb/$client ./
    cp $hb/$server ./
elif [ "$1" = "R" ]
then
    echo -e "${YELLOW}Read-Only${NC}"
    $remove
    cp $r/$client ./
    cp $r/$server ./
elif [ "$1" = "RR" ]
then
    echo -e "${YELLOW}Read-Only Reverse List${NC}"
    $remove
    cp $rr/$client ./
    cp $rr/$server ./
elif [ "$1" = "G" ]
then
    echo -e "${YELLOW}Greedy With Dirty-Read (AgtB = 1, AwtB = -1)${NC}"
    $remove
    cp $g/$client ./
    cp $g/$server ./
elif [ "$1" = "GR" ]
then
    echo -e "${YELLOW}Greedy With Dirty-Read (AgtB = 1, AwtB = -1) Reverse List${NC}"
    $remove
    cp $gr/$client ./
    cp $gr/$server ./
elif [ "$1" = "NG" ]
then
    echo -e "${YELLOW}New Greedy With Dirty-Read (AgtB = -1, AwtB = 1), Push Read-Only Txns The Tail${NC}"
    $remove
    cp $ng/$client ./
    cp $ng/$server ./
else
    echo -e "${YELLOW}No Such File${NC}"
fi