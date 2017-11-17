#!/bin/bash

mkdir log
mkdir sol

mkdir log/2a
mkdir sol/2a

javac  -verbose $( find ./src/* -not -name "*Test.java" | grep .java ) -d out/
java -cp out edu.mst.grbcp5.hw02.Main $1 $2
