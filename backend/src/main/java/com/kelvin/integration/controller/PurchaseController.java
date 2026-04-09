package com.kelvin.integration.controller;

import com.kelvin.integration.model.IntegrationStatus;
import com.kelvin.integration.model.Purchase;
import com.kelvin.integration.repository.PurchaseRepository;
import com.kelvin.integration.service.PurchaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    @Autowired
    private PurchaseRepository repository;

    @Autowired
    private PurchaseService purchaseService;

    @PostMapping
    public Purchase create(@RequestBody Purchase purchase) {

        if (purchase.getDate() == null) {
            purchase.setDate(LocalDateTime.now());
        }

        // status inicial
        purchase.setStatus(IntegrationStatus.PENDING);

        Purchase saved = repository.save(purchase);

        try {
            purchaseService.sendToPartner(saved);
            saved.setStatus(IntegrationStatus.SUCCESS);

        } catch (Exception e) {
            System.out.println("Erro ao integrar: " + e.getMessage());
            saved.setStatus(IntegrationStatus.ERROR);
        }

        return repository.save(saved);
    }

    @GetMapping
    public List<Purchase> findAll() {
        return repository.findAll();
    }
}