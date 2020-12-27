#!/usr/bin/env bash

cd ..

docker container stop $(docker container ps -a -q --filter ancestor=back)

docker run -d back