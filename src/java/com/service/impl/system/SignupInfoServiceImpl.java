package com.service.impl.system;

import com.entity.SignupInfo;
import com.service.impl.common.BaseServiceImpl;
import com.service.system.SignupInfoService;
import com.util.CollectionUtil;
import com.util.StringUtil;
import com.vo.Page;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by victor on 2018/4/11.
 */
@Service("signupInfoService")
public class SignupInfoServiceImpl extends BaseServiceImpl<SignupInfo> implements SignupInfoService{
    @Override
    public Map<String, Object> getParamMap() {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("xlsName", "报名信息表.xls");
        paramMap.put("queryHeaders", "序号,姓名,性别,电话,身份证,报名时间,状态,物资领取号,是否已领取物资");
        paramMap.put("queryKeys", "number,name,sex,telephone,idCard,signupTime,status,identifyNo,isTakeMaterial");
        return paramMap;
    }

    @Override
    public Page getData(Map<String, String> paramMap) {
        String pageSizeStr = paramMap.get("pageSize");
        String pageNumStr=paramMap.get("pageNum");
        String name=paramMap.get("name");
        String signupTimeBegin=paramMap.get("signupTimeBegin");
        String signupTimeEnd=paramMap.get("signupTimeEnd");
        String status=paramMap.get("status");
        String activityId=paramMap.get("activityId");
        String number=paramMap.get("number");
        String identifyNo=paramMap.get("identifyNo");
        String isTakeMaterial=paramMap.get("isTakeMaterial");

        int pageSize = 100000;
        int pageNum = 1;
        if(!StringUtil.isEmptyString(pageSizeStr)){
            pageSize = Integer.parseInt(pageSizeStr);
        }
        if(!StringUtil.isEmptyString(pageNumStr)){
            pageNum = Integer.parseInt(pageNumStr);
        }

        StringBuffer condition = new StringBuffer();
        if(!StringUtil.isEmptyString(name)){
            condition.append(" and si.name like '%" + name + "%'");
        }
        if(!StringUtil.isEmptyString(number)){
            condition.append(" and si.number = '" + number + "'");
        }
        if(!StringUtil.isEmptyString(identifyNo)){
            condition.append(" and io.identify_No = '" + identifyNo + "'");
        }
        if(!StringUtil.isEmptyString(isTakeMaterial)){
            condition.append(" and si.is_Take_Material = " + isTakeMaterial);
        }
        if(!StringUtil.isEmptyString(signupTimeBegin)){
            condition.append(" and si.signup_Time >= '" + signupTimeBegin +" 00:00:00'");
        }
        if(!StringUtil.isEmptyString(signupTimeEnd)){
            condition.append(" and si.signup_Time <= '" + signupTimeEnd +" 23:59:59'");
        }
        if(!StringUtil.isEmptyString(status)){
            condition.append(" and si.status = " + status);
        }
        if(!StringUtil.isEmptyString(activityId)){
            condition.append(" and si.activity_Id = " + activityId);
        }

        StringBuffer sql = new StringBuffer();
        sql.append("select si.signup_info_id signupInfoId,si.number number,si.name name \n");
        sql.append(",si.id_card idCard,si.telephone telephone,signup_Time signupTime \n");
        sql.append(",case si.sex when 1 then '男' else '女' end sex \n");
        sql.append(",case si.is_Take_Material when 1 then '是' else '否' end isTakeMaterial \n");
        sql.append(",case si.status when 1 then '未支付' when 2 then '支付中' when 3 then '已支付'else '' end status \n");
        sql.append(",ifnull(io.identify_No,'' ) identifyNo \n");
        sql.append("from signup_info si \n");
        sql.append(" left join identify_order io on io.openid=si.openid and io.activity_id=si.activity_id \n");
        sql.append(" where 1=1 \n");
        sql.append( condition);

        List list = super.getSession().createSQLQuery(sql.toString()).setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP)
                .setMaxResults(pageSize).setFirstResult((pageNum-1)*pageSize).list();
        Page page = new Page();
        page.setRows(list);
        sql = new StringBuffer();
        sql.append("select count(1) \n");
        sql.append("from signup_info si \n");
        sql.append(" left join identify_order io on io.openid=si.openid and io.activity_id=si.activity_id \n");
        sql.append(" where 1=1 \n");
        sql.append( condition);
        list = super.getSession().createSQLQuery(sql.toString()).list();
        int total = 0;
        if(!CollectionUtil.isEmptyCollection(list)){
            total = Integer.parseInt(list.get(0).toString());
        }
        page.setTotal(total);
        return page;
    }
}
