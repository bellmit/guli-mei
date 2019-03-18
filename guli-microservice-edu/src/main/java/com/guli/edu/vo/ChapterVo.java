package com.guli.edu.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：mei
 * @date ：Created in 2019/3/5 0005 下午 16:29
 * @description：章节
 * @modified By：
 * @version: $
 */
@Data
public class ChapterVo implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "课程ID")
    private String id;
    @ApiModelProperty(value = "课程标题")
    private String title;
    private List<VideoVo> children = new ArrayList<>();
}
