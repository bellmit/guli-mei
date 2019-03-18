package com.guli.edu.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：mei
 * @date ：Created in 2019/3/5 0005 下午 16:31
 * @description：课时视频
 * @modified By：
 * @version: $
 */
@Data
public class VideoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @ApiModelProperty(value = "节点名称")
    private String title;

    @TableField(value = "is_free")
    private Boolean free;

    @ApiModelProperty(value = "云服务器上存储的视频文件名称")
    private String videoOriginalName;

    private String videoSourceId;
}
