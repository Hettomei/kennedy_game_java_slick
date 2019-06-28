#!/bin/bash

echo "Download lwjgl libs"
rm -rf /tmp/lwjgl-2.9.3
curl "https://netix.dl.sourceforge.net/project/java-game-lib/Official%20Releases/LWJGL%202.9.3/lwjgl-2.9.3.zip" --output /tmp/lwjgl-2.9.3.zip
unzip /tmp/lwjgl-2.9.3.zip -d /tmp
cp /tmp/lwjgl-2.9.3/jar/* ./lib/
# Please change linux with other platform
cp /tmp/lwjgl-2.9.3/native/linux/* ./lib/

echo "Compiling"
mkdir -p build/screenshots
cp -r sprites build/
javac -cp "./lib/*:." principal/StartGame.java -d build -encoding UTF8

echo ""
echo "run"
cd build
java -cp "../lib/*:." -Djava.library.path=../lib principal.StartGame
