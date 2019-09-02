package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class FilmActorVO implements Serializable {
    private static final long serialVersionUID = -9196845475934760179L;
    private String imgAddress;
    private String directorName;
    private String roleName;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getImgAddress() {
        return imgAddress;
    }

    public void setImgAddress(String imgAddress) {
        this.imgAddress = imgAddress;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
