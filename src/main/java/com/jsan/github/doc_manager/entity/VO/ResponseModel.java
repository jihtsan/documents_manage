package com.jsan.github.doc_manager.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @description: 业务返回数据
 * @author: Jihaotian
 * @create: 2018-04-14 15:35
 **/

@ApiModel(description = "业务对象")
public class ResponseModel<T> {
    public static Integer NOTAVAILABLE = 1;
    public static Integer AVAILABLE = 0;

    @ApiModelProperty(value = "返回状态，0成功，1失败", required = true)
    public Integer statusCode = NOTAVAILABLE;
    @ApiModelProperty(value = "http状态值", required = true)
    public String httpStatus = "00";
    @ApiModelProperty(value = "消息提示", required = true)
    public String message;
    @ApiModelProperty(value = "返回数据", required = false)
    public T data;

    public ResponseModel(Integer statusCode, String httpStatus, String message, T data) {
        this.statusCode = statusCode;
        this.httpStatus = httpStatus;
        this.message = message;
        this.data = data;
    }

    public ResponseModel(String message, Integer statusCode, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public ResponseModel() {

    }

    public String getMessage() {
        return message;
    }

    public ResponseModel<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public ResponseModel<T> setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResponseModel<T> setData(T data) {
        this.data = data;
        return this;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public ResponseModel<T> setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }


    @Override
    public String toString() {
        return "ResponseModel{" +
                "statusCode=" + statusCode +
                ", httpStatus='" + httpStatus + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }


}
