package com.suke.RentalSystem.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tbl_ball")
public class Ball {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 类型
     */
    private String type;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 日租价格
     */
    @Column(name = "day_price1")
    private BigDecimal dayPrice1;

    /**
     * 日租超出价格
     */
    @Column(name = "day_price2")
    private BigDecimal dayPrice2;

    /**
     * 月租价格
     */
    @Column(name = "month_price")
    private BigDecimal monthPrice;

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
    private String brandName;

    @Transient
    private String typeName;

    @Column(name = "image_url")
    private String imageUrl;

    @Transient
    private Integer count;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取类型
     *
     * @return type - 类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类型
     *
     * @param type 类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取品牌
     *
     * @return brand - 品牌
     */
    public String getBrand() {
        return brand;
    }

    /**
     * 设置品牌
     *
     * @param brand 品牌
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * 获取库存
     *
     * @return stock - 库存
     */
    public Integer getStock() {
        return stock;
    }

    /**
     * 设置库存
     *
     * @param stock 库存
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    /**
     * 获取日租价格
     *
     * @return day_price1 - 日租价格
     */
    public BigDecimal getDayPrice1() {
        return dayPrice1;
    }

    /**
     * 设置日租价格
     *
     * @param dayPrice1 日租价格
     */
    public void setDayPrice1(BigDecimal dayPrice1) {
        this.dayPrice1 = dayPrice1;
    }

    /**
     * 获取日租超出价格
     *
     * @return day_price2 - 日租超出价格
     */
    public BigDecimal getDayPrice2() {
        return dayPrice2;
    }

    /**
     * 设置日租超出价格
     *
     * @param dayPrice2 日租超出价格
     */
    public void setDayPrice2(BigDecimal dayPrice2) {
        this.dayPrice2 = dayPrice2;
    }

    /**
     * 获取月租价格
     *
     * @return month_price - 月租价格
     */
    public BigDecimal getMonthPrice() {
        return monthPrice;
    }

    /**
     * 设置月租价格
     *
     * @param monthPrice 月租价格
     */
    public void setMonthPrice(BigDecimal monthPrice) {
        this.monthPrice = monthPrice;
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

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}