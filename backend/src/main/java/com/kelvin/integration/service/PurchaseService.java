package com.kelvin.integration.service;

import com.kelvin.integration.model.Purchase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class PurchaseService {

    @Autowired
    private RestTemplate restTemplate;

    public int sendToPartner(Purchase purchase) {

        if (purchase.getPurchaseId() != null && purchase.getPurchaseId().startsWith("FAIL")) {
            throw new RuntimeException("Erro simulado de integração");
        }

        String url = "https://jsonplaceholder.typicode.com/posts";

        int maxAttempts = 3;
        int attempt = 0;
        boolean success = false;

        log.info("Iniciando integração da compra {}", purchase.getPurchaseId());

        while (attempt < maxAttempts && !success) {
            attempt++;

            try {
                log.info(
                    "Tentativa {} | purchaseId={} | customerId={}",
                    attempt,
                    purchase.getPurchaseId(),
                    purchase.getCustomerId()
                );

                Object response = restTemplate.postForObject(url, purchase, Object.class);

                log.info(
                    "Integração concluída com sucesso | purchaseId={} | tentativa={}",
                    purchase.getPurchaseId(),
                    attempt
                );

                log.debug("Resposta do parceiro: {}", response);

                success = true;

            } catch (Exception e) {
                log.error(
                    "Falha na integração | purchaseId={} | tentativa={} | erro={}",
                    purchase.getPurchaseId(),
                    attempt,
                    e.getMessage()
                );

                if (attempt == maxAttempts) {
                    throw new RuntimeException("Falha na integração após " + maxAttempts + " tentativas");
                }
            }
        }

        return attempt;
    }
}