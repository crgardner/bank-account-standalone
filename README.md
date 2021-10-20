# bank-account-standalone

bank-account-standalone is a Java 17 graalvm console application used as an example of the Clean Architecture. The application allows you to:

* Open accounts
* Adjust accounts with deposits and withdrawals
* Prepare statements

It uses a separately running instance of DynamoDB local to store data.

## Installation

### AWS

#### AWS CLI

[Install AWS CLI](https://docs.aws.amazon.com/cli/latest/userguide/install-cliv2.html) for your platform.

#### DynamoDB
1. [Setup Amazon DynamoDB locally](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.DownloadingAndRunning.html).
2. Start DynamoDB with the following command:

```bash
$ java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar -sharedDb
```

### Application
1. Install JDK version 16. [SDKMAN!](https://sdkman.io/) makes it quite easy.
2. Ensure a recent version of Maven is installed. SDKMAN! can help with that too.
3. Clone this repo.
4. Create the BankAccount table. In the script directory of the root of this clone, run the following:

```bash
$ ./create-account-table.sh
```
5. In the root directory of the clone, run the following:

```bash
$ mvn clean package
```

## Usage
* After a successful installation, in the target directory of the root, execute the following:

```bash
$ java -jar bank-account-standalone.jar
```

* Alternatively, start the application in your IDE by creating a run configuration for the com.crg.learn.account.application.start.Main Java file. There are no start up options.

After the application is started, navigate to the script directory and run the following scripts:

```bash

$ ./open_account.sh

$ ./deposit_amount.sh <account number from open_account.sh> 

$ ./withdraw_amount.sh <account number from open_account.sh> 

$ ./prepare_statement.sh <account number from open_account.sh>

```
Note these scripts hardcode the currency code "EUR," as the application currently supports only Euros.
Also, if you are running behind a proxy, you may need to unset all your proxy-related environment variables in the shell.
We observed connectivity problems to the local DynamoDB with those variables set.

