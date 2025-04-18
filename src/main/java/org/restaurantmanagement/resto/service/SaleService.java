package org.restaurantmanagement.resto.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.restaurantmanagement.resto.entity.model.Sale;
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

    public void getBestSales(String startTime, String endTime) {
        try {
//            2025-04-01 12:00:00
//            String startTime = "2024-04-01T00:00:00";
//            String endTime = "2025-05-18T23:59:59";
            String uri = "http://localhost:8080/sales/bestSales?top=3&startTime=" + startTime + "&endTime=" + endTime;


            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest
                    .newBuilder()
                    .uri(new URI(uri))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            int responseStatus = response.statusCode();
            logger.info("Response Status: " + responseStatus);

            if (responseStatus >= 200 && responseStatus < 300) {
                ObjectMapper objectMapper = new ObjectMapper();
                List<org.restaurantmanagement.resto.entity.model.Sale> sales = objectMapper.readValue(response.body(), new TypeReference<List<org.restaurantmanagement.resto.entity.model.Sale>>() {
                });

                if (sales.isEmpty()) {
                    logger.info("No sales found.");
                } else {
                    for (org.restaurantmanagement.resto.entity.model.Sale sale : sales) {
                        logger.info("Dish: " + sale.getDishName() +
                                " | Sold: " + sale.getSaleQuantity() +
                                " | Earned: $" + sale.getTotalEarned());
                    }
                }
            } else {
                logger.warning("Failed to fetch sales, HTTP code: " + responseStatus);
            }

        } catch (Exception e) {
            logger.severe("Error fetching best sales: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

//    public static void main(String[] args) {
//        SaleService service = new SaleService();
//        service.getBestSales();
//    }
}
