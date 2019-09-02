package com.stylefeng.guns.api.film.filmVo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SourceInfoVo implements Serializable {
    private static final long serialVersionUID = 8625665210978436111L;
    private Integer sourceId;

    private String sourceName;

    private boolean isActive = false;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
}
