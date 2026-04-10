package com.kelvin.integration.controller;

import com.kelvin.integration.model.IntegrationStatus;
import com.kelvin.integration.model.Purchase;
import com.kelvin.integration.repository.PurchaseRepository;
import com.kelvin.integration.service.PurchaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/purchases")
@Slf4j
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

        purchase.setStatus(IntegrationStatus.PENDING);

        Purchase saved = repository.save(purchase);

        log.info("Compra criada com status PENDING | purchaseId={}", saved.getPurchaseId());

        try {
            purchaseService.sendToPartner(saved);
            saved.setStatus(IntegrationStatus.SUCCESS);
            log.info("Compra atualizada para SUCCESS | purchaseId={}", saved.getPurchaseId());

        } catch (Exception e) {
            log.error("Erro ao integrar compra | purchaseId={} | erro={}", saved.getPurchaseId(), e.getMessage());
            saved.setStatus(IntegrationStatus.ERROR);
        }

        return repository.save(saved);
    }

    @GetMapping
    public List<Purchase> findAll() {
        log.info("Listando todas as compras");
        return repository.findAll();
    }

    @GetMapping("/status/{status}")
    public List<Purchase> findByStatus(@PathVariable IntegrationStatus status) {
        log.info("Buscando compras por status {}", status);
        return repository.findByStatus(status);
    }
}