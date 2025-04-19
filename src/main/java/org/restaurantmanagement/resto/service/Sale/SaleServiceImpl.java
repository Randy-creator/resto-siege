package org.restaurantmanagement.resto.service.Sale;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.restaurantmanagement.resto.entity.model.Branch;
import org.restaurantmanagement.resto.entity.model.Sale;
import org.restaurantmanagement.resto.repository.BranchDaoImpl;
import org.restaurantmanagement.resto.repository.SaleCrudImpl;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.logging.Logger;

@Service
public class SaleServiceImpl implements SaleService {

    private static final Logger logger = Logger.getLogger(SaleServiceImpl.class.getName());
    private final SaleCrudImpl saleCrud;
    private final BranchDaoImpl branchDao;

    public SaleServiceImpl(SaleCrudImpl saleCrud, BranchDaoImpl branchDao) {
        this.saleCrud = saleCrud;
        this.branchDao = branchDao;
    }

    @Override
    public void getSales(String startTime, String endTime) {
        List<Branch> branches = branchDao.getBranches();
        for (Branch branch : branches) {
            try {
                String uri = branch.getBranch_url()
                        + "/sales?startTime=" + startTime
                        + "&endTime=" + endTime;

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
                    List<Sale> sales = objectMapper.readValue(response.body(), new TypeReference<>() {
                    });

                    if (sales.isEmpty()) {
                        logger.info("No sales found.");
                    } else {
                        saleCrud.saveAll(sales, branch.getBranch_name());
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
}