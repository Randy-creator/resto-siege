package org.restaurantmanagement.resto.service.Branch;

import org.restaurantmanagement.resto.entity.model.Branch;
import org.restaurantmanagement.resto.repository.BranchDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchServiceImpl implements BranchService{
    private final BranchDaoImpl branchDao;

    public BranchServiceImpl(BranchDaoImpl branchDao) {
        this.branchDao = branchDao;
    }

    @Override
    public List<Branch> getAll() {
         try {
             return branchDao.getBranches();
        } catch (Exception e) {
             throw new RuntimeException(e);
         }
    }
}
