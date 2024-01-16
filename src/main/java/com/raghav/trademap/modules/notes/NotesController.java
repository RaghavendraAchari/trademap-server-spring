package com.raghav.trademap.modules.notes;

import com.raghav.trademap.modules.notes.dto.DistinctData;
import com.raghav.trademap.modules.notes.dto.NoteRequest;
import com.raghav.trademap.modules.notes.dto.NoteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NotesController {
    @Autowired
    private NotesService notesService;

    @GetMapping()
    public ResponseEntity<List<NoteResponse>> getAllNotes(@RequestParam(defaultValue = "", required = false) String category,
                                                          @RequestParam(defaultValue = "", required = false) String tag ,
                                                          @RequestParam(defaultValue = "DESC", required = false) Sort.Direction sort){
        return ResponseEntity.ok(notesService.getAllNotes(category, tag, sort));
    }

    @PostMapping()
    public ResponseEntity<NoteResponse> saveNote(@RequestBody NoteRequest request){
        return ResponseEntity.ok(notesService.postNote(request));
    }

    @PutMapping()
    public ResponseEntity<NoteResponse> updateNote(@RequestBody NoteRequest request){
        return ResponseEntity.ok(notesService.updateNote(request));
    }

    @DeleteMapping()
    public ResponseEntity<Boolean> deleteNote(@RequestParam(required = true) Long id){
        return ResponseEntity.ok(notesService.deleteNote(id));
    }

    @GetMapping("/categoriesAndTags")
    public ResponseEntity<DistinctData> getDistinctCategoriesAndTags(){
        return ResponseEntity.ok(notesService.getDistinctCategoriesAndTags());
    }
}
