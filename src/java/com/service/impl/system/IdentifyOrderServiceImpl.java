package com.service.impl.system;

import com.entity.IdentifyOrder;
import com.exception.MessageException;
import com.service.impl.common.BaseServiceImpl;
import com.service.system.IdentifyOrderService;
import org.springframework.stereotype.Service;

/**
 * Created by victor on 2018/4/16.
 */
@Service("identifyOrderService")
public class IdentifyOrderServiceImpl extends BaseServiceImpl<IdentifyOrder> implements IdentifyOrderService {
    @Override
    public int confirmReceive(String identifyOrder, String numer) throws MessageException{
        return 0;
    }
}
