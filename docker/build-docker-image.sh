#! /bin/bash

# Stop script if a command fails
set -e

usage() {
  echo "Usage: $0 [ -a architecture ] [ -i image_name ]" 1>&2
}

exit_abnormal() {
  usage
  exit 1
}

# Default architecture - pass arm64v8 for M1 Mac
arch=amd64

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

if [ "${image}" == "" ]; then
  image="us-docker.pkg.dev/authlete/demo/java-getting-started:${arch}"
fi

docker build --platform "linux/${arch}" -t "${image}" --build-arg "PLATFORM=${arch}" .