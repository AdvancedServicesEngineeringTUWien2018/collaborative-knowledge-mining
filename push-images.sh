set -x

sh blockchainBridge/push-blockchain-bridge-image.sh
sh apigateway/push-apigateway-image.sh
sh exploiterManagement/push-exploiter-management-image.sh
sh costEstimator/push-cost-estimator-image.sh
sh exploratorySearch/push-exploratory-search.sh