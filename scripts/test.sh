#!/usr/bin/env bash
echo "Test script"
scp ${TRAVIS_BUILD_DIR}/server/build/distributions/server.tar ${DEPLOY_USER}@${MORRIS_HOST}:~
ssh ${DEPLOY_USER}@{MORRIS_HOST}
scp ~/server.tar ${DEPLOY_HOST}:~
rm ~/server.tar
ssh ${DEPLOY_HOST}
ls
