#! /bin/bash

# Stop script if a command fails
set -e

for module in ecommerce loyalty
do
  cd /mount/${module}
  mvn clean package
  # By default, cp is aliased to cp -i, so we have to supply the path!
  /usr/bin/cp target/${module}.war /opt/tomcat/webapps/
done
