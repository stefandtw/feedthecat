#!/bin/sh

# -----------------------------------------------------------
# The targets are the different build scripts.
# The default "jar" is suggested
# and does not require any external packages
# 
# "compile"           target builds Turbine core classes
# "clean"          target #oves bin directory
# "jar"           target builds "core" + jar file
# "javadocs"        target builds the javadocs
# -----------------------------------------------------------
TARGET=${1}
# TARGET=javadocs
# TARGET=compile
# TARGET=clean
# TARGET=jar

#-------------------------------------------------------------------
# Define the paths to each of the packages
#-------------------------------------------------------------------
JSDK=/classes/JSDK2.0/lib/jsdk.jar
HTTPCLIENT=../lib/httpclient.jar

#--------------------------------------------
# No need to edit anything past here
#--------------------------------------------
if test -z "${JAVA_HOME}" ; then
    echo "ERROR: JAVA_HOME not found in your environment."
    echo "Please, set the JAVA_HOME variable in your environment to match the"
    echo "location of the Java Virtual Machine you want to use."
    exit
fi

if test -z "${TARGET}" ; then 
TARGET=jar
fi

if test -f ${JAVA_HOME}/lib/tools.jar ; then
    CLASSPATH=${CLASSPATH}:${JAVA_HOME}/lib/tools.jar
fi

if test -z "${JIKES}" ; then
    JAVAC=classic
else
    JAVAC=jikes
fi

echo JAVAC = ${JAVAC}

echo "Now building ${TARGET}..."

CP=${CLASSPATH}:${JSDK}:${HTTPCLIENT}:xml.jar:ant.jar

BUILDFILE=build.xml

${JAVA_HOME}/bin/java -classpath ${CP} -DJAVAC=${JAVAC} org.apache.tools.ant.Main -buildfile ${BUILDFILE} ${TARGET}
