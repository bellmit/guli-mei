package com.guli.edu.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author ：mei
 * @date ：Created in 2019/3/14 0014 下午 15:41
 * @description：网站热门课程封装字段
 * @modified By：
 * @version: $
 */
@ApiModel(value="课程信息", description="网站热门课程需要的相关字段")
@Data
public class HotCourseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
    private BigDecimal price;

    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;

    @ApiModelProperty(value = "销售数量")
    private Long buyCount;

    @ApiModelProperty(value = "浏览数量")
    private Long viewCount;
}
