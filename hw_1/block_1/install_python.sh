#!/bin/bash

NODES="namenode nodemanager2 nodemanager3"

for node in $NODES; do
	sudo docker exec -it $node apt update -y
	sudo docker exec -it $node apt install python3 -y
done