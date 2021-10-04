package com.crg.learn.domain.account;

import org.junit.jupiter.api.*;

import java.util.List;

import static com.natpryce.makeiteasy.MakeItEasy.*;
import static com.crg.learn.domain.testsupport.AccountMaker.*;
import static org.assertj.core.api.Assertions.*;

@DisplayName("EntrySelectionRange")
class EntrySelectionRangeTest {

    private Entry secondEntry;
    private List<Entry> entries;

    @BeforeEach
    void init() {
        var firstEntry = make(an(Entry));
        secondEntry = make(an(Entry));
        entries = List.of(firstEntry, secondEntry);
    }

    @Test
    @DisplayName("selects all entries")
    void selectsAllEntries() {
        var selectedEntries = EntrySelectionRange.ALL.select(entries);

        assertThat(selectedEntries).isEqualTo(entries);
    }

    @Test
    @DisplayName("selects most recently added entry")
    void selectsMostRecentlyAddedEntry() {
        var selectedEntries = EntrySelectionRange.MOST_RECENT.select(entries);

        assertThat(selectedEntries).containsOnly(secondEntry);
    }

}