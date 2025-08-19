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

echo "attacker name"
curl -s -X 'GET' \
  "${HOST}/game/${GAME_ID}" \
  -H 'accept: */*' | jq .attacker.name

echo "defender name"
curl -s -X 'GET' \
  "${HOST}/game/${GAME_ID}" \
  -H 'accept: */*' | jq .defender.name

ATTACKER=$(curl -s -X 'GET' \
             "${HOST}/game/${GAME_ID}" \
             -H 'accept: */*' | jq --raw-output .attacker.name)

DEFENDER=$(curl -s -X 'GET' \
             "${HOST}/game/${GAME_ID}" \
             -H 'accept: */*' | jq --raw-output .defender.name)




ATTACKER_CARD=$(curl -s -X 'GET' \
  "${HOST}/game/${GAME_ID}" \
  -H 'accept: */*' | jq .attacker.hand.hand[0])

  curl -X 'POST' \
    "${HOST}/game/${GAME_ID}/attack?name=${ATTACKER}" \
    -H 'accept: */*' \
    -H 'Content-Type: application/json' \
    -d "${ATTACKER_CARD}" |jq




echo ""
echo ""
echo "print all games ids"
curl -s -X 'GET' \
  "${HOST}/game" \
  -H 'accept: */*' | jq