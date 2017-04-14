#!/bin/bash

# Clean com.apollo.thriftgen
rm -r $PWD/src/main/java/com/apollo/thriftgen
rm $PWD/src/main/node/*
rm -r $PWD/src/test/java/com/apollo/thriftgen

# Generate new java thrift files
THRIFT_FILES=$PWD/src/main/thrift/*
for f in $THRIFT_FILES
do
    thrift --gen java -out src/main/java/ $f
    thrift --gen java -out src/test/java/ $f
    thrift --gen js:node -out src/main/node/ $f
done
