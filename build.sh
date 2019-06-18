#!/bin/bash

#Cleanup old server/build files
rm -r /usr/local/deploy-user/server 2>/dev/null
./gradlew clean

#Rebuild the project and extract it to home
./gradlew build -x RunClientTests
cp server/build/distributions/server.tar /usr/local/deploy-user/server.tar -v
tar xvf /usr/local/deploy-user/server.tar -C /usr/local/deploy-user/

#Get rid of the tar, and move 3601_run.sh executable into home
rm /usr/local/deploy-user/server.tar
cp .3601_run.sh /usr/local/deploy-user/3601.sh
chmod +x /usr/local/deploy-user/3601.sh
