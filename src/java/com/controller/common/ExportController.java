package com.controller.common;

import com.service.common.Reportable;
import com.util.StringUtil;
import com.vo.Page;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by victor on 2018/3/27.
 */
@Controller
@RequestMapping("/export")
public class ExportController {
    @RequestMapping(value = "/exportToExcel", method = RequestMethod.POST)
    public void exportToExcel(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        String serviceName = request.getParameter("serviceName");//服务名
        Map<String,String[]> parameterMap = request.getParameterMap();
        Map paramMap = new HashMap();
        for(Iterator iter = parameterMap.entrySet().iterator(); iter.hasNext();) {
            Map.Entry element = (Map.Entry) iter.next();
            //key值
            Object strKey = element.getKey();
            //value,数组形式
            String[] value = (String[]) element.getValue();
            paramMap.put(strKey,value[0]);
        }
        try {
            WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
            Reportable service = (Reportable)wc.getBean(serviceName);
            //获取表头以及标题信息
            Map<String, Object> map = service.getParamMap();
            //获取数据
            Page result = service.getData(paramMap);
            displayOS(request,response,result.getRows(),map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 结果集以流的方式写到excel
     * @param response	返回浏览器
     * @param result	报表结果集
     * @return 页面跳转
     */
    protected static void displayOS(HttpServletRequest request, HttpServletResponse response, List<Map<String,String>> result, Map<String,Object> paramMap) {
        String xlsName = paramMap.get("xlsName") == null ? "" : paramMap.get("xlsName").toString();
        String xlsNameReq = request.getParameter("xlsName");//前端传来的文件名称设置，如果前端使用了，则用这个
        if(!StringUtil.isEmptyString(xlsNameReq)){
            xlsName = xlsNameReq;
        }
        OutputStream os = null;

        //文件名称
        if(StringUtil.isEmptyString(xlsName)) {
            xlsName = "report.xls";
        } else {
            try {
                xlsName = URLDecoder.decode(xlsName,"UTF-8");
            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();
            }
        }

        try {
            response.setContentType("application/msexcel");
            os = response.getOutputStream();
            response.setHeader("Content-Disposition", "attachment;" + " filename=" + new String(xlsName.getBytes("GBK"), "ISO-8859-1"));
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet();
            String queryHeaders = paramMap.get("queryHeaders") == null ? "" : paramMap.get("queryHeaders").toString();
            String queryKeys = paramMap.get("queryKeys") == null ? "" : paramMap.get("queryKeys").toString();
            //头信息
            String[] headers = queryHeaders.split(",");
            //每一列对应的属性名
            String[] keys = queryKeys.split(",");
            //列数
            int columnNum = headers.length;
            int row = 0;
            HSSFRow dataRow = null;
            HSSFCell cell = null;
            //第一行输出表头
            dataRow = sheet.createRow(row++);
            for(int i = 0; i < columnNum; i++){
                cell = dataRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
            //输出列表信息
            for(int i = 0; i < result.size(); i++){
                dataRow = sheet.createRow(row++);
                Map<String,String> data = result.get(i);
                for(int j = 0; j < columnNum; j++){
                    cell = dataRow.createCell(j);
                    cell.setCellValue(data.get(keys[j]));
                }
            }
            wb.write(os);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }finally{


            if(os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
