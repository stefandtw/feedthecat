#!/bin/sh
./build-noodle.sh clean
./build-noodle.sh war
javac -classpath ../lib/servlet.jar:../lib/httpclient.jar:../lib/jsoup-1.7.2.jar -d ../bin/classes -s ../bin/classes `find ../src -type f -name "*.java" |paste -sd " "`
rm -rf ../noodle.war ../noodle-war
./build-noodle.sh war
cd ../noodle-war && zip -r ../noodle.war WEB-INF && cd ../build
mvn install:install-file -Dfile=../noodle.war -DgroupId=feedthecat -DartifactId=noodle -Dpackaging=war -Dversion=0.1

