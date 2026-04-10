package com.kelvin.integration.service;

import com.kelvin.integration.model.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PurchaseService {

    @Autowired
    private RestTemplate restTemplate;

    public void sendToPartner(Purchase purchase) {
        String url = "https://jsonplaceholder.typicode.com/posts";

        int maxAttempts = 3;
        int attempt = 0;
        boolean success = false;

        while (attempt < maxAttempts && !success) {
            attempt++;

            try {
                System.out.println("Tentativa " + attempt + " de envio para parceiro...");

                Object response = restTemplate.postForObject(url, purchase, Object.class);

                System.out.println("Integração realizada com sucesso na tentativa " + attempt);
                System.out.println(response);

                success = true;

            } catch (Exception e) {
                System.out.println("Erro na tentativa " + attempt + ": " + e.getMessage());

                if (attempt == maxAttempts) {
                    throw new RuntimeException("Falha na integração após " + maxAttempts + " tentativas");
                }
            }
        }
    }
}