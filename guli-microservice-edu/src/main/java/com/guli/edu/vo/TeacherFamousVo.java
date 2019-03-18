package com.guli.edu.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：mei
 * @date ：Created in 2019/3/14 0014 下午 20:12
 * @description：页面大咖名师相关字段
 * @modified By：
 * @version: $
 */
@ApiModel(description = "名师大咖相关字段")
@Data
public class TeacherFamousVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "讲师ID")
    private String id;

    @ApiModelProperty(value = "讲师姓名")
    private String name;

    @ApiModelProperty(value = "讲师资历,一句话说明讲师")
    private String intro;

    @ApiModelProperty(value = "讲师简介")
    private String career;

    @ApiModelProperty(value = "讲师头像")
    private String avatar;


}
