#!/usr/bin/env bash
# This file needs to use tabs and not spaces to work properly

scp ${TRAVIS_BUILD_DIR}/server/build/distributions/server.tar ${MORRIS_USER}@${MORRIS_HOST}:~
ssh -tt ${MORRIS_USER}@${MORRIS_HOST} <<-EOF
	scp ~/server.tar deploy-user@${DEPLOY_HOST}:~
	rm ~/server.tar
	ssh -tt deploy-user@${DEPLOY_HOST} <<END
		rm -r ~/server
		tar -xf ~/server.tar
		rm ~/server.tar
		sudo rm -r /swadm/hosts/urs/www/*
		sudo cp -r /home/deploy-user/server/lib/client/public/* /swadm/hosts/urs/www/
		sudo systemctl restart urs.service &
		exit
	END
	exit
EOF
