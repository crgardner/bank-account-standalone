package com.crg.learn.domain.account;

import com.crg.learn.domain.person.Person;
import com.crg.learn.domain.testsupport.*;
import nl.jqno.equalsverifier.*;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.*;

import java.util.*;

import static com.crg.learn.domain.testsupport.BookingDates.*;
import static com.crg.learn.domain.testsupport.MonetaryAmounts.*;
import static org.assertj.core.api.Assertions.*;


@DisplayName("Account")
class AccountTest {
    private Account account;

    @BeforeEach
    void init() {
        var accountNumber = new AccountNumber("123X99948715");
        var accountHolder = new Person("Zippy", "Foo");

        account = new Account(accountNumber, accountHolder);
    }

    @Nested
    @DisplayName("when created")
    class WhenCreated {

        @Test
        @DisplayName("has zero balance")
        void hasZeroBalance() {
            var exporter = new AccountTestExporter();
            account.export(exporter);

            assertThat(exporter.balance()).isEqualTo(zero());
        }

        @Nested
        @DisplayName("after making deposits")
        class AfterMakingDeposits {
            private Money depositAmount;

            @BeforeEach
            void init() {
                depositAmount = amountInDefaultCurrency(50.25);
                account.add(new Entry(transactionId(), depositAmount));
            }

            @Test
            @DisplayName("has balance equal to deposit amount after first deposit")
            void hasBalanceEqualToDepositAmount() {
                var exporter = new AccountTestExporter();
                account.export(exporter);

                assertThat(exporter.balance()).isEqualTo(depositAmount);
            }

            @Test
            @DisplayName("has balance equal to the cumulative deposits")
            void hasBalanceEqualToCumulativeDeposits() {
                account.add(new Entry(transactionId(), amountInDefaultCurrency(1000)));
                account.add(new Entry(transactionId(), amountInDefaultCurrency(14.35)));

                var exporter = new AccountTestExporter();
                account.export(exporter);

                assertThat(exporter.balance()).isEqualTo(amountInDefaultCurrency(1064.60));
            }
        }

        @Nested
        @DisplayName("after making withdrawals")
        class AfterMakingWithdrawals {
            @BeforeEach
            void init() {
                account.add(new Entry(transactionId(), amountInDefaultCurrency(1064.60)));
            }

            @Test
            @DisplayName("has balance equal to the previous balance minus the withdrawal amount")
            void hasBalanceEqualToPreviousBalanceMinusWithdrawalAmount() {
                account.add(new Entry(transactionId(), amountInDefaultCurrency(-100)));

                var exporter = new AccountTestExporter();
                account.export(exporter);

                assertThat(exporter.balance()).isEqualTo(amountInDefaultCurrency(964.60));
            }
        }
    }

    @Test
    @DisplayName("creates statement with all entries")
    void createsStatementIncludingAllEntries() {
        account.add(new Entry(transactionId(), amountInDefaultCurrency(100), jun_21_2021()));
        account.add(new Entry(transactionId(), amountInDefaultCurrency(150), jul_03_2021()));
        account.add(new Entry(transactionId(), amountInDefaultCurrency(-20), aug_20_2021()));

        var statement = account.createStatement();

        assertThat(statement).usingRecursiveComparison().isEqualTo(expectedStatement());
    }

    @Test
    @DisplayName("supports creation via import")
    void supportsCreationViaImport() {
        account = new Account(importer());
        var exporter = new AccountTestExporter();

        account.export(EntrySelectionRange.ALL, exporter);

        assertThat(exporter.accountNumber()).isEqualTo("123");
        assertThat(exporter.balance()).isEqualTo(amountInDefaultCurrency(100));
        assertThat(exporter.ownerFirstName()).isEqualTo("Zippy");
        assertThat(exporter.ownerLastName()).isEqualTo("Foo");
        assertThat(exporter.entries()).containsOnly(new EntryTestExporter("999", jun_21_2021(),
                                                                          amountInDefaultCurrency(100)));
    }

    @Test
    @DisplayName("honors equals and hashcode contracts")
    void honorsEqualAndHashCodeContracts() {
        EqualsVerifier.forClass(Account.class).suppress(Warning.NULL_FIELDS,
                                                        Warning.STRICT_INHERITANCE,
                                                        Warning.INHERITED_DIRECTLY_FROM_OBJECT,
                                                        Warning.ALL_FIELDS_SHOULD_BE_USED,
                                                        Warning.NONFINAL_FIELDS)
                                              .verify();
    }

    private AccountImporter importer() {
        return new AccountImporter() {

            @Override
            public AccountNumber accountNumber() {
                return new AccountNumber("123");
            }

            public Person accountHolder() {
                return new Person("Zippy", "Foo");
            }

            public List<EntryImporter> entryImporters() {
                return List.of(new TestEntryImporter(new TransactionId("999"), amountInDefaultCurrency(100),
                                                     jun_21_2021()));
            }
        };
    }

    private AccountStatement expectedStatement() {
        var lines = List.of(
            new AccountStatementLine(amountInDefaultCurrency(100), jun_21_2021(), amountInDefaultCurrency(100)),
            new AccountStatementLine(amountInDefaultCurrency(150), jul_03_2021(), amountInDefaultCurrency(250)),
            new AccountStatementLine(amountInDefaultCurrency(-20), aug_20_2021(), amountInDefaultCurrency(230))
        );

        return new AccountStatement(new AccountStatementLines(lines));
    }

    private TransactionId transactionId() {
        return new TransactionId(UUID.randomUUID().toString());
    }

}