#!/usr/bin/env bash

# delete outdated back container
docker stop $(docker container ps -a -q --filter name=jdk)

docker rm $(docker container ps -a -q --filter name=jdk)

docker rmi $(docker images --filter=reference='back:*' -qa)

docker build --tag back /home/ubuntu/docker/jdk

docker run -d --name jdk back

sleep 2

docker-compose-restart nginx
