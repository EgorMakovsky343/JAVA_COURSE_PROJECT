package com.newsportal.dto;

import java.sql.Timestamp;

public class BookmarkDto {
    private Long id;
    private Long userId;
    private Long newsId;
    private Timestamp savedTime;
    private String newsTitle;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getNewsId() { return newsId; }
    public void setNewsId(Long newsId) { this.newsId = newsId; }
    public Timestamp getSavedTime() { return savedTime; }
    public void setSavedTime(Timestamp savedTime) { this.savedTime = savedTime; }
    public String getNewsTitle() { return newsTitle; }
    public void setNewsTitle(String newsTitle) { this.newsTitle = newsTitle; }
}
