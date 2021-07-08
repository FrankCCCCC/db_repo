RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m' # No Color

monitor="htop"

ssh_command="ssh -i C:\Users\user\.ssh\ming_db dbdbdb@140.113.216.170"

if [ "$1" = "1" ]
then
    echo -e "${GREEN}Node 1: DB0${NC}"
    $ssh_command -p 6666
elif [ "$1" = "2" ]
then
    echo -e "${GREEN}Node 2: DB1${NC}"
    $ssh_command -p 6667
elif [ "$1" = "3" ]
then
    echo -e "${GREEN}Node 3: Sequencer${NC}"
    $ssh_command -p 6668
elif [ "$1" = "4" ]
then
    echo -e "${GREEN}Node 4: Client${NC}"
    $ssh_command -p 5555
fi