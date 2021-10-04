#!/bin/sh

curl -v -d '{"firstName": "Bozo", "lastName": "Clown"}' -H 'Content-Type: application/json' -X POST http://localhost:8080/banking/v1/accounts
echo "\n"
