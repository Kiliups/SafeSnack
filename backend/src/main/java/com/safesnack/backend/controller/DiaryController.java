package com.safesnack.backend.controller;

import com.safesnack.backend.model.DiaryEntry;
import com.safesnack.backend.model.UserPrincipal;
import com.safesnack.backend.repository.IDiaryEntryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DiaryController {

    private final IDiaryEntryRepository diaryRepo;

    public DiaryController(IDiaryEntryRepository diaryRepo) {
        this.diaryRepo = diaryRepo;
    }

    @GetMapping("/diaryEntries")
    public ResponseEntity<List<DiaryEntry>> getDiaryEntries(Authentication authentication) {
        // use the token to retrieve the UserMeta object
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        var entries = diaryRepo.findByUser(principal);
        return ResponseEntity.ok(entries.orElse(null));
    }

    @PostMapping("/diaryEntry")
    public ResponseEntity<DiaryEntry> addDiaryEntry(Authentication authentication, DiaryEntry entry) {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        entry.setUser(principal);
        diaryRepo.save(entry);
        return ResponseEntity.ok(entry);
    }

    @PutMapping
    public ResponseEntity<DiaryEntry> updateDiaryEntry(@RequestBody DiaryEntry entry) {
        diaryRepo.save(entry);
        return ResponseEntity.ok(entry);
    }

    @DeleteMapping("/diaryEntry")
    public ResponseEntity<String> deleteDiaryEntry(@RequestParam Long id) {
        diaryRepo.deleteById(id);
        return ResponseEntity.ok("Diary entry deleted");
    }
}
