#!/bin/sh

curl -v -d '{"amount": "-10.00", "currency": "EUR"}' -H 'Content-Type: application/json' -X POST http://localhost:8080/banking/v1/accounts/$1/adjustments
echo "\n"
