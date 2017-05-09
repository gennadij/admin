#!/bin/bash

cd /home/gennadi/development/projects/admin/docu/db_schmas/$1

/home/gennadi/development/tools/orientdb/bin/console.sh db_schema.txt
