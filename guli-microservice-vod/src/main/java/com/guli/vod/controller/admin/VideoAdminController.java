package com.guli.vod.controller.admin;

import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import com.aliyuncs.vod.model.v20170321.RefreshUploadVideoResponse;
import com.guli.common.vo.R;
import com.guli.vod.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author ：mei
 * @date ：Created in 2019/3/11 0011 下午 17:00
 * @description：阿里云视频点播
 * @modified By：
 * @version: $
 */
@CrossOrigin
@Api(description = "阿里云视频点播服务")
@RestController
@RequestMapping("/admin/vod/video")
public class VideoAdminController {

    @Autowired
    private VideoService videoService;

    @ApiOperation("上传视频")
    @PostMapping("upload")
    public R uploadVideo(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file) {

        String videoId = videoService.uploadVideo(file);
        return R.ok().message("视频上传成功").data("videoId", videoId);
    }

    @ApiOperation("根据id删除视频")
    @DeleteMapping("{videoId}")
    public R removeVideo(
            @ApiParam(name = "videoId", value = "视频id", required = true)
            @PathVariable String videoId) {
        videoService.removeVideo(videoId);
        return R.ok().message("视频删除成功!");
    }

    @ApiOperation("批量删除视频")
    @DeleteMapping("delete-batch")
    public R removeVideoList(
            @ApiParam(name = "videoIdList", value = "视频id", required = true)
            @RequestParam("videoIdList") List videoIdList) {
        videoService.removeVideoList(videoIdList);
        return R.ok().message("视频删除成功!");
    }

    @ApiOperation("获取视频上传地址和凭证")
    @GetMapping("get-upload-auth-and-address/{title}/{fileName}")
    public R getUploadAuthAndAddress(
            @ApiParam(name = "title", value = "视频标题", required = true)
            @PathVariable String title,
            @ApiParam(name = "fileName", value = "视频源文件名", required = true)
            @PathVariable String fileName) {
        CreateUploadVideoResponse response = videoService.getUploadAuthAndAddress(title, fileName);
        return R.ok().message("获取视频上传地址和凭证成功").data("response", response);
    }

    @ApiOperation("刷新视频上传凭证")
    @GetMapping("refresh-upload-auth/{videoId}")
    public R refreshUploadAuthAndAddress(
            @ApiParam(name = "videoId",value = "视频id",required = true)
                    @PathVariable String videoId){

        RefreshUploadVideoResponse response = videoService.refreshUploadAuthAndAddress(videoId);
        return R.ok().message("刷新上传凭证成功").data("response",response);
    }

}
