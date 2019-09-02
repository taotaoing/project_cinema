package com.stylefeng.guns.api.film.filmVo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BannerVo implements Serializable {
    private static final long serialVersionUID = 2940345568112893582L;

    private Integer bannerId;

    private String bannerAddress;

    private String bannerUrl;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getBannerId() {
        return bannerId;
    }

    public void setBannerId(Integer bannerId) {
        this.bannerId = bannerId;
    }

    public String getBannerAddress() {
        return bannerAddress;
    }

    public void setBannerAddress(String bannerAddress) {
        this.bannerAddress = bannerAddress;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }
}
