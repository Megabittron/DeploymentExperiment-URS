#!/bin/bash

#Cleanup old Server/build files
rm -r ~/Server 2>/dev/null
./gradlew clean

#Rebuild the project and extract it to home
./gradlew build -x RunClientTests
cp Server/build/distributions/Server.tar ~/Server.tar -v
tar xvf ~/Server.tar -C ~

#Get rid of the tar, and move 3601_run.sh executable into home
rm ~/Server.tar
cp .3601_run.sh ~/3601.sh
chmod +x ~/3601.sh
