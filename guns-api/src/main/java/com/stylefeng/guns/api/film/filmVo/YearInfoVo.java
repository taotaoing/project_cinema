package com.stylefeng.guns.api.film.filmVo;

import lombok.Data;

import java.io.Serializable;

@Data
public class YearInfoVo implements Serializable {
    private static final long serialVersionUID = -4840722166290756238L;
    private Integer yearId;

    private String yearName;

    private boolean isActive = false;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getYearId() {
        return yearId;
    }

    public void setYearId(int yearId) {
        this.yearId = yearId;
    }

    public String getYearName() {
        return yearName;
    }

    public void setYearName(String yearName) {
        this.yearName = yearName;
    }
}
