#!/bin/sh
# Push the latest govstat image
set -x
gcloud docker -- push gcr.io/rugged-alloy-205510/cost-estimator
