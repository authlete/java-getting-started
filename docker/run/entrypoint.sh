#! /bin/bash

# Copy source to bind mount directory
cp -R /src/* /mount
cp -R /src/.git* /mount

# Start Tomcat
exec /opt/tomcat/bin/catalina.sh run