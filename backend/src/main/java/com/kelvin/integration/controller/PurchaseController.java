package com.kelvin.integration.controller;

import com.kelvin.integration.dto.PurchaseResponseDTO;
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
    public PurchaseResponseDTO create(@RequestBody Purchase purchase) {

        if (purchase.getDate() == null) {
            purchase.setDate(LocalDateTime.now());
        }

        purchase.setStatus(IntegrationStatus.PENDING);
        purchase.setAttempts(0);

        Purchase saved = repository.save(purchase);

        log.info("Compra criada com status PENDING | purchaseId={}", saved.getPurchaseId());

        try {
            int attemptsUsed = purchaseService.sendToPartner(saved);
            saved.setAttempts(attemptsUsed);
            saved.setStatus(IntegrationStatus.SUCCESS);
            log.info("Compra atualizada para SUCCESS | purchaseId={}", saved.getPurchaseId());

        } catch (Exception e) {
            saved.setAttempts(3);
            saved.setStatus(IntegrationStatus.ERROR);
            log.error("Erro ao integrar compra | purchaseId={} | erro={}", saved.getPurchaseId(), e.getMessage());
        }

        Purchase updated = repository.save(saved);
        return toResponseDTO(updated);
    }

    @GetMapping
    public List<PurchaseResponseDTO> findAll() {
        log.info("Listando todas as compras");
        return repository.findAll()
            .stream()
            .map(this::toResponseDTO)
            .toList();
    }

    @GetMapping("/status/{status}")
    public List<PurchaseResponseDTO> findByStatus(@PathVariable IntegrationStatus status) {
        log.info("Buscando compras por status {}", status);
        return repository.findByStatus(status)
            .stream()
            .map(this::toResponseDTO)
            .toList();
    }

    @PostMapping("/{id}/reprocess")
    public PurchaseResponseDTO reprocess(@PathVariable Long id) {
        Purchase purchase = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Compra não encontrada"));

        log.info("Reprocessando compra | id={} | purchaseId={}", purchase.getId(), purchase.getPurchaseId());

        purchase.setStatus(IntegrationStatus.PENDING);
        repository.save(purchase);

        try {
            int attemptsUsed = purchaseService.sendToPartner(purchase);
            purchase.setAttempts(attemptsUsed);
            purchase.setStatus(IntegrationStatus.SUCCESS);
            log.info("Reprocessamento concluído com sucesso | purchaseId={}", purchase.getPurchaseId());

        } catch (Exception e) {
            purchase.setAttempts(3);
            purchase.setStatus(IntegrationStatus.ERROR);
            log.error("Falha no reprocessamento | purchaseId={} | erro={}", purchase.getPurchaseId(), e.getMessage());
        }

        Purchase updated = repository.save(purchase);
        return toResponseDTO(updated);
    }

    private PurchaseResponseDTO toResponseDTO(Purchase purchase) {
        return new PurchaseResponseDTO(
            purchase.getId(),
            purchase.getPurchaseId(),
            purchase.getCustomerId(),
            purchase.getTotalAmount(),
            purchase.getDate(),
            purchase.getStatus(),
            purchase.getAttempts()
        );
    }
}