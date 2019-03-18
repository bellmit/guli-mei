package com.guli.edu.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：mei
 * @date ：Created in 2019/3/2 0002 下午 13:07
 * @description：响应课程类
 * @modified By：
 * @version: $
 */
@Data
public class SubjectVo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    private List<SubSubjectVo> children = new ArrayList<>();
}
