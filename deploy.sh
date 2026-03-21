#!/bin/bash

set -e

export JAVA_HOME="/c/Users/User/.jdks/corretto-21.0.9"
export PATH="$JAVA_HOME/bin:$PATH"

mvn clean package

docker-compose down

docker-compose up --build