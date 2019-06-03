package com.suke.RentalSystem.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Table(name = "tbl_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 外键
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 借球日期
     */
    @Column(name = "order_rent_date")
    private LocalDateTime orderRentDate;

    /**
     * 借球日期
     */
    @Column(name = "order_date")
    private LocalDateTime orderDate;

    /**
     * 还球日期
     */
    @Column(name = "order_return_date")
    private LocalDateTime orderReturnDate;

    /**
     * 还球日期
     */
    @Column(name = "order_real_return_date")
    private LocalDateTime orderRealReturnDate;

    /**
     * 实际花费
     */
    private Double cost;

    @Column(name = "predict_cost")
    private Double predictCost;

    /**
     * 订单状态
     */
    private String status;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "last_modified_by")
    private Long lastModifiedBy;

    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    private Integer version;

    @Transient
    private User user;

    @Transient
    private List<Ball> balls;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取外键
     *
     * @return user_id - 外键
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置外键
     *
     * @param userId 外键
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取借球日期
     *
     * @return order_rent_date - 借球日期
     */
    public LocalDateTime getOrderRentDate() {
        return orderRentDate;
    }

    /**
     * 设置借球日期
     *
     * @param orderRentDate 借球日期
     */
    public void setOrderRentDate(LocalDateTime orderRentDate) {
        this.orderRentDate = orderRentDate;
    }

    /**
     * 获取还球日期
     *
     * @return order_return_date - 还球日期
     */
    public LocalDateTime getOrderReturnDate() {
        return orderReturnDate;
    }

    /**
     * 设置还球日期
     *
     * @param orderReturnDate 还球日期
     */
    public void setOrderReturnDate(LocalDateTime orderReturnDate) {
        this.orderReturnDate = orderReturnDate;
    }

    /**
     * 获取实际花费
     *
     * @return cost - 实际花费
     */
    public Double getCost() {
        return cost;
    }

    /**
     * 设置实际花费
     *
     * @param cost 实际花费
     */
    public void setCost(Double cost) {
        this.cost = cost;
    }

    /**
     * 获取订单状态
     *
     * @return status - 订单状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置订单状态
     *
     * @param status 订单状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return created_by
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy
     */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return created_date
     */
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate
     */
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return last_modified_by
     */
    public Long getLastModifiedBy() {
        return lastModifiedBy;
    }

    /**
     * @param lastModifiedBy
     */
    public void setLastModifiedBy(Long lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    /**
     * @return last_modified_date
     */
    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    /**
     * @param lastModifiedDate
     */
    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    /**
     * @return version
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * @param version
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Ball> getBalls() {
        return balls;
    }

    public void setBalls(List<Ball> balls) {
        this.balls = balls;
    }

    public Double getPredictCost() {
        return predictCost;
    }

    public void setPredictCost(Double predictCost) {
        this.predictCost = predictCost;
    }

    public LocalDateTime getOrderRealReturnDate() {
        return orderRealReturnDate;
    }

    public void setOrderRealReturnDate(LocalDateTime orderRealReturnDate) {
        this.orderRealReturnDate = orderRealReturnDate;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}