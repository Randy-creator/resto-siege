package org.restaurantmanagement.resto.repository;

import org.restaurantmanagement.resto.entity.model.Branch;

import java.util.List;

public interface BranchDao {
    List<Branch> getBranches();
}
