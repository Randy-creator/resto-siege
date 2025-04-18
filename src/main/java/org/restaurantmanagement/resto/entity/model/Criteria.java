package org.restaurantmanagement.resto.entity.model;

import lombok.Getter;
import org.restaurantmanagement.resto.entity.Enum.LogicalOperator;

@Getter
public class Criteria {
    private final String columnName;
    private final Object columnValue;
    private Object secondValue;
    private final String operator;
    private final LogicalOperator logicalOperator;

    public Criteria(String columnName, Object columnValue, Object secondValue, String operator, LogicalOperator logicalOperator) {
        this.columnName = columnName;
        this.columnValue = columnValue;
        this.secondValue = secondValue;
        this.operator = operator;
        this.logicalOperator = logicalOperator;
    }

    public Criteria(String columnName, Object columnValue, String operator, LogicalOperator logicalOperator) {
        this.columnName = columnName;
        this.columnValue = columnValue;
        this.operator = operator;
        this.logicalOperator = logicalOperator;
    }
}
