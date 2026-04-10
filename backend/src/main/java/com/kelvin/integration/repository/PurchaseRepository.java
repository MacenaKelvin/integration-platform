package com.kelvin.integration.repository;

import com.kelvin.integration.model.IntegrationStatus;
import com.kelvin.integration.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByStatus(IntegrationStatus status);
}