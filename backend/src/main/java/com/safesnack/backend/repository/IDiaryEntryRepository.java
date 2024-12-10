package com.safesnack.backend.repository;

import com.safesnack.backend.model.DiaryEntry;
import com.safesnack.backend.model.UserPrincipal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IDiaryEntryRepository extends JpaRepository<DiaryEntry, Long> {

    Optional<List<DiaryEntry>> findByUser(UserPrincipal user);
}
