#!/bin/sh
./feedthecat_compile.sh
cd ../../feedthecat
mvn package -DskipTests
java -jar target/FeedTheCat-standalone.jar
