package com.raghav.trademap.modules.notes;

import com.raghav.trademap.modules.notes.dto.DistinctData;
import com.raghav.trademap.modules.notes.dto.NoteRequest;
import com.raghav.trademap.modules.notes.dto.NoteResponse;
import com.raghav.trademap.modules.notes.model.Notes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotesService {
    @Autowired
    private NotesRepo notesRepo;

    public List<NoteResponse> getAllNotes(String category, String tag, Sort.Direction sort){
        List<Notes> result = notesRepo.findAll(Sort.by(sort, "dateTime"));

        return result.stream().map(NoteResponse::mapToNoteResponse).toList();
    }

    public NoteResponse postNote(NoteRequest noteRequest){
        Notes note = Notes.mapToNote(noteRequest);

        Notes saved = notesRepo.save(note);

        return NoteResponse.mapToNoteResponse(saved);
    }

    public boolean deleteNote(Long id){
        notesRepo.deleteById(id);

        return notesRepo.findById(id).isEmpty();
    }

    public NoteResponse updateNote(NoteRequest request){
        if(request.getId() == null){
            throw new RuntimeException("Id should be present");
        }

        Optional<Notes> result = notesRepo.findById(request.getId());
        Notes existing = result.orElseThrow();

        //update the note
        existing.setCategories(request.getCategories());
        existing.setTags(request.getTags());
        existing.setDateTime(request.getDateTime());
        existing.setContent(request.getContent());
        existing.setTitle(request.getTitle());
        existing.setDescription(request.getDescription());

        Notes updated = notesRepo.save(existing);

        return NoteResponse.mapToNoteResponse(updated);
    }

    public DistinctData getDistinctCategoriesAndTags() {
        List<String> categories = notesRepo.findDistinctCategories();
        List<String> tags = notesRepo.findDistinctTags();

        return new DistinctData(categories, tags) ;
    }
}
