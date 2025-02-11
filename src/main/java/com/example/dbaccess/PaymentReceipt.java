package com.example.dbaccess;

import java.util.*;

public class PaymentReceipt {
    private Integer customerId;
    private ArrayList<Map<String, Object>> availableServices;
    private ArrayList<Map<String, Object>> unavailableServices;
    private double totalPrice;
    private double gst;
    private double finalTotal;

    // Getters and Setters
    public Integer getCustomerId() {
        return customerId; }
    
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId; }

    public ArrayList<Map<String, Object>> getAvailableServices() { 
        return availableServices; }
    
    public void setAvailableServices(ArrayList<Map<String, Object>> availableServices) { 
        this.availableServices = availableServices; }

    public ArrayList<Map<String, Object>> getUnavailableServices() { 
        return unavailableServices; }
    
    public void setUnavailableServices(ArrayList<Map<String, Object>> unavailableServices) {
        this.unavailableServices = unavailableServices; }

    public double getTotalPrice() {
        return totalPrice; }
    
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice; }

    public double getGst() { 
        return gst; }
    
    public void setGst(double gst) {
        this.gst = gst; }

    public double getFinalTotal() {
        return finalTotal; }
    
    public void setFinalTotal(double finalTotal) { 
        this.finalTotal = finalTotal; }
}