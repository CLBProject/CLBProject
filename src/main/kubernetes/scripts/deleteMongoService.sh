#!/bin/sh
##
# Script to remove/undepoy all project resources from the local Minikube environment.
##

# Delete mongod stateful set + mongodb service + secrets + host vm configuer daemonset
# kubectl delete ingress apprun-ingress
kubectl delete svc/mongo
kubectl delete statefulsets/mongod-service
kubectl delete secret shared-bootstrap-data
sleep 3

# Delete persistent volume claims
kubectl delete persistentvolumeclaims -l role=mongo