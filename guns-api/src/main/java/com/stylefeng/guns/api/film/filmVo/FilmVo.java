package com.stylefeng.guns.api.film.filmVo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FilmVo implements Serializable {
    private static final long serialVersionUID = -7286527955048504544L;

    private int filmNum;

    private int nowPage;

    private int totalPage;

    private List<FilmInfo> filmInfo;

    public int getFilmNum() {
        return filmNum;
    }

    public void setFilmNum(int filmNum) {
        this.filmNum = filmNum;
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
        return filmInfo;
    }

    public void setFilmInfo(List<FilmInfo> filmInfo) {
        this.filmInfo = filmInfo;
    }


}
