package com.util;

import java.util.*;

/**
 * Created by victor on 2018/4/3.
 */
public class Tools {

    public static java.util.Date toDateTime(String strDate) {
        java.util.Date datReturn = null;
        if (strDate==null) return datReturn ;
        try {
            String strForm="yyyy-MM-dd";
            if (strDate.indexOf(":") >0 ) strForm="yyyy-MM-dd HH:mm";
            if (strDate.indexOf(":") >0 && strDate.lastIndexOf(":")>strDate.indexOf(":"))
                strForm="yyyy-MM-dd HH:mm:ss";

            java.text.SimpleDateFormat tmpsdf = new java.text.SimpleDateFormat(strForm);
            datReturn = tmpsdf.parse(strDate);
        }catch (Exception ex) {}
        return datReturn ;
    }
    public static long dateDiff(String interval,java.util.Date date1, java.util.Date date2) {
        long intReturn=-1000000000 ;
        if (date1==null || date2==null || interval==null) return intReturn ;
        try {
            java.util.Calendar cal1 = java.util.Calendar.getInstance();
            java.util.Calendar cal2 = java.util.Calendar.getInstance();

            // different date might have different offset
            cal1.setTime(date1);
            long ldate1 = date1.getTime() + cal1.get(java.util.Calendar.ZONE_OFFSET)
                    + cal1.get(java.util.Calendar.DST_OFFSET);

            cal2.setTime(date2);
            long ldate2 = date2.getTime() + cal2.get(java.util.Calendar.ZONE_OFFSET)
                    + cal2.get(java.util.Calendar.DST_OFFSET);

            // Use integer calculation, truncate the decimals
            long hr1   = (long)(ldate1/3600000);
            long hr2   = (long)(ldate2/3600000);

            long days1 = (long)hr1/24;
            long days2 = (long)hr2/24;

            long yearDiff  = cal2.get(java.util.Calendar.YEAR)
                    - cal1.get(java.util.Calendar.YEAR);
            long monthDiff = yearDiff * 12 + cal2.get(java.util.Calendar.MONTH)
                    - cal1.get(java.util.Calendar.MONTH);
            long dateDiff  = days2 - days1;
            long hourDiff  = hr2 - hr1;
            long minuteDiff  =  (long)(ldate2/60000) - (long)(ldate1/60000);
            long secondDiff  =  (long)(ldate2/1000) - (long)(ldate1/1000);

            if ("Y".equals(interval)){
                intReturn = yearDiff;
            }
            else if ("M".equals(interval)){
                intReturn = monthDiff;
            }
            else if ("D".equals(interval)){
                intReturn = dateDiff;
            }
            else if ("H".equals(interval)){
                intReturn = hourDiff;
            }
            else if ("m".equals(interval)){
                intReturn = minuteDiff;
            }
            else if ("S".equals(interval)){
                intReturn = secondDiff;
            }
        }
        catch (Exception ex) {
        }
        return intReturn;
    }

    /* String version of dateDiff(String interval,java.util.Date date1, java.util.Date date2) */
    public static long dateDiff(String interval,String strDate1, String strDate2) {
        return dateDiff(interval,toDateTime(strDate1),toDateTime(strDate2));
    }
}
