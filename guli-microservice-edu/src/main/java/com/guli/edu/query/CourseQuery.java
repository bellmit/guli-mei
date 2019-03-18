package com.guli.edu.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：mei
 * @date ：Created in 2019/3/6 0006 下午 18:56
 * @description：课程查询项
 * @modified By：
 * @version: $
 */
@ApiModel(value = "Course查询对象", description = "课程查询对象封装")
@Data
public class CourseQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程名称")
    private String title;

    @ApiModelProperty(value = "讲师Id")
    private String teacherId;

    @ApiModelProperty(value = "一级类别ID")
    private String subjectParentId;

    @ApiModelProperty(value = "二级类别ID")
    private String subjectId;

}
