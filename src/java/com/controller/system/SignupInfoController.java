package com.controller.system;

import com.service.system.ShowDataService;
import com.service.system.SignupInfoService;
import com.util.StringUtil;
import com.util.WebUtil;
import com.vo.Page;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by victor on 2018/4/11.
 */
@Controller
@RequestMapping("/signupInfo")
public class SignupInfoController {
    @Resource
    private SignupInfoService signupInfoService;
    @Resource
    private ShowDataService showDataService;
    @RequestMapping(value = "/searchSignupinfo", method = RequestMethod.POST)
    public void searchSignupinfo(HttpServletRequest request,
                              HttpServletResponse response) throws IOException {
        String pageSize = request.getParameter("rows");
        String pageNum=request.getParameter("page");
        String name=request.getParameter("name");
        String signupTimeBegin=request.getParameter("signupTimeBegin");
        String signupTimeEnd=request.getParameter("signupTimeEnd");
        String status=request.getParameter("status");
        String activityId=request.getParameter("activityId");
        String number=request.getParameter("number");

        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("pageSize",pageSize);
        paramMap.put("pageNum",pageNum);
        paramMap.put("name",name);
        paramMap.put("signupTimeBegin",signupTimeBegin);
        paramMap.put("signupTimeEnd",signupTimeEnd);
        paramMap.put("status",status);
        paramMap.put("activityId",activityId);
        paramMap.put("number",number);

        Page page = this.signupInfoService.getData(paramMap);
        JSONObject o = JSONObject.fromObject(page);
        WebUtil.outputPage(request, response, o.toString());
    }

    @RequestMapping(value = "/showData", method = RequestMethod.POST)
    public void showData(HttpServletRequest request,
                                 HttpServletResponse response) throws IOException {
        String pageSize = request.getParameter("rows");
        String pageNum=request.getParameter("page");
        String signupTimeBegin=request.getParameter("signupTimeBegin");
        String signupTimeEnd=request.getParameter("signupTimeEnd");
        String activityId=request.getParameter("activityId");

        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("pageSize",pageSize);
        paramMap.put("pageNum",pageNum);
        paramMap.put("signupTimeBegin",signupTimeBegin);
        paramMap.put("signupTimeEnd",signupTimeEnd);
        paramMap.put("activityId",activityId);

        Page page = this.showDataService.getData(paramMap);
        JSONObject o = JSONObject.fromObject(page);
        WebUtil.outputPage(request, response, o.toString());
    }
}
