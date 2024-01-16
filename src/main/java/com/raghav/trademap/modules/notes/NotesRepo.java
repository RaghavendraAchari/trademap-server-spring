package com.raghav.trademap.modules.notes;

import com.raghav.trademap.modules.notes.model.Notes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotesRepo extends JpaRepository<Notes, Long> {
    @Query("select distinct(a.tags) from Notes a")
    List<String> findDistinctTags();

    @Query("select distinct(a.categories) from Notes a")
    List<String> findDistinctCategories();
}
