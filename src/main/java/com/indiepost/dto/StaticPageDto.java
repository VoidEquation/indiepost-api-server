package com.indiepost.dto;

import com.indiepost.enums.Types;

import java.time.Instant;

/**
 * Created by jake on 17. 3. 5.
 */
public class StaticPageDto {

    private Long id;

    private String title;

    private String content;

    private String slug;

    private int displayOrder;

    private Types.PostStatus status;

    private String authorDisplayName;

    private Instant createdAt;

    private Instant modifiedAt;

    private String type = "";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getAuthorDisplayName() {
        return authorDisplayName;
    }

    public void setAuthorDisplayName(String authorDisplayName) {
        this.authorDisplayName = authorDisplayName;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Types.PostStatus getStatus() {
        return status;
    }

    public void setStatus(Types.PostStatus status) {
        this.status = status;
    }
}
