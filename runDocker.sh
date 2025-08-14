#!/bin/bash

IMAGE_NAME=durok:latest
docker run --detach --name durokServer -p 8080:8080 $IMAGE_NAME


#sanity
#curl http://localhost:8080/version