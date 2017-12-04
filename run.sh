#!/bin/bash

mkdir -p log
mkdir -p sol

mkdir -p log/2c
mkdir -p sol/2c

javac -verbose $( find ./src/* -not -name "*Test.java" | grep .java ) -d out/
java -cp out edu.mst.grbcp5.hw02.Main $1 $2
