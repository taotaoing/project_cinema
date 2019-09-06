package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FilmsVO implements Serializable {
    private static final long serialVersionUID = -8637295407587483684L;
    private int status;
    private String imgPre;
    private int nowPage;
    private int totalPage;
    private List<FilmInfo> FilmInfo;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getImgPre() {
        return imgPre;
    }

    public void setImgPre(String imgPre) {
        this.imgPre = imgPre;
    }

    public int getNowPage() {
        return nowPage;
    }

    public void setNowPage(int nowPage) {
        this.nowPage = nowPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<FilmInfo> getFilmInfo() {
        return FilmInfo;
    }

    public void setFilmInfo(List<FilmInfo> FilmInfo) {
        this.FilmInfo = FilmInfo;
    }
}
