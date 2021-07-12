DB_DIR="C:\Users\user\HiDB"
echo "Removing $DB_DIR"
rm -r $DB_DIR
echo "Coping $DB_DIR-backup-$1 to $DB_DIR"
cp -r $DB_DIR-backup-$1 $DB_DIR