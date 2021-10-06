#!/bin/sh

aws dynamodb create-table \
    --table-name BankAccount \
    --attribute-definitions \
        AttributeName=accountNumber,AttributeType=S \
    --key-schema AttributeName=accountNumber,KeyType=HASH \
    --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1 \
    --endpoint-url http://localhost:8000

