package com.guli.edu.client;

import com.guli.common.vo.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author ：mei
 * @date ：Created in 2019/3/12 0012 上午 11:51
 * @description：vod微服务映射接口方法
 * @modified By：
 * @version: $
 */
@Component
@FeignClient("guli-vod")
public interface VodClient {

    /**
     * 根据视频id删除阿里云视频
     * @param videoId
     * @return
     */
    @DeleteMapping("/admin/vod/video/{videoId}")
    public R removeVideo(@PathVariable("videoId") String videoId);

    /**
     * 批量删除阿里云视频
     * @param videoIdList
     * @return
     */
    @DeleteMapping("/admin/vod/video/delete-batch")
    public R removeVideoList(@RequestParam("videoIdList") List videoIdList);
}
