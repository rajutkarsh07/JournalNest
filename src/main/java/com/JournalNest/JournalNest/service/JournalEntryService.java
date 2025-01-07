package com.JournalNest.JournalNest.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.JournalNest.JournalNest.entity.JournalEntry;
import com.JournalNest.JournalNest.repository.JournalEntryRepository;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId Id) {
        return journalEntryRepository.findById(Id);
    }

    public void deleteById(ObjectId Id) {
        journalEntryRepository.deleteById(Id);
    }
}
