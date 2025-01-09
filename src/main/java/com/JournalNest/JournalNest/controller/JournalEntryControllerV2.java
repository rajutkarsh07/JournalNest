package com.JournalNest.JournalNest.controller;

import com.JournalNest.JournalNest.entity.JournalEntry;
import com.JournalNest.JournalNest.service.JournalEntryService;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) {
        try {
            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry);
            return ResponseEntity.status(HttpStatus.CREATED).body(myEntry);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntry(@PathVariable ObjectId myId) {
        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
        if (journalEntry.isPresent()) {
            return ResponseEntity.ok(journalEntry.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalEntry(@PathVariable ObjectId myId) {
        try {
            journalEntryService.deleteById(myId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting journal entry");
        }
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<JournalEntry> updateJournalEntry(@PathVariable ObjectId myId,
            @RequestBody JournalEntry newEntry) {
        Optional<JournalEntry> optionalEntry = journalEntryService.findById(myId);

        if (optionalEntry.isPresent()) {
            JournalEntry existingEntry = optionalEntry.get();

            if (newEntry.getTitle() != null && !newEntry.getTitle().isEmpty()) {
                existingEntry.setTitle(newEntry.getTitle());
            }

            if (newEntry.getContent() != null && !newEntry.getContent().isEmpty()) {
                existingEntry.setContent(newEntry.getContent());
            }

            journalEntryService.saveEntry(existingEntry);
            return ResponseEntity.ok(existingEntry);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
