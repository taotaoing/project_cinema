package com.stylefeng.guns.gateway.modular.vo;

import lombok.Data;

/**
 * @author 申涛涛
 * @date 2019/8/31 11:00
 */
@Data
public class ResponseVO<M> {
    //返回状态  0-成功，1-失败，999-系统异常
    private int status;
    //返回信息
    private String msg;
    //返回实体
    private M data;
    // 图片前缀
    private String imgPre;
    // 分页使用
    private int nowPage;
    private int totalPage;

    public ResponseVO() {
    }


    public static<M> ResponseVO success(String imgPre, M m) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setData(m);
        responseVO.setImgPre(imgPre);

        return responseVO;
    }

    public static<M> ResponseVO success(M m) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setData(m);

        return responseVO;
    }

    public static<M> ResponseVO success(String msg) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setMsg(msg);

        return responseVO;
    }

    public static<M> ResponseVO serviceFail(String msg) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(1);
        responseVO.setMsg(msg);

        return responseVO;
    }

    public static<M> ResponseVO appFail(String msg) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(1);
        responseVO.setMsg(msg);

        return responseVO;
    }
}
