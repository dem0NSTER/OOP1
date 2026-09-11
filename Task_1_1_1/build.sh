#!/bin/bash

mkdir -p build/java build/javadoc

javac -d build/java src/main/java/org/example/*.java
javadoc -d build/javadoc src/main/java/org/example/*.java
jar cfe build/task-1-1-1.jar org.example.Main -C build/java .
java -jar build/task-1-1-1.jar
