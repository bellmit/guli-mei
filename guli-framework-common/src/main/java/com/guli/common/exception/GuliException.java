package com.guli.common.exception;

import com.guli.common.constants.ResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：mei
 * @date ：Created in 2019/2/24 0024 下午 17:28
 * @description：自定义异常
 * @modified By：
 * @version: $
 */
@Data
@ApiModel(value = "全局异常")
public class GuliException extends RuntimeException{

    @ApiModelProperty(value = "状态码")
    private Integer code;

    /**
     * 接收状态码和消息
     * @param code
     * @param message
     */
    public GuliException(Integer code,String message){
        super(message);
        this.code = code;
    }

    /**
     * 接收枚举类型
     * @param resultCodeEnum
     */
    public GuliException(ResultCodeEnum resultCodeEnum){
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    @Override
    public String toString() {
        return "GuliException{" +
                "message=" + this.getMessage() +
                "code=" + code +
                '}';
    }
}
