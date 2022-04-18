#! /bin/bash

for module in ecommerce loyalty
  for arch in amd64 arm64v8

docker push us-docker.pkg.dev/authlete/demo/java-getting-started:arm64v8
docker push us-docker.pkg.dev/authlete/demo/java-getting-started:amd64

docker manifest rm us-docker.pkg.dev/authlete/demo/java-getting-started:latest
docker manifest create us-docker.pkg.dev/authlete/demo/java-getting-started:latest \
us-docker.pkg.dev/authlete/demo/java-getting-started:amd64 \
us-docker.pkg.dev/authlete/demo/java-getting-started:arm64v8

docker manifest push us-docker.pkg.dev/authlete/demo/java-getting-started:latest