package com.stylefeng.guns.rest.Respond;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


//保证json值为null时不序列化
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseFilmVo<M> {
    private String msg;
    private String imgPre;
    private int nowPage ;
    private int totalPage ;
    private int status ;
    private M data;


    public ResponseFilmVo() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public M getData() {
        return data;
    }

    public String getImgPre() {
        return imgPre;
    }

    public void setImgPre(String imgPre) {
        this.imgPre = imgPre;
    }

    public int getNowPage() {
        return nowPage;
    }

    public void setNowPage(int nowPage) {
        this.nowPage = nowPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public void setData(M data) {
        this.data = data;
    }


}
