#!/bin/bash

# Stop script if a command fails
set -e

function check_apps {
  RETRIES=30

  for (( i=0; i<${#modules[@]}; i++ )); do
    # Apps can take a few seconds to redeploy, so retry a few times
    echo -n "Looking for \"${headers[$i]}\" at http:/localhost:${host_port}/${modules[$i]}"
    for (( j=0; j<${RETRIES}; j++ )); do
      response=$(curl -s -L --connect-timeout 5 \
                     --max-time 10 \
                     --retry 10 \
                     --retry-all-errors \
                     --retry-delay 1 \
                     --retry-max-time 60 \
                     http://localhost:"${host_port}"/"${modules[$i]}")

      if [[ "${response}" == *"${headers[$i]}"* ]]; then
        break
      fi

      sleep 1
      echo -n "."
    done
    echo

    if [[ ${j} == ${RETRIES} ]]; then
      echo "Can't see ${modules[$i]} app"
      exit 1
    fi

    echo "${modules[$i]} app is up"
  done
}

script_dir=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

# Set architecture to match machine reported by uname -m
machine=$(uname -m)
if [ "${machine}" == "arm64" ]; then
  # M1 Mac
  arch=arm64/v8
elif [ "${machine}" == "amd64" ]; then
  # Intel
  arch=amd64
else
  echo "Unrecognised machine: ${machine}"
  exit 1
fi

# Find an open port - from https://unix.stackexchange.com/a/358101/3721
host_port=$(netstat -aln | awk '
  $6 == "LISTEN" {
    if ($4 ~ "[.:][0-9]+$") {
      split($4, a, /[:.]/);
      port = a[length(a)];
      p[port] = 1
    }
  }
  END {
    for (i = 3000; i < 65000 && p[i]; i++){};
    if (i == 65000) {exit 1};
    print i
  }
')

# 6 random hex characters
rand="$(openssl rand -hex 3)"

image=test-image-${rand}
instance=test-instance-${rand}
srcdir="$(pwd)"/src-${rand}

echo "Building Docker image ${image} for ${arch}"

"${script_dir}"/build-docker-image.sh -a "${arch}" -i "${image}"

mkdir "${srcdir}"

echo "Running Docker image ${image}, listening on ${host_port}"

"${script_dir}"/run-docker-instance.sh -a "${arch}" -i "${image}" -n "${instance}" -p "${host_port}" -s "${srcdir}" > /dev/null

echo "Docker instance ${instance} started"

# Are the apps up?

echo "Checking apps on localhost port ${host_port}"

# No associative arrays in bash on the Mac, so use two regular arrays
modules=("ecommerce" "loyalty")
headers=("eCommerce Application" "Loyalty Program")
check_apps

# Change the app headers and rebuild
echo "Editing the application source"
sed -i '' 's/eCommerce Application/XXXXX/' "${srcdir}/ecommerce/src/main/webapp/index.jsp"
sed -i '' 's/Loyalty Program/YYYYY/' "${srcdir}/loyalty/src/main/webapp/index.html"

echo "Rebuilding"
docker exec -it "${instance}" /run/rebuild.sh

echo "Rechecking apps on localhost port ${host_port}"
headers=("XXXXX" "YYYYY")
check_apps

echo "Cleaning up"

docker rm -f "${instance}" > /dev/null

docker image rm "${image}" > /dev/null

rm -r "${srcdir}"

echo "Tests complete!"