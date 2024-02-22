package com.raghav.trademap.modules.notes.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.raghav.trademap.modules.notes.model.Note;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
public class NoteResponse {
    private Long id;

    @JsonFormat(pattern = "YYYY-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateTime;

    private List<String> tags;

    private List<String> categories;

    private String content;

    private String title;

    private String description;

    public static NoteResponse mapToNoteResponse(Note notes) {
        return NoteResponse.builder()
                .id(notes.getId())
                .content(notes.getContent())
                .dateTime(notes.getDateTime())
                .tags(notes.getTags())
                .categories(notes.getCategories())
                .title(notes.getTitle())
                .description(notes.getDescription())
                .build();
    }
}
