package com.indiepost.dto.stat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jake on 8/10/17.
 */
public class CampaignDto {

    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String clientName;

    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    @NotNull
    private Instant startAt;

    @NotNull
    private Instant endAt;

    private LocalDateTime createdAt;

    @NotNull
    private Long goal = 0L;

    private Long validClicks = 0L;

    private Long allClicks = 0L;

    private List<LinkDto> links = new ArrayList<>();

    public Long getAllClicks() {
        return allClicks;
    }

    public void setAllClicks(Long allClicks) {
        this.allClicks = allClicks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getStartAt() {
        return startAt;
    }

    public void setStartAt(Instant startAt) {
        this.startAt = startAt;
    }

    public Instant getEndAt() {
        return endAt;
    }

    public void setEndAt(Instant endAt) {
        this.endAt = endAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createAt) {
        this.createdAt = createAt;
    }

    public Long getGoal() {
        return goal;
    }

    public void setGoal(Long goal) {
        this.goal = goal;
    }

    public Long getValidClicks() {
        return validClicks;
    }

    public void setValidClicks(Long validClicks) {
        this.validClicks = validClicks;
    }

    public List<LinkDto> getLinks() {
        return links;
    }

    public void setLinks(List<LinkDto> links) {
        this.links = links;
    }
}
