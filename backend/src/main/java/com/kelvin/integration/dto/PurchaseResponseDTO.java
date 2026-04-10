package com.kelvin.integration.dto;

import com.kelvin.integration.model.IntegrationStatus;

import java.time.LocalDateTime;

public class PurchaseResponseDTO {

    private Long id;
    private String purchaseId;
    private String customerId;
    private Double totalAmount;
    private LocalDateTime date;
    private IntegrationStatus status;
    private Integer attempts;

    public PurchaseResponseDTO() {
    }

    public PurchaseResponseDTO(
        Long id,
        String purchaseId,
        String customerId,
        Double totalAmount,
        LocalDateTime date,
        IntegrationStatus status,
        Integer attempts
    ) {
        this.id = id;
        this.purchaseId = purchaseId;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
        this.date = date;
        this.status = status;
        this.attempts = attempts;
    }

    public Long getId() {
        return id;
    }

    public String getPurchaseId() {
        return purchaseId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public IntegrationStatus getStatus() {
        return status;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setStatus(IntegrationStatus status) {
        this.status = status;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }
}