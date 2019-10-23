package com.zsdominik.articlesbackendapp.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class NewArticleRequestDto {

    @NotNull(message = "AuthorId must be added")
    private Long authorId;

    @NotNull(message = "Text must be added")
    @Size(min = 1)
    private String text;

    @Size(min = 1, max = 255)
    private String summary;

    @Size(min = 1, max = 100)
    @NotNull(message = "Title must be added")
    private String title;

    public NewArticleRequestDto(@NotNull(message = "AuthorId must be added") Long authorId, @NotNull String text, @Size(max = 255) String summary, @Size(max = 100) @NotNull(message = "Title must be added") String title) {
        this.authorId = authorId;
        this.text = text;
        this.summary = summary;
        this.title = title;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public String getText() {
        return text;
    }

    public String getSummary() {
        return summary;
    }

    public String getTitle() {
        return title;
    }

}
