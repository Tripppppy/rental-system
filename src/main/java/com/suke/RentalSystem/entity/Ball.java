package com.suke.RentalSystem.entity;

public class Ball {

  private Long id;
  private String status;
  private String type;
  private String brand;
  private Integer stock;
  private Double dayPrice1;
  private Double dayPrice2;
  private Double monthPrice;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public Integer getStock() {
    return stock;
  }

  public void setStock(Integer stock) {
    this.stock = stock;
  }

  public Double getDayPrice1() {
    return dayPrice1;
  }

  public void setDayPrice1(Double dayPrice1) {
    this.dayPrice1 = dayPrice1;
  }

  public Double getDayPrice2() {
    return dayPrice2;
  }

  public void setDayPrice2(Double dayPrice2) {
    this.dayPrice2 = dayPrice2;
  }

  public Double getMonthPrice() {
    return monthPrice;
  }

  public void setMonthPrice(Double monthPrice) {
    this.monthPrice = monthPrice;
  }

  @Override
  public String toString() {
    return "Ball{" +
      "id=" + id +
      ", status='" + status + '\'' +
      ", type='" + type + '\'' +
      ", brand='" + brand + '\'' +
      ", stock=" + stock +
      ", dayPrice1=" + dayPrice1 +
      ", dayPrice2=" + dayPrice2 +
      ", monthPrice=" + monthPrice +
      '}';
  }
}
