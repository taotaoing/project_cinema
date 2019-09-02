package com.stylefeng.guns.api.cinema.VO.getFieldInfo;



import com.stylefeng.guns.api.cinema.VO.getFields.CinemaInfo;

import java.io.Serializable;

public class FieldInfo implements Serializable {
    private FilmInfo filmInfo;
    private CinemaInfo cinemaInfo;
    private HallInfo hallInfo;

    public FilmInfo getFilmInfo() {
        return filmInfo;
    }

    public void setFilmInfo(FilmInfo filmInfo) {
        this.filmInfo = filmInfo;
    }

    public CinemaInfo getCinemaInfo() {
        return cinemaInfo;
    }

    public void setCinemaInfo(CinemaInfo cinemaInfo) {
        this.cinemaInfo = cinemaInfo;
    }

    public HallInfo getHallInfo() {
        return hallInfo;
    }

    public void setHallInfo(HallInfo hallInfo) {
        this.hallInfo = hallInfo;
    }

    @Override
    public String toString() {
        return "FieldInfo{" +
                "filmInfo=" + filmInfo +
                ", cinemaInfo=" + cinemaInfo +
                ", hallInfo=" + hallInfo +
                '}';
    }
}
