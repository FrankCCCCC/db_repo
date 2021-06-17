pidwithpname=$(netstat -nlp | grep 42961  | awk '{print $7}')
pid=$(echo $pidwithpname | cut -d'/' -f1)
kill -9 $pid

pidwithpname=$(netstat -nlp | grep 42962  | awk '{print $7}')
pid=$(echo $pidwithpname | cut -d'/' -f1)
kill -9 $pid

pidwithpname=$(netstat -nlp | grep 42963  | awk '{print $7}')
pid=$(echo $pidwithpname | cut -d'/' -f1)
kill -9 $pid

pidwithpname=$(netstat -nlp | grep 30000  | awk '{print $7}')
pid=$(echo $pidwithpname | cut -d'/' -f1)
kill -9 $pid
