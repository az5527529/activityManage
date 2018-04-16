package com.controller.system;

import com.exception.MessageException;
import com.service.system.IdentifyOrderService;
import com.util.WebUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by victor on 2018/4/16.
 */
@Controller
@RequestMapping("/identifyOrder")
public class IdentifyOrderController {
    @Resource
    private IdentifyOrderService identifyOrderService;
    @RequestMapping(value = "/showData", method = RequestMethod.POST)
    public void showData(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        String identifyOrder = request.getParameter("identifyOrder");
        String number = request.getParameter("number");
        int result = 0;
        JSONObject o = new JSONObject();
        try {
            result = this.identifyOrderService.confirmReceive(identifyOrder,number);
        } catch (MessageException e) {
            e.printStackTrace();
            o.put("errorMsg",e.getErrorMsg());
        }
        o.put("success",result);
        WebUtil.outputPage(request, response, o.toString());
    }
}