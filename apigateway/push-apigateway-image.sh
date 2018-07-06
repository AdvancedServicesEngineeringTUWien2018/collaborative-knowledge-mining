#!/bin/sh
# Push the latest apigateway image
set -x
gcloud docker -- push gcr.io/rugged-alloy-205510/apigateway
