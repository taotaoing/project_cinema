package com.stylefeng.guns.api.cinema.VO.getFields;

import java.io.Serializable;
import java.util.List;

public class Fields implements Serializable {
    private CinemaInfo cinemaInfo;
    private List<FilmList> filmList;

    public CinemaInfo getCinemaInfo() {
        return cinemaInfo;
    }

    public void setCinemaInfo(CinemaInfo cinemaInfo) {
        this.cinemaInfo = cinemaInfo;
    }

    public List<FilmList> getFilmList() {
        return filmList;
    }

    public void setFilmList(List<FilmList> filmList) {
        this.filmList = filmList;
    }
}
