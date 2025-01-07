package com.JournalNest.JournalNest.controller;

import com.JournalNest.JournalNest.entity.JournalEntry;
import com.JournalNest.JournalNest.service.JournalEntryService;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntry> getAll() {
        return journalEntryService.getAll();
    }

    @PostMapping
    public JournalEntry createEntry(@RequestBody JournalEntry myEntry) {
        myEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(myEntry);
        return myEntry;
    }

    @GetMapping("id/{myId}")
    public JournalEntry getJournalEntry(@PathVariable ObjectId myId) {
        return journalEntryService.findById(myId).orElse(null);
    }

    @DeleteMapping("id/{myId}")
    public boolean deleteJournalEntry(@PathVariable ObjectId myId) {
        journalEntryService.deleteById(myId);
        return true;
    }

    // @PutMapping("id/{myId}")
    // public JournalEntry updateJournalEntry(@PathVariable ObjectId myId,
    // @RequestBody JournalEntry newEntry) {
    // JournalEntry journalEntry = journalEntryService.findById(myId).orElse(null);

    // if (journalEntry != null) {

    // old.setTitle(
    // newEntry.getTitle() != null && newEntry.getTitle().equals("") ?
    // newEntry.getTitle() : oldTitle());
    // old.setContent(newEntry.getContent() != null && newEntry.equals("") ?
    // newEntry.getContent() : oldContent());
    // }
    // journalEntryService.saveEntry(newEntry);
    // return myEntry;
    // }

    @PutMapping("id/{myId}")
    public JournalEntry updateJournalEntry(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry) {
        JournalEntry existingEntry = journalEntryService.findById(myId).orElse(null);

        if (existingEntry != null) {
            if (newEntry.getTitle() != null && !newEntry.getTitle().isEmpty()) {
                existingEntry.setTitle(newEntry.getTitle());
            }

            if (newEntry.getContent() != null && !newEntry.getContent().isEmpty()) {
                existingEntry.setContent(newEntry.getContent());
            }

            journalEntryService.saveEntry(existingEntry);
        }

        return existingEntry;
    }

}
