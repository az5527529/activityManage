package com.service.impl.system;

import com.entity.SignupInfo;
import com.service.impl.common.BaseServiceImpl;
import com.service.system.ShowDataService;
import com.util.CollectionUtil;
import com.util.StringUtil;
import com.vo.Page;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by victor on 2018/4/12.
 */
@Service("showDataService")
public class ShowDataServiceImpl extends BaseServiceImpl<SignupInfo> implements ShowDataService {
    @Override
    public Map<String, Object> getParamMap() {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("xlsName", "数据统计表.xls");
        paramMap.put("queryHeaders", "已报名人数,已支付人数,未支付人数,支付中人数");
        paramMap.put("queryKeys", "totalNum,payedNum,toPayNum,payingNum");
        return paramMap;
    }

    @Override
    public Page getData(Map<String, String> paramMap) {
        String pageSizeStr = paramMap.get("pageSize");
        String pageNumStr=paramMap.get("pageNum");
        String signupTimeBegin=paramMap.get("signupTimeBegin");
        String signupTimeEnd=paramMap.get("signupTimeEnd");
        String activityId=paramMap.get("activityId");

        int pageSize = 100000;
        int pageNum = 1;
        if(!StringUtil.isEmptyString(pageSizeStr)){
            pageSize = Integer.parseInt(pageSizeStr);
        }
        if(!StringUtil.isEmptyString(pageNumStr)){
            pageNum = Integer.parseInt(pageNumStr);
        }

        StringBuffer condition = new StringBuffer();

        if(!StringUtil.isEmptyString(signupTimeBegin)){
            condition.append(" and si.signup_Time >= '" + signupTimeBegin +" 00:00:00'");
        }
        if(!StringUtil.isEmptyString(signupTimeEnd)){
            condition.append(" and si.signup_Time <= '" + signupTimeEnd +" 23:59:59'");
        }

        if(!StringUtil.isEmptyString(activityId)){
            condition.append(" and si.activity_Id = " + activityId);
        }

        StringBuffer sql = new StringBuffer();
        sql.append("select si.signup_info_id signupInfoId,cast(count(1) as char) totalNum \n");
        sql.append(",cast(ifnull(sum(case si.status when 1 then 1 else 0 end),0) as char) toPayNum \n");
        sql.append(",cast(ifnull(sum(case si.status when 2 then 1 else 0 end),0) as char) payingNum \n");
        sql.append(",cast(ifnull(sum(case si.status when 3 then 1 else 0 end),0) as char) payedNum \n");
        sql.append("from signup_info si \n");
        sql.append(" where 1=1 \n");
        sql.append( condition);

        List list = super.getSession().createSQLQuery(sql.toString()).setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP)
                .setMaxResults(pageSize).setFirstResult((pageNum-1)*pageSize).list();
        Page page = new Page();
        page.setRows(list);
        sql = new StringBuffer();
        sql.append("select count(1) \n");
        sql.append("from signup_info si \n");
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
