#!/usr/bin/env bash

# delete outdated back container
docker stop $(docker container ps -a -q --filter name=jdk)

docker rm $(docker container ps -a -q --filter name=jdk)

docker rmi $(docker images --filter=reference='back:*' -qa)

# build back image
docker build --tag back /home/ubuntu/docker/jdk

# run jdk(back) container
docker-compose -f /home/ubuntu/docker/docker-compose.yml up -d jdk

# pause for prevent memory overload
sleep 5

# restart nginx(front) container
docker-compose -f /home/ubuntu/docker/docker-compose.yml restart nginx
