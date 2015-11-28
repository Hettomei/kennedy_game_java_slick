#!/bin/bash

echo "Compiling"
mkdir build
mkdir build/screenshots
cp -r sprites build/
javac -cp "./lib/*:." principal/StartGame.java -d build -encoding UTF8

echo ""
echo "run"
cd build
java -cp "../lib/*:." -Djava.library.path=../lib principal.StartGame
