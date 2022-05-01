#! /bin/bash

# Stop script if a command fails
set -e

# Default image prefix
image="us-docker.pkg.dev/authlete/demo/java-getting-started"

# Default to no push
push=""

usage() {
  echo "Usage: $0 [ -i image_prefix ] [ -p ]\n" 1>&2
  echo "Options" 1>&2
  echo "      -i image_prefix   Set image prefix. Defaults to ${image}" 1>&2
  echo "      -p                Push a multiplatform image to the registry." 1>&2
  echo "                        If omitted, the script will default to the local platform and " 1>&2
  echo "                        load the image into the local Docker image repository." 1>&2
}

exit_abnormal() {
  usage
  exit 1
}

while getopts ":i:p" opt; do
  case $opt in
    i) image="$OPTARG"
    ;;
    p) push="yes"
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
  if [ -z "$push" ]; then
    # Build default platform and load into local Docker image repository
    docker buildx build -t "${image}-${module}" --build-arg "MODULE=${module}" --load .
  else
    # Build multiplatform image and push to Google Container Registry
    docker buildx build --platform=linux/amd64,linux/arm64/v8 -t "${image}-${module}" --build-arg "MODULE=${module}" --push .
  fi
done