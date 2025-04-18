package org.restaurantmanagement.resto.mapper;

import org.restaurantmanagement.resto.entity.DTO.stockMovement.StockMovementDto;
import org.restaurantmanagement.resto.entity.model.StockMovement;
import org.springframework.stereotype.Component;

@Component
public class StockMovementMapper {
    public StockMovementDto mapToDto(StockMovement stockMovement){
        StockMovementDto stockMovementDto = new StockMovementDto();
        stockMovementDto.setMovementType(stockMovement.getMovementType());
        stockMovementDto.setMovementDate(stockMovement.getMovementDate());
        stockMovementDto.setUnit(stockMovement.getUnit());
        stockMovementDto.setMovementQuantity(stockMovement.getMovementQuantity());
        return stockMovementDto;
    }

}
