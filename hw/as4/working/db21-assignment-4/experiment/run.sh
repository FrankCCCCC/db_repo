#java -Djava.util.logging.config.file=logging.properties -Dorg.vanilladb.bench.config.file=vanillabench.properties -Dorg.vanilladb.core.config.file=vanilladb.properties -jar server.jar HaHaDB

mkdir temp

exp_id="1"
jar_id="1"
n_exp=3
n_jar=3

for i in $( seq 1 $n_exp )
do
    exp_id=$i
    echo ${exp_id}

    # Name the pair of properties & jar
    pair="exp${exp_id}-jar${jar_id}"

    # Copy properties
    cp -r "./exps/exp${exp_id}" "./temp/${pair}"
    # Copy server.jar
    cp -r "./jars/ver${jar_id}/server.jar" "./temp/${pair}/server/"
    # Copy client.jar
    cp -r "./jars/ver${jar_id}/client.jar" "./temp/${pair}/client/"

    # Copy client-load.sh
    cp -r "./utils/client-load.sh" "./temp/${pair}/client/client-load.sh"
    # Copy client-bench.sh
    cp -r "./utils/client-bench.sh" "./temp/${pair}/client/client-bench.sh"
    # Copy server.sh
    cp -r "./utils/server.sh" "./temp/${pair}/server/server.sh"

    # Copy run-client.sh
    cp -r "./utils/run-client.sh" "./temp/${pair}/run-client.sh"
    # Copy run-server.sh
    cp -r "./utils/run-server.sh" "./temp/${pair}/run-server.sh"

    echo ${pair}
    echo  "./exps/exp${exp_id}" 
    echo "./temp/${pair}"
done