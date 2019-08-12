#!/usr/bin/env bash
echo "Test script"
scp ${TRAVIS_BUILD_DIR}/server/build/distributions/server.tar ${DEPLOY_USER}@${MORRIS_HOST}:~
ssh -tt ${DEPLOY_USER}@${MORRIS_HOST} <<-EOF
    scp ~/server.tar ${DEPLOY_HOST}:~
    rm ~/server.tar
    ssh -tt ${DEPLOY_HOST}
    ls
    tar -xvf ~/server.tar .
EOF
