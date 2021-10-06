#!/bin/sh

java -jar ../target/bank-account-standalone.jar adjustAccount $1 -10.00 EUR
