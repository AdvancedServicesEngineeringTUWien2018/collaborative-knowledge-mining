#!/bin/sh
set -x
(cd apigateway && mvn clean install dockerfile:build -DskipTests=true)
(cd costEstimator && mvn clean install dockerfile:build -DskipTests=true)
(cd blockhainBridge && mvn clean install dockerfile:build -DskipTests=true)
(cd exploiterManagement && mvn clean install dockerfile:build -DskipTests=true)
(cd exploratorySearch && sh build-exploratory-search-image.sh)

