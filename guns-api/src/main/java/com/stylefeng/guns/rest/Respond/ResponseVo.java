package com.stylefeng.guns.rest.Respond;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

//保证json值为null时不序列化
@JsonInclude(JsonInclude.Include.NON_NULL)
public  class ResponseVo<M> {
    private String msg;
    private int status;
    private M data;

    public ResponseVo() {
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

    public void setData(M data) {
        this.data = data;
    }

    public static<M> ResponseVo success(M m){
        ResponseVo<M> objectResponseVo = new ResponseVo<>();
        objectResponseVo.setStatus(0);
        objectResponseVo.setData(m);
        return objectResponseVo;
    }

    public static<M> ResponseVo Fail(String msg){
        ResponseVo<M> objectResponseVo = new ResponseVo<>();
        objectResponseVo.setStatus(1);
        objectResponseVo.setMsg(msg);

        return objectResponseVo;
    }public static<M> ResponseVo unknown(String msg){
        ResponseVo<M> objectResponseVo = new ResponseVo<>();
        objectResponseVo.setStatus(1);
        objectResponseVo.setMsg(msg);
        return objectResponseVo;
    }
}
