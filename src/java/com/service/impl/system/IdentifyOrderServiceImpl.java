package com.service.impl.system;

import com.entity.IdentifyOrder;
import com.entity.SignupInfo;
import com.exception.MessageException;
import com.service.impl.common.BaseServiceImpl;
import com.service.system.IdentifyOrderService;
import com.util.CollectionUtil;
import com.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by victor on 2018/4/16.
 */
@Service("identifyOrderService")
public class IdentifyOrderServiceImpl extends BaseServiceImpl<IdentifyOrder> implements IdentifyOrderService {
    @Override
    public int confirmReceive(String identifyNo, String numer) throws MessageException{
        int result = 1;
        //当物资领取号不为空的时候，将其绑定的已付款的报名信息全部更新为已领取
        if(!StringUtil.isEmptyString(identifyNo)){
            String sql = "update signup_info t join identify_order s on s.activity_id=t.activity_id and s.openid=t.openid" +
                    " set t.is_Take_Material=1 where t.is_Take_Material=0 and s.identify_no=:identifyNo and t.status=:status";
            result = super.getSession().createSQLQuery(sql).setString("identifyNo",identifyNo).setInteger("status",3).executeUpdate();
            if(result == 0){
                throw new MessageException("物资领取号不存在或者重复领取");
            }
        }else if (!StringUtil.isEmptyString(numer)){//通过序号确认报名信息已领取
            String hql = "from SignupInfo where number=:number";
            List<SignupInfo> list = super.getSession().createQuery(hql).setString("number",numer).list();
            if(CollectionUtil.isEmptyCollection(list)){
                throw new MessageException("序号不存在");
            }
            SignupInfo signupInfo = list.get(0);
            if(signupInfo.getIsTakeMaterial()){
                throw new MessageException("该序号已领取物资，请勿重复领取");
            }
            signupInfo.setIsTakeMaterial(true);
            super.getSession().update(signupInfo);
        }else {
            throw new MessageException("物资领取号与序号都为空");
        }
        return result;
    }

    @Override
    public int cancelReceive(String identifyNo, String numer) throws MessageException {
        int result = 1;
        //当物资领取号不为空的时候，将其绑定的已付款的报名信息全部更新为未领取
        if(!StringUtil.isEmptyString(identifyNo)){
            String sql = "update signup_info t join identify_order s on s.activity_id=t.activity_id and s.openid=t.openid" +
                    " set t.is_Take_Material=0 where t.is_Take_Material=1 and s.identify_no=:identifyNo  and t.status=:status";
            result = super.getSession().createSQLQuery(sql).setString("identifyNo",identifyNo).setInteger("status",3).executeUpdate();
            if(result == 0){
                throw new MessageException("物资领取号不存在或者重复取消");
            }
        }else if (!StringUtil.isEmptyString(numer)){//通过序号确认报名信息已领取
            String hql = "from SignupInfo where number=:number";
            List<SignupInfo> list = super.getSession().createQuery(hql).setString("number",numer).list();
            if(CollectionUtil.isEmptyCollection(list)){
                throw new MessageException("序号不存在");
            }
            SignupInfo signupInfo = list.get(0);
            if(!signupInfo.getIsTakeMaterial()){
                throw new MessageException("该序号未领取物资");
            }
            signupInfo.setIsTakeMaterial(false);
            super.getSession().update(signupInfo);
        }else {
            throw new MessageException("物资领取号与序号都为空");
        }
        return result;
    }
}
