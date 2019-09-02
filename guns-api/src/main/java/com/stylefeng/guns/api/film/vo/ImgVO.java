package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ImgVO implements Serializable {
    private static final long serialVersionUID = 7775702272673775048L;
    private String mainImg;
    private String img01;
    private String img02;
    private String img03;
    private String img04;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getMainImg() {
        return mainImg;
    }

    public void setMainImg(String mainImg) {
        this.mainImg = mainImg;
    }

    public String getImg01() {
        return img01;
    }

    public void setImg01(String img01) {
        this.img01 = img01;
    }

    public String getImg02() {
        return img02;
    }

    public void setImg02(String img02) {
        this.img02 = img02;
    }

    public String getImg03() {
        return img03;
    }

    public void setImg03(String img03) {
        this.img03 = img03;
    }

    public String getImg04() {
        return img04;
    }

    public void setImg04(String img04) {
        this.img04 = img04;
    }
}