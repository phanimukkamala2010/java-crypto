#!/usr/bin/bash

MD_HOME=$HOME/github/java-crypto/market-data
JAVAHOME=/usr/bin

CLASS_PATH=$MD_HOME/build
for jar in `ls $MD_HOME/jars/*.jar`
do
    CLASS_PATH=$CLASS_PATH:$jar
done
echo $CLASS_PATH

CLASS=testapp.TestApp
echo $CLASS

$JAVAHOME/java -cp $CLASS_PATH $CLASS
