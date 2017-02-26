package com.indiepost.dto;

/**
 * Created by jake on 17. 2. 25.
 */
public class RenderingResponseDto {
    private String title;
    private String meta;
    private String state = "{}";
    private String markup = "";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMarkup() {
        return markup;
    }

    public void setMarkup(String markup) {
        this.markup = markup;
    }
}
