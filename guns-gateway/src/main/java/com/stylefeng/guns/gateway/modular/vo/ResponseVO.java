package com.stylefeng.guns.gateway.modular.vo;

import lombok.Data;

/**
 * @author 申涛涛
 * @date 2019/8/31 11:00
 */
@Data
public class ResponseVO<M> {
    //返回状态  0-成功，1-失败，999-系统异常
    private Integer status;
    //返回信息
    private String msg;
    //返回实体
    private M data;
    // 图片前缀
    private String imgPre;
    // 分页使用
    private Integer nowPage;
    private Integer totalPage;

    public ResponseVO() {
    }


    public static<M> ResponseVO success(String imgPre, M m) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setData(m);
        responseVO.setImgPre(imgPre);

        return responseVO;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public M getData() {
        return data;
    }

    public void setData(M data) {
        this.data = data;
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

    /**
     * 过期 3
     * @creator shentaotao
     * @creat date 2019/9/5 15:10
     * @param
     * @return com.stylefeng.guns.gateway.modular.vo.ResponseVO
     * @throws
     * @since
     */
    public static<M> ResponseVO expire() {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(3);
        responseVO.setMsg("token已过期");
        return responseVO;
    }
}
