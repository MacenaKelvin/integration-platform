package com.kelvin.integration.controller;

import com.kelvin.integration.model.Purchase;
import com.kelvin.integration.repository.PurchaseRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    private final PurchaseRepository repository;

    public PurchaseController(PurchaseRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Purchase create(@RequestBody Purchase purchase) {
        return repository.save(purchase);
    }

    @GetMapping
    public List<Purchase> findAll() {
        return repository.findAll();
    }
}