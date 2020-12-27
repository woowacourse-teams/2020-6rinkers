#!/usr/bin/env bash

cd ..

docker rm $(docker container ps -a -q --filter ancestor=back)

docker run -d --name jdk back