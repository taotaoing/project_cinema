package com.stylefeng.guns.api.film.filmVo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FilmConditionVo implements Serializable {
    private static final long serialVersionUID = -2987715843125492859L;

    private List<CatInfoVo> catInfo;

    private List<SourceInfoVo> sourceInfo;

    private List<YearInfoVo> yearInfo;

    public List<CatInfoVo> getCatInfo() {
        return catInfo;
    }

    public void setCatInfo(List<CatInfoVo> catInfo) {
        this.catInfo = catInfo;
    }

    public List<SourceInfoVo> getSourceInfo() {
        return sourceInfo;
    }

    public void setSourceInfo(List<SourceInfoVo> sourceInfo) {
        this.sourceInfo = sourceInfo;
    }

    public List<YearInfoVo> getYearInfo() {
        return yearInfo;
    }

    public void setYearInfo(List<YearInfoVo> yearInfo) {
        this.yearInfo = yearInfo;
    }
}
