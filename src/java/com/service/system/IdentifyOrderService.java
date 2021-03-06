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
     * @param identifyNo
     * @param numer
     * @return
     */
    public int confirmReceive(String identifyNo,String numer) throws MessageException;

    /**
     * 取消领取物资
     * @param identifyNo
     * @param numer
     * @return
     * @throws MessageException
     */
    public int cancelReceive(String identifyNo,String numer) throws MessageException;
}
