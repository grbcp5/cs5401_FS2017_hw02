#!/bin/bash

mkdir log
mkdir sol

mkdir log/2b
mkdir sol/2b

javac  -verbose $( find ./src/* -not -name "*Test.java" | grep .java ) -d out/
java -cp out edu.mst.grbcp5.hw02.Main $1 $2
