package com.raghav.trademap.modules.notes;

import com.raghav.trademap.modules.notes.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotesRepo extends JpaRepository<Note, Long> {
    @Query("select distinct(a.tags) from Note a")
    List<String> findDistinctTags();

    @Query("select distinct(a.categories) from Note a")
    List<String> findDistinctCategories();
}
