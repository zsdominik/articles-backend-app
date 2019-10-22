package com.zsdominik.articlesbackendapp.dto;

import javax.validation.constraints.Size;

public class UpdateArticleRequestDto {

    @Size(min = 1)
    private String text;

    @Size(min = 1, max = 255)
    private String summary;

    @Size(min = 1, max = 100)
    private String title;

    public UpdateArticleRequestDto(@Size(min = 1) String text, @Size(max = 255) String summary, @Size(min = 1, max = 100) String title) {
        this.text = text;
        this.summary = summary;
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
