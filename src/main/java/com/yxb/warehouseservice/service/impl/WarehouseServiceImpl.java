package com.yxb.warehouseservice.service.impl;

import com.yxb.warehouseservice.dao.HmilyLogDao;
import com.yxb.warehouseservice.dao.WarehouseDao;
import com.yxb.warehouseservice.service.IWarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.Hmily;
import org.dromara.hmily.common.exception.HmilyRuntimeException;
import org.dromara.hmily.core.concurrent.threadlocal.HmilyTransactionContextLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
@Slf4j
public class WarehouseServiceImpl implements IWarehouseService {
    @Resource
    public WarehouseDao warehouseDao;
    @Autowired
    public HmilyLogDao hmilyLogDao;

    @Override
    @Transactional
    @Hmily(confirmMethod = "confirmWarehouse", cancelMethod = "cancelWarehouse")
    public Boolean tryWarehouse(String productId, Integer amount) {
        log.error("******** confirmWarehouse() begin try...");
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        int existTry = hmilyLogDao.isExistTry(transId);
        //幂等校验
        if (existTry > 0) {
            log.info("******** tryWarehouse() 已经执行try，无需重复执行..." + transId);
            return null;
        }
        int existConfirm = hmilyLogDao.isExistConfirm(transId);
        int existCancel = hmilyLogDao.isExistCancel(transId);
        //悬挂校验
        if (existCancel > 0 || existConfirm > 0) {
            log.info("******** tryWarehouse() 已经执行confirm或cancel，悬挂处理..." + transId);
            return null;
        }
        //业务逻辑执行
        int b = warehouseDao.tryWarehouse(productId, amount);
        if (b <= 0) {
            log.info("******** tryWarehouse() 扣减库存失败..." + transId);
            throw new HmilyRuntimeException("");
        }
        //记录try日志
        hmilyLogDao.addTry(transId);
        return null;
    }

    @Override
    @Transactional
    public Boolean confirmWarehouse(String productId, Integer amount) {
        log.error("******** confirmWarehouse() begin commit...");
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        int existConfirm = hmilyLogDao.isExistConfirm(transId);
        //幂等判断
        if (existConfirm > 0) {
            log.info("******** confirmWarehouse() begin commit..." + transId);
            return null;
        }
        warehouseDao.confirmWarehouse(productId, amount);
        //记录confirm日志
        hmilyLogDao.addConfirm(transId);
        return null;
    }

    @Override
    @Transactional
    public Boolean cancelWarehouse(String productId, Integer amount) {
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        int existTry = hmilyLogDao.isExistTry(transId);
        //空回滚判断,没有try阶段，不用回滚
        if (existTry == 0) {
            log.info("******** cancelWarehouse() try阶段失败... 无需rollback " + transId);
            return null;
        }
        int existCancel = hmilyLogDao.isExistCancel(transId);
        //幂等判断，已经硅回滚过不需要再回滚
        if (existCancel > 0) {
            log.info("******** cancelWarehouse() 已经执行过rollback... 无需再次rollback " + transId);
            return null;
        }
        //回滚逻辑
        warehouseDao.cancelWarehouse(productId, amount);
        //记录回滚日志
        hmilyLogDao.addCancel(transId);
        return null;
    }
}
