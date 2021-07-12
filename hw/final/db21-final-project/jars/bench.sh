RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m' # No Color

move_dir="cd /home/dbdbdb/share-data/db21-final-project/jars"
reset_db="bash reset-db.sh"
kill_port="bash killbyport.sh"
server="bash server.sh"
client="bash client.sh"
exit="exit"


rm db1.txt

# ssh_command="ssh -i C:\Users\user\.ssh\ming_db dbdbdb@140.113.216.170"

if [ "$1" = "1" ]
then
    echo -e "${GREEN}Node 1: DB0${NC}"
    ${move_dir}
    rm db0.txt 
    ${reset_db}
    ${kill_port} 
    ${server} db0 0 0 >> db0.txt
elif [ "$1" = "2" ]
then
    echo -e "${GREEN}Node 2: DB1${NC}"
    ${move_dir}
    ${reset_db}
    ${kill_port}
    ${server} db1 1 0 >> db1.txt
elif [ "$1" = "3" ]
then
    echo -e "${GREEN}Node 3: Sequencer${NC}"
    ${move_dir}
    ${kill_port}
    ${server} dbseq 2 1
elif [ "$1" = "4" ]
then
    echo -e "${GREEN}Node 4: Benchmark Client${NC}"
    ${move_dir}
    ${kill_port}
    ${client} 0 2
elif [ "$1" = "5" ]
then
    echo -e "${GREEN}Node 4: Load Client${NC}"
    ${move_dir}
    ${kill_port}
    ${client} 0 1
fi

