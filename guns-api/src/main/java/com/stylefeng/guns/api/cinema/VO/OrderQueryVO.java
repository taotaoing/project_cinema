package com.stylefeng.guns.api.cinema.VO;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderQueryVO implements Serializable{

    private String cinemaId;
    private String filmPrice;

    public String getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(String cinemaId) {
        this.cinemaId = cinemaId;
    }

    public String getFilmPrice() {
        return filmPrice;
    }

    public void setFilmPrice(String filmPrice) {
        this.filmPrice = filmPrice;
    }

}
