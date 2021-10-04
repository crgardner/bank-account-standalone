#!/bin/sh

curl -v http://localhost:8080/banking/v1/accounts/$1/statement
echo "\n"
