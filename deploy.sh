#!/usr/bin/env bash

# pull latest image
docker-compose -f /home/ubuntu/docker/docker-compose1.yml pull

# run jdk(back) container
cat /home/ubuntu/docker/password.txt | docker login -u cocktailpick --password-stdin

docker-compose -f /home/ubuntu/docker/docker-compose1.yml up -d jdk

# pause for prevent memory overload
sleep 5

# run nginx(front) container

cat /home/ubuntu/docker/password.txt | docker login -u 6drinkers --password-stdin

docker-compose -f /home/ubuntu/docker/docker-compose1.yml up -d nginx
