package com.suke.RentalSystem.entity;

import java.time.LocalDateTime;

public class Order {
  private Long id;
  private Long ballId;
  private Long userId;
  private LocalDateTime orderRentDate;
  private LocalDateTime orderReturnDate;
  private Double cost;
  private String status;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getBallId() {
    return ballId;
  }

  public void setBallId(Long ballId) {
    this.ballId = ballId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public LocalDateTime getOrderRentDate() {
    return orderRentDate;
  }

  public void setOrderRentDate(LocalDateTime orderRentDate) {
    this.orderRentDate = orderRentDate;
  }

  public LocalDateTime getOrderReturnDate() {
    return orderReturnDate;
  }

  public void setOrderReturnDate(LocalDateTime orderReturnDate) {
    this.orderReturnDate = orderReturnDate;
  }

  public Double getCost() {
    return cost;
  }

  public void setCost(Double cost) {
    this.cost = cost;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "Order{" +
      "id=" + id +
      ", ballId=" + ballId +
      ", userId=" + userId +
      ", orderRentDate=" + orderRentDate +
      ", orderReturnDate=" + orderReturnDate +
      ", cost=" + cost +
      ", status='" + status + '\'' +
      '}';
  }
}
