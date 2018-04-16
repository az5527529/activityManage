package com.service.common;

import com.vo.Page;

import java.util.Map;

/**
 * Created by victor on 2018/3/27.
 */
public interface Reportable {
    //获取表头
    public Map<String,Object> getParamMap();

    //获取结果
    public Page getData(Map<String, String> paramMap);
}
