package com.raghav.trademap.modules.notes.model;

import com.raghav.trademap.modules.notes.dto.NoteRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "notes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @ElementCollection
    @Column(name = "tags")
    private List<String> tags;

    @ElementCollection
    @Column(name = "categories")
    private List<String> categories;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "title", columnDefinition = "TEXT")
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;


    public static Notes mapToNote(NoteRequest request){
        return Notes.builder()
                .dateTime(request.getDateTime())
                .categories(request.getCategories())
                .tags(request.getTags())
                .content(request.getContent())
                .title(request.getTitle())
                .description(request.getDescription())
                .build();
    }
}
