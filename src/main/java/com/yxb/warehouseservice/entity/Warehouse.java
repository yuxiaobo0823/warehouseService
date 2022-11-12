package com.yxb.warehouseservice.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Warehouse implements Serializable {
    public Integer id;
    public String productId;
    public Integer totalInventory;
    public Integer lockInventory;
    public Integer validInventory;


}
