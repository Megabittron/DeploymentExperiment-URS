#!/usr/bin/env bash
echo "Test script"
scp -C ${TRAVIS_BUILD_DIR}/server/build/distributions/server.tar ${DEPLOY_USER}@${DEPLOY_HOST}:~

