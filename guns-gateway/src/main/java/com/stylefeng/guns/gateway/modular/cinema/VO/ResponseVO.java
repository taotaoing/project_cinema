package com.stylefeng.guns.gateway.modular.cinema.VO;

import lombok.Data;

@Data
public class ResponseVO<M> {
    // 返回状态 0-成功
    private Integer status;
    // 返回信息
    private String msg;
    // 返回数据实体;
    private M data;
    // 图片前缀
    private String imgPre;

    // 分页使用
    private Integer nowPage;
    private Integer totalPage;

    private ResponseVO(){}

    public static<M> ResponseVO success(int nowPage,int totalPage,String imgPre,M m){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setData(m);
        responseVO.setImgPre(imgPre);
        responseVO.setTotalPage(totalPage);
        responseVO.setNowPage(nowPage);

        return responseVO;
    }

    public static<M> ResponseVO success(String imgPre,M m){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setData(m);
        responseVO.setImgPre(imgPre);

        return responseVO;
    }

    public static<M> ResponseVO success(M m){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setData(m);

        return responseVO;
    }

    public static<M> ResponseVO success(String msg){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setMsg(msg);

        return responseVO;
    }

    public static<M> ResponseVO serviceFail(String msg){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(1);
        responseVO.setMsg(msg);

        return responseVO;
    }

    public static<M> ResponseVO appFail(String msg){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(999);
        responseVO.setMsg(msg);

        return responseVO;
    }

}
