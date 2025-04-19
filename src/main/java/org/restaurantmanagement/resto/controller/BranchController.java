package org.restaurantmanagement.resto.controller;

import org.restaurantmanagement.resto.entity.model.Branch;
import org.restaurantmanagement.resto.service.Branch.BranchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/branches")
public class BranchController {
    @Autowired
    private BranchServiceImpl branchService;

    @GetMapping("/")
    public ResponseEntity<Object> getAll() {
        try {
            List<Branch> branches = branchService.getAll();
            return ResponseEntity.ok(branches);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}
