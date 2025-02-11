package com.example.dbaccess;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class Booking {
    private Integer bookingId;
    private Integer customerId;
    private Integer categoryId;
    private Integer serviceId;
    private Date bookingDate;
    private Time bookingTime;
    private String bookingProgress;
    private Boolean paymentStatus;
    private String serviceProgress;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String transactionId;

    public Booking() {};

    public Booking(Integer bookingId, Integer customerId, Integer categoryId, Integer serviceId, Date bookingDate,
                   Time bookingTime, String bookingProgress, Boolean paymentStatus, String serviceProgress,
                   Timestamp createdAt, Timestamp updatedAt, String transactionId) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.categoryId = categoryId;
        this.serviceId = serviceId;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.bookingProgress = bookingProgress;
        this.paymentStatus = paymentStatus;
        this.serviceProgress = serviceProgress;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.transactionId = transactionId;
    }

    // Getters and Setters
    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Time getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(Time bookingTime) {
        this.bookingTime = bookingTime;
    }

    public String getBookingProgress() {
        return bookingProgress;
    }

    public void setBookingProgress(String bookingProgress) {
        this.bookingProgress = bookingProgress;
    }

    public Boolean getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getServiceProgress() {
        return serviceProgress;
    }

    public void setServiceProgress(String serviceProgress) {
        this.serviceProgress = serviceProgress;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
