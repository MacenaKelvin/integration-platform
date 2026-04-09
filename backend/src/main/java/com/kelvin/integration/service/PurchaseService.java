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

        try {
            Object response = restTemplate.postForObject(url, purchase, Object.class);

            System.out.println("Integração realizada com sucesso!");
            System.out.println(response);

        } catch (Exception e) {
            System.out.println("Erro ao integrar com parceiro: " + e.getMessage());
            throw new RuntimeException("Falha na integração");
        }
    }
}