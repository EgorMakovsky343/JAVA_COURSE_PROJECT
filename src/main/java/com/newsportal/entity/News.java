package com.newsportal.entity;

import java.sql.Timestamp;

public class News {
    private Long id;
    private Long userId;
    private String userName;
    private boolean archived;
    private String title;
    private String description;
    private String content;
    private int viewsCount;
    private Timestamp lastChange;
    private String[] tags;

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public boolean isArchived() { return archived; }
    public void setArchived(boolean archived) { this.archived = archived; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public int getViewsCount() { return viewsCount; }
    public void setViewsCount(int viewsCount) { this.viewsCount = viewsCount; }

    public Timestamp getLastChange() { return lastChange; }
    public void setLastChange(Timestamp lastChange) { this.lastChange = lastChange; }

    public String[] getTags() { return tags; }
    public void setTags(String[] tags) { this.tags = tags; }
}