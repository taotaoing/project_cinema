package com.stylefeng.guns.rest.film.filmVo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CatInfoVo implements Serializable {
    private static final long serialVersionUID = -3002747686927904669L;
    private Integer catId ;

    private String catName;

    private boolean isActive = false;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }
}
