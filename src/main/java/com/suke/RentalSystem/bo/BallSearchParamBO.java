package com.suke.RentalSystem.bo;

import lombok.Data;

@Data
public class BallSearchParamBO {

    private String type;
    private String brand;
    private Double minDayPrice;
    private Double maxDayPrice;
    private Double minMonthPrice;
    private Double maxMonthPrice;

}
