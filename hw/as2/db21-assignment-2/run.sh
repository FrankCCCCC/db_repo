# !/bin/bash

AdoptOpenJDK\jdk-11.0.10.9-hotspot\bin\java.exe '-Djava.util.logging.config.file=target/classes/java/util/logging/logging.properties' '-Dorg.vanilladb.bench.config.file=target/classes/org/vanilladb/bench/vanillabench.properties' '-Dorg.vanilladb.core.config.file=target/classes/org/vanilladb/core/vanilladb.properties' "-javaagent:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2020.3.3\lib\idea_rt.jar=62725:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2020.3.3\bin" '-Dfile.encoding=UTF-8' '-classpath D:\data_\database\hw\as2\coop\db21-assignment-2\bench\target\classes;D:\data_\database\hw\as2\coop\db21-assignment-2\core-patch\target\classes;C:\Users\quant\.m2\repository\net\smacke\jaydio\0.1\jaydio-0.1.jar;C:\Users\quant\.m2\repository\net\java\dev\jna\jna\4.0.0\jna-4.0.0.jar;C:\Users\quant\.m2\repository\log4j\log4j\1.2.17\log4j-1.2.17.jar;C:\Users\quant\.m2\repository\org\vanilladb\core\0.4.1-course-20210322\core-0.4.1-course-20210322.jar' org.vanilladb.bench.server.StartUp BenchDB
java '-Djava.util.logging.config.file=target/classes/java/util/logging/logging.properties' '-Dorg.vanilladb.bench.config.file=target/classes/org/vanilladb/bench/vanillabench.properties' '-Dorg.vanilladb.core.config.file=target/classes/org/vanilladb/core/vanilladb.properties' '-Dfile.encoding=UTF-8' '-classpath D:\data_\database\hw\as2\coop\db21-assignment-2\bench\target\classes;D:\data_\database\hw\as2\coop\db21-assignment-2\core-patch\target\classes' 'org.vanilladb.bench.server.StartUp' 'BenchDB'

java '-Djava.util.logging.config.file=target/classes/java/util/logging/logging.properties;-Dorg.vanilladb.bench.config.file=target/classes/org/vanilladb/bench/vanillabench.properties;-Dorg.vanilladb.core.config.file=target/classes/org/vanilladb/core/vanilladb.properties;' '-Dfile.encoding=UTF-8' -classpath 'D:\data_\database\hw\as2\coop\db21-assignment-2\bench\target\classes;D:\data_\database\hw\as2\coop\db21-assignment-2\core-patch\target\classes' org.vanilladb.bench.server.StartUp BenchDB