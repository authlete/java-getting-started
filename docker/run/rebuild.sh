#! /bin/bash

# Stop script if a command fails
set -e

cd /mount/loyalty
mvn clean package
# By default, cp is aliased to cp -i, so we have to supply the path!
/usr/bin/cp target/loyalty.war /opt/tomcat/webapps/
