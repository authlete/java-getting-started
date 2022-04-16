#! /bin/bash

# Stop script if a command fails
set -e

usage() {
  echo "Usage: $0 [ -a architecture ] [ -i image_prefix ]" 1>&2
}

exit_abnormal() {
  usage
  exit 1
}

# Default architecture - pass arm64v8 for M1 Mac
arch="amd64"

# Default image prefix
image="us-docker.pkg.dev/authlete/demo/java-getting-started"

while getopts ":a:i:" opt; do
  case $opt in
    a) arch="$OPTARG"
    ;;
    i) image="$OPTARG"
    ;;
    :) echo "Error: -${OPTARG} requires an argument." >&2
    exit_abnormal
    ;;
    \?) echo "Error: Invalid option -$OPTARG" >&2
    exit_abnormal
    ;;
  esac
done

for module in ecommerce loyalty
do
  docker build --platform "linux/${arch}" -t "${image}-${module}" --build-arg "PLATFORM=${arch}" --build-arg "MODULE=${module}" .
done