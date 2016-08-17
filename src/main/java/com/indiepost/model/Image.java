package com.indiepost.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.nio.file.Paths;
import java.util.Date;

/**
 * Created by jake on 8/15/16.
 */
@Entity
@Table(name = "Images")
public class Image implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    @Size(min = 2, max = 120)
    private String directory;

    @NotNull
    @Size(min = 2, max = 120)
    private String original;

    @NotNull
    private int height;

    @NotNull
    private int width;

    @Size(min = 2, max = 120)
    private String large;

    @Size(min = 2, max = 120)
    private String medium;

    @Size(min = 2, max = 120)
    private String small;

    @NotNull
    @Size(min = 2, max = 120)
    private String thumbnail;

    @NotNull
    private boolean isFeatured = false;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private Post post;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getLarge() {
        if (large == null) {
            return original;
        }
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getMedium() {
        if (medium == null) {
            return original;
        }
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getSmall() {
        if (small == null) {
            return original;
        }
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Date getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Date uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public void setFeatured(boolean featured) {
        isFeatured = featured;
    }

    public String getLocation() {
        return Paths.get(directory).resolve(original).toString();
    }
}
