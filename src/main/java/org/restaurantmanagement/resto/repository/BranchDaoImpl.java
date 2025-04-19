package org.restaurantmanagement.resto.repository;

import org.restaurantmanagement.resto.configuration.DbConnection;
import org.restaurantmanagement.resto.entity.model.Branch;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
@Repository
public class BranchDaoImpl implements BranchDao{
    private final DbConnection datasource;

    public BranchDaoImpl(DbConnection datasource) {
        this.datasource = datasource;
    }

    @Override
    public List<Branch> getBranches() {
        String sql = "SELECT branch_id, branch_name, branch_url, branch_api_key FROM branches;";
        List<Branch> branches = new ArrayList<>();
        try (Connection connection = datasource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()
        ){
            while (resultSet.next()){
                branches.add(new Branch(
                        resultSet.getLong("branch_id"),
                        resultSet.getString("branch_name"),
                        resultSet.getString("branch_url"),
                        resultSet.getString("branch_api_key")
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return branches;
    }
}
