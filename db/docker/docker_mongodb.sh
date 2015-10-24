#!/bin/sh


# install docker first 
# https://docs.docker.com/installation/

#repo: https://github.com/docker-library/docs/tree/master/mongo

#runs mongodb on port: 27017 (EXPOSE 27017)
sudo docker run --name sweng15-db -d -p 27017:27017 mongo 
