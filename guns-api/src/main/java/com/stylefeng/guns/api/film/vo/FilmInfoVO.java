package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class FilmInfoVO implements Serializable {
    private static final long serialVersionUID = 269871976241895023L;
    private String filmId;
    private int filmType;
    private String imgAddress;
    private String filmName;
    private String score;
    private String catId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    public int getFilmType() {
        return filmType;
    }

    public void setFilmType(int filmType) {
        this.filmType = filmType;
    }

    public String getImgAddress() {
        return imgAddress;
    }

    public void setImgAddress(String imgAddress) {
        this.imgAddress = imgAddress;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }
}
