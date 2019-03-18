package com.guli.edu.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author ：mei
 * @date ：Created in 2019/3/6 0006 下午 23:37
 * @description：课程发布封装
 * @modified By：
 * @version: $
 */
@Data
public class CoursePublishVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private BigDecimal price;

}
