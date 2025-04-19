package org.restaurantmanagement.resto.service.dish;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.restaurantmanagement.resto.entity.Enum.Mode;
import org.restaurantmanagement.resto.entity.Enum.StatusType;
import org.restaurantmanagement.resto.entity.model.Branch;
import org.restaurantmanagement.resto.entity.model.DishOrder;
import org.restaurantmanagement.resto.entity.model.Status;
import org.restaurantmanagement.resto.repository.BranchDaoImpl;
import org.restaurantmanagement.resto.service.Branch.BranchServiceImpl;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class DishServiceImpl implements DishService{
    private final BranchDaoImpl branchDao;

    public DishServiceImpl(BranchDaoImpl branchDao) {
        this.branchDao = branchDao;
    }

    private Map<Long, DishOrder> getDishOrdersByDishId(long dishId, LocalDateTime start, LocalDateTime end) {
        Map<Long, DishOrder> dishOrders = new HashMap<>();
        List<Branch> branches = branchDao.getBranches();

        for (Branch branch : branches) {
            try {
                HttpClient client = HttpClient.newHttpClient();

                String baseUrl = branch.getBranchUrl();
                String endpoint = "/dishes/" + dishId + "/dishOrders";

                String startStr = start.toString();
                String endStr = end.toString();

                String queryParams = "?start=" + startStr + "&end=" + endStr;

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI(baseUrl + endpoint + queryParams))
                        .header(branch.getBranchName().toUpperCase()+"API-KEY", branch.getBranchApiKey())
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 200) {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.registerModule(new JavaTimeModule());
                    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                    System.out.println(response.body());

                    Map<Long, DishOrder> branchDishOrderMap = mapper.readValue(
                            response.body(),
                            new TypeReference<Map<Long, DishOrder>>() {}
                    );

                    dishOrders.putAll(branchDishOrderMap);
                } else {
                    System.err.println("Failed to fetch dish orders. HTTP " + response.statusCode());
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


        return dishOrders;
    }

    @Override
    public double calculateProcessingTimeForDishOrders(long dishId, LocalDateTime start, LocalDateTime end, TimeUnit unit, Mode mode){
        Map<Long, DishOrder> dishOrderMap = getDishOrdersByDishId(dishId, start, end);

        List<Long> durationsInSeconds = new ArrayList<>();

        for (DishOrder dishOrder : dishOrderMap.values()) {
            List<Status> statuses = dishOrder.getStatusList().stream()
                    .sorted(Comparator.comparing(Status::getCreationDate))
                    .toList();

            Status inProgressStatus = null;

            for (Status status : statuses) {
                if (status.getStatusType() == StatusType.IN_PROGRESS) {
                    inProgressStatus = status;
                } else if (status.getStatusType() == StatusType.FINISHED && inProgressStatus != null) {
                    Instant inProgressTime = inProgressStatus.getCreationDate();
                    Instant finishedTime = status.getCreationDate();

                    if (finishedTime.isAfter(inProgressTime)) {
                        long seconds = Duration.between(inProgressTime, finishedTime).getSeconds();
                        durationsInSeconds.add(seconds);
                    }

                    inProgressStatus = null;
                }
            }
        }

        if (durationsInSeconds.isEmpty()) return 0;

        double result;
        switch (mode) {
            case MIN -> result = Collections.min(durationsInSeconds);
            case MAX -> result = Collections.max(durationsInSeconds);
            case AVG -> result = durationsInSeconds.stream().mapToDouble(Long::doubleValue).average().orElse(0);
            default -> result = durationsInSeconds.stream().mapToLong(Long::longValue).average().orElse(0);
        }

        return switch (unit) {
            case MINUTES -> result / 60;
            case HOURS -> result / 3600;
            default -> result;
        };
    };
}
