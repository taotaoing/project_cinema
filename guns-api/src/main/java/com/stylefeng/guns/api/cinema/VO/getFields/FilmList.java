package com.stylefeng.guns.api.cinema.VO.getFields;

import java.io.Serializable;
import java.util.List;

public class FilmList implements Serializable {
    private int filmId;
    private String filmName;
    private String filmType;
    private String filmCats;
    private String actors;
    private String imgAddress;
    private String filmLength;

    public String getFilmLength() {
        return filmLength;
    }

    public void setFilmLength(String filmLength) {
        this.filmLength = filmLength;
    }

    private List<FilmField> filmFields;

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

    public String getFilmCats() {
        return filmCats;
    }

    public void setFilmCats(String filmCats) {
        this.filmCats = filmCats;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getImgAddress() {
        return imgAddress;
    }

    public void setImgAddress(String imgAddress) {
        this.imgAddress = imgAddress;
    }

    public List<FilmField> getFilmFields() {
        return filmFields;
    }

    public void setFilmFields(List<FilmField> filmFields) {
        this.filmFields = filmFields;
    }

}
