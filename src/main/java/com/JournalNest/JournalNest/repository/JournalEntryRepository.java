package com.JournalNest.JournalNest.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.bson.types.ObjectId;
import com.JournalNest.JournalNest.entity.JournalEntry;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {

}
