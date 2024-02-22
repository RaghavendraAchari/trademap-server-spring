package com.raghav.trademap.modules.notes;

import com.raghav.trademap.modules.notes.dto.DistinctData;
import com.raghav.trademap.modules.notes.dto.NoteRequest;
import com.raghav.trademap.modules.notes.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotesService {
    @Autowired
    private NotesRepo notesRepo;

    public List<Note> getAllNotes(String category, String tag, Sort.Direction sort){
        return notesRepo.findAll(Sort.by(sort, "dateTime"));
    }

    public Note saveNote(NoteRequest noteRequest){
        Note note = Note.mapToNote(noteRequest);

        return notesRepo.save(note);
    }

    public boolean deleteNote(Long id){
        notesRepo.deleteById(id);

        return notesRepo.findById(id).isEmpty();
    }

    public Note updateNote(NoteRequest request){
        if(request.getId() == null){
            throw new RuntimeException("Id should be present");
        }

        Optional<Note> result = notesRepo.findById(request.getId());
        Note existing = result.orElseThrow();

        //update the note
        existing.setCategories(request.getCategories());
        existing.setTags(request.getTags());
        existing.setDateTime(request.getDateTime());
        existing.setContent(request.getContent());
        existing.setTitle(request.getTitle());
        existing.setDescription(request.getDescription());

        return notesRepo.save(existing);
    }

    public DistinctData getDistinctCategoriesAndTags() {
        List<String> categories = notesRepo.findDistinctCategories();
        List<String> tags = notesRepo.findDistinctTags();

        return new DistinctData(categories, tags) ;
    }
}
