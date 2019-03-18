package com.guli.common.vo;

import com.guli.common.constants.ResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：mei
 * @date ：Created in 2019/2/23 0023 下午 16:17
 * @description：全局统一返回结果
 * @modified By：
 * @version: $
 */
@Data
@ApiModel(value = "全局统一返回结果")
public class R {

    @ApiModelProperty(value = "是否成功")
    private Boolean success;
    @ApiModelProperty(value = "返回码")
    private Integer code;
    @ApiModelProperty(value = "返回信息")
    private String message;
    @ApiModelProperty(value = "返回参数")
    private Map<String , Object> data =new HashMap<>();

    private R() {
    }

    public static R ok(){
        R r = new R();
        r.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());
        r.setCode(ResultCodeEnum.SUCCESS.getCode());
        r.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return r;
    }

    public static R error(){
        R r = new R();
        r.setSuccess(ResultCodeEnum.UNKNOW_REASON.getSuccess());
        r.setCode(ResultCodeEnum.UNKNOW_REASON.getCode());
        r.setMessage(ResultCodeEnum.UNKNOW_REASON.getMessage());
        return r;
    }

    public static R result(ResultCodeEnum resultCodeEnum){
        R r = new R();
        r.setSuccess(resultCodeEnum.getSuccess());
        r.setCode(resultCodeEnum.getCode());
        r.setMessage(resultCodeEnum.getMessage());
        return r;
    }

    public R success(Boolean success){
        this.success = success;
        return this;
    }

    public R code(Integer code){
        this.code = code;
        return this;
    }

    public R message(String message){
        this.message = message;
        return this;
    }

    public R data(String key , Object value){
        this.data.put(key,value);
        return this;
    }

    public R data(Map<String , Object> map){
        this.setData(map);
        return this;
    }

}
