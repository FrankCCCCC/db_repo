# Run Bench
DB_DIR="C:\Users\user\HiDB"
rm -r $DB_DIR
cp -r $DB_DIR-backup $DB_DIR

cd ./server
sh server.sh