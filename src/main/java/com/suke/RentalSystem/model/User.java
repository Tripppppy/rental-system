package com.suke.RentalSystem.model;

import java.time.LocalDateTime;


public class User {

    private Long id;
    private String loginName;
    private String name;
    private String mobile;
    private String email;
    private String imageUrl;
    private String status;
    private String password;
    private int createdBy;
    private LocalDateTime createdDate;
    private int lastModifiedBy;
    private LocalDateTime lastModifiedDate;
    private int version;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getLoginName() {
    return loginName;
  }

  public void setLoginName(String loginName) {
    this.loginName = loginName;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public int getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(int createdBy) {
    this.createdBy = createdBy;
  }

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(LocalDateTime createdDate) {
    this.createdDate = createdDate;
  }

  public int getLastModifiedBy() {
    return lastModifiedBy;
  }

  public void setLastModifiedBy(int lastModifiedBy) {
    this.lastModifiedBy = lastModifiedBy;
  }

  public LocalDateTime getLastModifiedDate() {
    return lastModifiedDate;
  }

  public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
  }

  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  @Override
  public String toString() {
    return "User{" +
      "id=" + id +
      ", loginName='" + loginName + '\'' +
      ", name='" + name + '\'' +
      ", mobile='" + mobile + '\'' +
      ", email='" + email + '\'' +
      ", imageUrl='" + imageUrl + '\'' +
      ", status='" + status + '\'' +
      ", password='" + password + '\'' +
      ", createdBy=" + createdBy +
      ", createdDate=" + createdDate +
      ", lastModifiedBy=" + lastModifiedBy +
      ", lastModifiedDate=" + lastModifiedDate +
      ", version=" + version +
      '}';
  }
}
