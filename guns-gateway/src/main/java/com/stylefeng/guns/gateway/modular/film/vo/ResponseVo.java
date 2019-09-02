package com.stylefeng.guns.gateway.modular.film.vo;

import lombok.Data;

@Data
public class ResponseVo {
    private String imgPre;

    private String msg;

    private String nowPage;

    private String status;

    private String totalPage;

    private Object data;

    public ResponseVo() {
    }

    public static ResponseVo success(Object data){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setData(data);
        responseVo.setStatus(0+"");
        responseVo.setImgPre("http://img.meetingshop.cn/");
        return responseVo;
    }

    public String getImgPre() {
        return imgPre;
    }

    public void setImgPre(String imgPre) {
        this.imgPre = imgPre;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getNowPage() {
        return nowPage;
    }

    public void setNowPage(String nowPage) {
        this.nowPage = nowPage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
