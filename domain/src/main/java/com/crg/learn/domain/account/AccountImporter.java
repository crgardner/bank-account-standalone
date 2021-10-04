package com.crg.learn.domain.account;

import com.crg.learn.domain.person.Person;

import java.util.List;

public interface AccountImporter {
    AccountNumber accountNumber();

    Person accountHolder();

    List<EntryImporter> entryImporters();
}
