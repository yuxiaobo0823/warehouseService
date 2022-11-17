package com.yxb.warehouseservice.dao;

import com.yxb.warehouseservice.entity.Warehouse;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

public interface WarehouseDao {

    /**
     * 预扣减商品的库存
     *
     * @param productId
     * @return
     */
    public Integer tryWarehouse(String productId, Integer amount);

    /**
     * 提交确认库存
     *
     * @param productId
     * @return
     */
    public Integer confirmWarehouse(String productId, Integer amount);

    /**
     * 回滚库存
     *
     * @param productId
     * @return
     */
    public Integer cancelWarehouse(String productId, Integer amount);


}
