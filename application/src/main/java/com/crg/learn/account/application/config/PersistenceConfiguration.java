package com.crg.learn.account.application.config;

import com.crg.learn.domain.account.AccountRepository;
import com.crg.learn.persistence.account.*;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;

public class PersistenceConfiguration {

    public AccountRepository accountGateway() {
        var client = dynamoDbEnhancedClient();
        var table = TableSchema.fromClass(PersistentAccount.class);
        var bankAccountTable = client.table("BankAccount", table);
        return new AccountGateway(bankAccountTable);
    }

    private DynamoDbEnhancedClient dynamoDbEnhancedClient() {
        var client = DynamoDbClient.builder().endpointOverride(URI.create("http://localhost:8000"))
                                             .region(Region.US_EAST_1).build();

        return DynamoDbEnhancedClient.builder().dynamoDbClient(client).build();
    }
}
