package com.crg.learn.account.application.config;

import com.crg.learn.domain.account.AccountRepository;
import com.crg.learn.persistence.account.*;
import com.crg.learn.persistence.conversion.MoneyConverter;
import org.javamoney.moneta.Money;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.StaticTableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;
import java.time.Instant;
import java.util.List;

import static software.amazon.awssdk.enhanced.dynamodb.mapper.StaticAttributeTags.*;

public class PersistenceConfiguration {

    public AccountRepository accountGateway() {
        var client = dynamoDbEnhancedClient();
//        var table = TableSchema.fromClass(PersistentAccount.class);

        var entryTable = StaticTableSchema.builder(PersistentEntry.class)
                .newItemSupplier(PersistentEntry::new)
                .addAttribute(Instant.class, a -> a.name("whenBooked")
                                                  .getter(PersistentEntry::getWhenBooked)
                                                  .setter(PersistentEntry::setWhenBooked))
                .addAttribute(Money.class, a -> a.name("amount")
                        .getter(PersistentEntry::getAmount)
                        .setter(PersistentEntry::setAmount).attributeConverter(new MoneyConverter()))
                .addAttribute(String.class, a -> a.name("transactionId")
                        .getter(PersistentEntry::getTransactionId)
                        .setter(PersistentEntry::setTransactionId))
                .build();


        var table = TableSchema.builder(PersistentAccount.class)
                .newItemSupplier(PersistentAccount::new)
                .addAttribute(String.class, a -> a.name("accountNumber")
                                                .getter(PersistentAccount::getAccountNumber)
                                                .setter(PersistentAccount::setAccountNumber)
                                                .tags(primaryPartitionKey()))
                .addAttribute(String.class, a -> a.name("holderFirstName")
                                                .getter(PersistentAccount::getHolderFirstName)
                                                .setter(PersistentAccount::setHolderFirstName))
                .addAttribute(String.class, a -> a.name("holderLastName")
                                                .getter(PersistentAccount::getHolderLastName)
                                                .setter(PersistentAccount::setHolderLastName))
//                .addAttribute(List.class, a -> a.name("entries")
//                                                .getter(PersistentAccount::getEntries)
//                                                .setter(PersistentAccount::setEntries))
//                .flatten(EnhancedType.listOf(entryTable.itemType()), PersistentAccount::getEntries, PersistentAccount::setEntries))
                .addAttribute(
                        EnhancedType.listOf(
                                EnhancedType.documentOf(PersistentEntry.class, entryTable)), a -> a.name("entries")
                                                .getter(PersistentAccount::getEntries)
                                                .setter(PersistentAccount::setEntries))
//                .flatten(EnhancedType.listOf(entryTable.itemType()), PersistentAccount::getEntries, PersistentAccount::setEntries))
                .build();


        var bankAccountTable = client.table("BankAccount", table);
        return new AccountGateway(bankAccountTable);
    }

    private DynamoDbEnhancedClient dynamoDbEnhancedClient() {
        var client = DynamoDbClient.builder().endpointOverride(URI.create("http://localhost:8000"))
                                             .region(Region.US_EAST_1).build();

        return DynamoDbEnhancedClient.builder().dynamoDbClient(client).build();
    }
}
