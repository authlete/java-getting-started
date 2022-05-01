# Cross-platform section
FROM --platform=$BUILDPLATFORM rockylinux AS build

MAINTAINER pat.patterson@authlete.com

# Set this to latest Tomcat 9.0.x
ARG TOMCAT_VERSION=9.0.62

# Download and install Tomcat
RUN mkdir -p /out/opt/tomcat/
WORKDIR /out/opt/tomcat
RUN curl -O https://downloads.apache.org/tomcat/tomcat-9/v${TOMCAT_VERSION}/bin/apache-tomcat-${TOMCAT_VERSION}.tar.gz
RUN tar xvfz apache*.tar.gz
RUN mv apache-tomcat-${TOMCAT_VERSION}/* /out/opt/tomcat/.

# Platform-specific section
FROM rockylinux

ARG MODULE

# Install Java, Maven
RUN dnf -y install java-11-openjdk java-11-openjdk-devel maven git
RUN java -version

# Use JDK 11
ENV JAVA_HOME "/usr/lib/jvm/java-11"

COPY --from=build /out/opt /opt

# Copy the git repo
COPY .git /src/.git
COPY .gitignore /src/

# Restore from the git repo
WORKDIR /src
RUN git restore .

# Checkout tutorial starting point
RUN git checkout main

# Build and deploy the WAR file
WORKDIR /src/${MODULE}
RUN --mount=type=cache,target=/root/.m2 mvn clean package
RUN /usr/bin/cp target/${MODULE}.war /opt/tomcat/webapps/

# Copy the runtime scripts
COPY docker/run/* /run/
RUN chmod +x /run/*

EXPOSE 8080

CMD ["/run/entrypoint.sh"]
