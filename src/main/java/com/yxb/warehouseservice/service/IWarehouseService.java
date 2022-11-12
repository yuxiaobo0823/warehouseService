package com.yxb.warehouseservice.service;

public interface IWarehouseService {
    /**
     *
     * @param productId  商品编号
     * @param amount     扣减数量
     * @return
     */
    Boolean tryWarehouse(String productId, Integer amount);

    Boolean confirmWarehouse(String productId, Integer amount);

    Boolean cancelWarehouse(String productId, Integer amount);

}
