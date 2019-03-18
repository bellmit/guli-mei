package com.guli.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.guli.common.exception.GuliException;
import com.guli.common.vo.R;
import com.guli.vod.util.ALiYunVodSDKUtils;
import com.guli.vod.util.ConstantPropertiesUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

/**
 * @author ：mei
 * @date ：Created in 2019/3/15 0015 上午 10:15
 * @description：视频点播
 * @modified By：
 * @version: $
 */
@CrossOrigin
@Api(description = "视频点播管理")
@RestController
@RequestMapping("/vod/video")
public class VideoController {

    @GetMapping("get-play-auth/{videoId}")
    public R getVideoPlayAuth(
            @ApiParam(name = "videoId", value = "视频id", required = true)
            @PathVariable String videoId) {
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;

        try {
            DefaultAcsClient client = ALiYunVodSDKUtils.initVodClient(accessKeyId, accessKeySecret);
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(videoId);
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth", playAuth);
        } catch (ClientException e) {
            throw new GuliException(20003, "获取播放凭证失败");
        }
    }
}
