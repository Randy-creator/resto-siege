package org.restaurantmanagement.resto.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.restaurantmanagement.resto.entity.model.Sale;
import org.restaurantmanagement.resto.repository.SaleCrudImpl;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.logging.Logger;

@Service
public class SaleService {

    private static final Logger logger = Logger.getLogger(SaleService.class.getName());
    private final SaleCrudImpl saleCrud;

    public SaleService(SaleCrudImpl saleCrud) {
        this.saleCrud = saleCrud;
    }

    public void getSales(String startTime, String endTime) {
        try {
            String uri = "http://localhost:"+ System.getenv("RESTO1_SERVER_PORT")+"/sales?startTime=" + startTime + "&endTime=" + endTime;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(uri))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int responseStatus = response.statusCode();
            logger.info("Response Status: " + responseStatus);

            if (responseStatus >= 200 && responseStatus < 300) {
                ObjectMapper objectMapper = new ObjectMapper();
                List<Sale> sales = objectMapper.readValue(response.body(), new TypeReference<>() {});

                if (sales.isEmpty()) {
                    logger.info("No sales found.");
                } else {
                    saleCrud.saveAll(sales);
                    logger.info("Sales successfully persisted.");
                }
            } else {
                logger.warning("Failed to fetch sales, HTTP code: " + responseStatus);
            }

        } catch (Exception e) {
            logger.severe("Error fetching best sales: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
