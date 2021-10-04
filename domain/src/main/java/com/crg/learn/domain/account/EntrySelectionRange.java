package com.crg.learn.domain.account;

import java.util.*;

public enum EntrySelectionRange {
    ALL() {
        @Override
        public List<Entry> select(List<Entry> entries) {
            return entries;
        }
    },

    MOST_RECENT() {
        @Override
        public List<Entry> select(List<Entry> entries) {
            return entries.isEmpty() ? entries : Collections.singletonList(entries.get(entries.size() - 1));
        }
    },
    ;

    public abstract List<Entry> select(List<Entry> entries);
}
