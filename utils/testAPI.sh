#!/bin/bash

# depends on curl and jq

# todo ensure server is running.

HOST="http://localhost:8080"
HOST_PLAYER=Steven

GAME_ID=$(curl -X 'POST' \
  "${HOST}/game?host=${HOST_PLAYER}" \
  -H 'accept: */*' \
  -d '' | jq --raw-output .id)

echo "${GAME_ID}"

curl -X 'GET' \
  "${HOST}/game/${GAME_ID}" \
  -H 'accept: */*' | jq

echo "Join players"
for name in Beth Mary Jim; do
  curl -X 'POST' -q 'POST' \
    "${HOST}/game/${GAME_ID}/join?name=${name}" \
    -H 'accept: */*' \
    -d '' | jq
done
printf "Done Join players \\n"


echo "Start Game"
curl -X 'POST' \
  "${HOST}/game/${GAME_ID}/start?hostName=${HOST_PLAYER}" \
  -H 'accept: */*' \
  -d ''
echo "Done Start Game"

echo "Print Game"
curl -X 'GET' \
  "${HOST}/game/${GAME_ID}" \
  -H 'accept: */*' | jq


echo "print all games ids"
curl -X 'GET' \
  "${HOST}/game" \
  -H 'accept: */*' | jq