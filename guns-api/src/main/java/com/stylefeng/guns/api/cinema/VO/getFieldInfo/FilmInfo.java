package com.stylefeng.guns.api.cinema.VO.getFieldInfo;


import java.io.Serializable;

public class FilmInfo implements Serializable {
    private int filmId;
    private String filmName;
    private String filmType;
    private String imgAddress;
    private String filmCats;
    private String filmLength;

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getFilmType() {
        return filmType;
    }

    public void setFilmType(String filmType) {
        this.filmType = filmType;
    }

    public String getImgAddress() {
        return imgAddress;
    }

    public void setImgAddress(String imgAddress) {
        this.imgAddress = imgAddress;
    }

    public String getFilmCats() {
        return filmCats;
    }

    public void setFilmCats(String filmCats) {
        this.filmCats = filmCats;
    }

    public String getFilmLength() {
        return filmLength;
    }

    public void setFilmLength(String filmLength) {
        this.filmLength = filmLength;
    }

    @Override
    public String toString() {
        return "FilmInfo{" +
                "filmId=" + filmId +
                ", filmName='" + filmName + '\'' +
                ", filmType='" + filmType + '\'' +
                ", imgAddress='" + imgAddress + '\'' +
                ", filmCats='" + filmCats + '\'' +
                ", filmLength='" + filmLength + '\'' +
                '}';
    }
}
