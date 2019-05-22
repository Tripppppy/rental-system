package com.suke.RentalSystem.model;

import javax.persistence.*;

@Table(name = "tbl_order_ball")
public class OrderBall {
    @Id
    @Column(name = "order_id")
    private Long orderId;

    @Id
    @Column(name = "ball_id")
    private Long ballId;

    private Integer count;

    /**
     * @return order_id
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * @param orderId
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * @return ball_id
     */
    public Long getBallId() {
        return ballId;
    }

    /**
     * @param ballId
     */
    public void setBallId(Long ballId) {
        this.ballId = ballId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}