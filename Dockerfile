ARG PLATFORM=amd64
FROM ${PLATFORM}/rockylinux

ARG MODULE

MAINTAINER pat.patterson@authlete.com

# Set this to latest Tomcat 9.0.x
ARG TOMCAT_VERSION=9.0.62

# Download and install Tomcat
RUN mkdir /opt/tomcat/
WORKDIR /opt/tomcat
RUN curl -O https://downloads.apache.org/tomcat/tomcat-9/v${TOMCAT_VERSION}/bin/apache-tomcat-${TOMCAT_VERSION}.tar.gz
RUN tar xvfz apache*.tar.gz
RUN mv apache-tomcat-${TOMCAT_VERSION}/* /opt/tomcat/.

# Install Java, Maven
RUN dnf -y install java-11-openjdk java-11-openjdk-devel maven git
RUN java -version

# Use JDK 11
ENV JAVA_HOME "/usr/lib/jvm/java-11"

# Copy the git repo
COPY .git /src/.git
COPY .gitignore /src/

# Restore from the git repo
WORKDIR /src
RUN git restore ${MODULE}

# Checkout tutorial starting point
RUN git checkout main

# Build and deploy the WAR file
WORKDIR /src/${MODULE}
RUN mvn clean package
RUN /usr/bin/cp target/${MODULE}.war /opt/tomcat/webapps/

# Copy the runtime scripts
COPY docker/run/* /run/
RUN chmod +x /run/*

EXPOSE 8080

CMD ["/run/entrypoint.sh"]
