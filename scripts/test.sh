#!/usr/bin/env bash
# This file needs to use indents and not spaces to work properly
echo "Test script"
scp ${TRAVIS_BUILD_DIR}/server/build/distributions/server.tar ${DEPLOY_USER}@${MORRIS_HOST}:~
ssh -tt ${DEPLOY_USER}@${MORRIS_HOST} <<-EOF
	scp ~/server.tar ${DEPLOY_HOST}:~
	rm ~/server.tar
	ssh -tt ${DEPLOY_HOST} <<END
		tar -xvf ~/server.tar
		rm ~/server.tar
		ls
	exit
	END
	exit
EOF
