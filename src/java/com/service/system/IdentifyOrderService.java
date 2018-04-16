package com.service.system;

import com.entity.IdentifyOrder;
import com.exception.MessageException;
import com.service.common.BaseService;

/**
 * Created by victor on 2018/4/16.
 */
public interface IdentifyOrderService extends BaseService<IdentifyOrder> {
    /**
     * 通过识别号或者报名序号来确认领取物资
     * @param identifyOrder
     * @param numer
     * @return
     */
    public int confirmReceive(String identifyOrder,String numer) throws MessageException;
}
