package com.stylefeng.guns.api.Respond;

import com.fasterxml.jackson.annotation.JsonInclude;

//保证json值为null时不序列化
@JsonInclude(JsonInclude.Include.NON_NULL)
public  class ResponseVo<M> {
    private String msg;
    private int status;
    private M data;
    //分页使用
    private int nowPage;
    private int totalPage;

    public ResponseVo() {
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
    }

    public static<M> ResponseVo success(String msg){
        ResponseVo<M> objectResponseVo = new ResponseVo<>();
        objectResponseVo.setStatus(0);
        objectResponseVo.setMsg(msg);
        return objectResponseVo;
    }

    public static<M> ResponseVo unknown(String msg,int status){
        ResponseVo<M> objectResponseVo = new ResponseVo<>();
        objectResponseVo.setStatus(status);
        objectResponseVo.setMsg(msg);
        return objectResponseVo;
    }

    public static<M> ResponseVo success(int nowPage,int totalPage,String imgPre,M m){
        ResponseVo responseVO = new ResponseVo();
        responseVO.setStatus(0);
        responseVO.setData(m);
        responseVO.setTotalPage(totalPage);
        responseVO.setNowPage(nowPage);

        return responseVO;
    }
}
