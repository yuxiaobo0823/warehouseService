package com.yxb.warehouseservice.controller;

import com.yxb.warehouseservice.service.IWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/wareHouse")
public class WarehouseController {
    @Autowired
    public IWarehouseService  warehouseService;


    @RequestMapping("/tryWarehouse")
    public Boolean tryWarehouse(@RequestParam String productId, @RequestParam  Integer amount){
        return warehouseService.tryWarehouse(productId, amount);
    }
}
