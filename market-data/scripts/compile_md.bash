#!/usr/bin/bash

MD_HOME=$HOME/github/java-crypto/market-data
JAVAHOME=/usr/bin

#rm -rf $MD_HOME/build
mkdir -p $MD_HOME/build

CLASS_PATH=$MD_HOME/build
for jar in `ls $MD_HOME/jars/*.jar`
do
    CLASS_PATH=$CLASS_PATH:$jar
done
echo $CLASS_PATH

$JAVAHOME/javac -cp $CLASS_PATH -d $MD_HOME/build $MD_HOME/src/*.java

cd $MD_HOME/build
jar cvf $MD_HOME/jars/market-data.jar *
cd -


