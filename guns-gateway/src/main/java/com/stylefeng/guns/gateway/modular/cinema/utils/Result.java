package com.stylefeng.guns.gateway.modular.cinema.utils;

import java.util.HashMap;

public class Result {


    public static HashMap response(int status, Object data){
        HashMap map = new HashMap();
        if(status==0){
            String imgPre="http://img.meetingshop.cn/";
            map.put("imgPre",imgPre);
            map.put("data",data);
        }else if(status==1){
            map.put("msg","影院信息查询失败");
        }else {
            status=999;
            map.put("msg","系统出现异常，请联系管理员");
        }
        map.put("status",status);
        return map;
    }
}
