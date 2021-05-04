temp_dir="temp"
mkdir ${temp_dir}

exp_id="1"
jar_id="3"
n_exp=1
n_jar=1

for i in $( seq 1 $n_exp )
do
    exp_id=$i
    echo ${exp_id}

    # Name the pair of properties & jar
    pair="exp${exp_id}-jar${jar_id}"

    mkdir "${temp_dir}/${pair}"

    # Copy properties to server
    cp -r "./exps/exp${exp_id}" "./temp/${pair}/server/"
    # Copy properties client
    cp -r "./exps/exp${exp_id}" "./temp/${pair}/client/"

    # Copy server.jar
    cp -r "./jars/ver${jar_id}/server.jar" "./temp/${pair}/server/"
    # Copy client.jar
    cp -r "./jars/ver${jar_id}/client.jar" "./temp/${pair}/client/"
    # Copy readme.md
    cp -r "./jars/ver${jar_id}/readme.md" "./temp/${pair}/readme.md"

    # Copy client-load.sh
    cp -r "./utils/client-load.sh" "./temp/${pair}/client/client-load.sh"
    # Copy client-bench.sh
    cp -r "./utils/client-bench.sh" "./temp/${pair}/client/client-bench.sh"
    # Copy server.sh
    cp -r "./utils/server.sh" "./temp/${pair}/server/server.sh"

    # Copy run-client-load.sh
    cp -r "./utils/run-client-load.sh" "./temp/${pair}/run-client-load.sh"
    # Copy run-client-bench.sh
    cp -r "./utils/run-client-bench.sh" "./temp/${pair}/run-client-bench.sh"
    # Copy run-server-load.sh
    cp -r "./utils/run-server-load.sh" "./temp/${pair}/run-server-load.sh"
    # Copy run-server-bench.sh
    cp -r "./utils/run-server-bench.sh" "./temp/${pair}/run-server-bench.sh"


    echo ${pair}
    echo  "./exps/exp${exp_id}" 
    echo "./temp/${pair}"
done