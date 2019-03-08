#!/bin/bash
sudo docker container create --mount "type=bind,src=/home/squedgy/jungle/github/query-construction,dst=/project,bind-propagation=private" --name testing-query -a STDOUT -i --rm query-construction:release /bin/sh
