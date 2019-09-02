package com.stylefeng.guns.api.cinema.VO.getFieldInfo;

import java.io.Serializable;

public class HallInfo implements Serializable {
   private Integer hallFieldId;
   private String  hallName;
   private String price;
   private String seatFile;
   private String soldSeats;

    public Integer getHallFieldId() {
        return hallFieldId;
    }

    public void setHallFieldId(Integer hallFieldId) {
        this.hallFieldId = hallFieldId;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSeatFile() {
        return seatFile;
    }

    public void setSeatFile(String seatFile) {
        this.seatFile = seatFile;
    }

    public String getSoldSeats() {
        return soldSeats;
    }

    public void setSoldSeats(String soldSeats) {
        this.soldSeats = soldSeats;
    }

    @Override
    public String toString() {
        return "HallInfo{" +
                "hallFieldId=" + hallFieldId +
                ", hallName='" + hallName + '\'' +
                ", price='" + price + '\'' +
                ", seatFile='" + seatFile + '\'' +
                ", soldSeats='" + soldSeats + '\'' +
                '}';
    }
}
