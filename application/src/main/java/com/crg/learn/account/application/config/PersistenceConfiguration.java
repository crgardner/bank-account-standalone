package com.crg.learn.account.application.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.crg.learn.domain.account.AccountRepository;
import com.crg.learn.persistence.account.AccountGateway;

public class PersistenceConfiguration {

    AccountRepository accountGateway() {
        return new AccountGateway();
    }

    private static DynamoDBMapper setUpDb() {
        var client = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
                new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-east-1")).build();

        return new DynamoDBMapper(client);
    }
}
