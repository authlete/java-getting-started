#! /bin/bash

# Stop script if a command fails
set -e

usage() {
  echo "Usage: $0 [ -a architecture ] [ -i image_prefix ] [ -n instance_name ] [ -p host_port ] [ -s host_source_directory ] [ -v volume_name ]" 1>&2
}

exit_abnormal() {
  usage
  exit 1
}

# Default architecture to match machine reported by uname -m
machine=$(uname -m)
if [ "${machine}" == "arm64" ]; then
  # M1 Mac
  arch="arm64/v8"
elif [ "${machine}" == "amd64" ]; then
  # Intel
  arch="amd64"
else
  echo "Unrecognised machine: ${machine}"
  exit 1
fi

# Default instance name
name="authlete-getting-started"

# Default host port
port=8080

# Default source directory
srcdir="$(pwd)/src"

# Default volume name
volume="authlete-src"

# Default image prefix
image="us-docker.pkg.dev/authlete/demo/java-getting-started"

while getopts ":a:i:n:p:s:" opt; do
  case $opt in
    a) arch="$OPTARG"
    ;;
    i) image="$OPTARG"
    ;;
    n) name="$OPTARG"
    ;;
    p) port="$OPTARG"
    ;;
    s) srcdir="$OPTARG"
    ;;
    :) echo "Error: -${OPTARG} requires an argument." >&2
    exit_abnormal
    ;;
    \?) echo "Error: Invalid option -$OPTARG" >&2
    exit_abnormal
    ;;
  esac
done

# E-Commerce instance
docker run -d \
  --platform "linux/${arch}" \
  --name "${name}-ecommerce" \
  --publish "${port}":8080 \
  "${image}-ecommerce"

# Loyalty instance - use bind mount - host src directory is mounted at /mount in the container
((port++))
mkdir -p ${srcdir}
docker run -d \
  --platform linux/"${arch}" \
  --name "${name}-loyalty" \
  --publish "${port}":8080 \
  --mount type=bind,source="${srcdir}",target=/mount \
  "${image}-loyalty"

