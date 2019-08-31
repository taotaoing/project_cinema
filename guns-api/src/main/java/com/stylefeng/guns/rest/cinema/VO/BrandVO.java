package com.stylefeng.guns.rest.cinema.VO;

import lombok.Data;

import java.io.Serializable;

@Data
public class BrandVO implements Serializable {

    private String brandId;
    private String brandName;
    private boolean isActive;


}
