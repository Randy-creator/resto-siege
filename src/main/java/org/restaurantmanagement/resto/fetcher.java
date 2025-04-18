package org.restaurantmanagement.resto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.restaurantmanagement.resto.entity.DTO.dish.Dish;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.logging.Logger;

public class fetcher {

    public static void main(String[] args) {
    Logger logger = Logger.getLogger(fetcher.class.getName());
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest
                    .newBuilder()
                    .uri(new URI("http://localhost:8081/dishes/"))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            int responseStatus = response.statusCode();
            logger.info("Response Status : " + responseStatus);

            ObjectMapper objectMapper = new ObjectMapper();
            List<org.restaurantmanagement.resto.entity.DTO.dish.Dish> dishes = objectMapper.readValue(response.body(), new TypeReference<List<org.restaurantmanagement.resto.entity.DTO.dish.Dish>>() {});

            String responseBody = response.body();
            for (Dish dish : dishes) {
                logger.info("Dish: " + dish.getName() + " - Price: " + dish.getActualPrice());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
