#! /bin/bash

# Stop script if a command fails
set -e

cd /src
mvn clean package

# By default, cp is aliased to cp -i, so we have to supply the path!
/usr/bin/cp target/ecommerce.war /opt/tomcat/webapps/
