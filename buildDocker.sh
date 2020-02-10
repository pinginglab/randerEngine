#!/bin/bash
docker pull openjdk:8-jre-alpine
./gradlew clean
./gradlew bootWar -Pprod buildDocker
docker tag rander-engine:1.0 ccr.ccs.tencentyun.com/pingsec/compose:rander-engine:1.0
docker push ccr.ccs.tencentyun.com/pingsec/compose:rander-engine:1.0
