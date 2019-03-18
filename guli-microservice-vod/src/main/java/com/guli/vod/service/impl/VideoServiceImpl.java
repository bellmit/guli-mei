package com.guli.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.*;
import com.guli.common.exception.GuliException;
import com.guli.vod.service.VideoService;
import com.guli.vod.util.ALiYunVodSDKUtils;
import com.guli.vod.util.ConstantPropertiesUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author ：mei
 * @date ：Created in 2019/3/11 0011 上午 8:57
 * @description：视频操作实现类
 * @modified By：
 * @version: $
 */
@Service
public class VideoServiceImpl implements VideoService {

    @Transactional
    @Override
    public String uploadVideo(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));

            UploadStreamRequest request = new UploadStreamRequest(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET, title, originalFilename, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            String videoId = response.getVideoId();
            if (!response.isSuccess()) {
                String errorMessage = "阿里云上传错误:" + "code" + response.getCode() + ",message:" + response.getMessage();
                if (StringUtils.isEmpty(videoId)) {
                    throw new GuliException(20001, errorMessage);
                }
            }
            return videoId;
        } catch (IOException e) {
            throw new GuliException(20001, "guli vod 服务上传失败");
        }
    }

    @Transactional
    @Override
    public void removeVideo(String videoId) {
        try {
            DefaultAcsClient client = ALiYunVodSDKUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);

            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(videoId);

            DeleteVideoResponse response = client.getAcsResponse(request);
        } catch (ClientException e) {
            throw new GuliException(20001, "视频删除失败");
        }
    }

    @Override
    public CreateUploadVideoResponse getUploadAuthAndAddress(String title, String fileName) {
        try {
            DefaultAcsClient client = ALiYunVodSDKUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);

            CreateUploadVideoRequest request = new CreateUploadVideoRequest();
            request.setFileName(fileName);
            request.setTitle(title);

            CreateUploadVideoResponse response = client.getAcsResponse(request);
            return response;
        } catch (ClientException e) {
            throw new GuliException(20001, "获取视频上传地址和凭证失败");
        }
    }

    @Override
    public RefreshUploadVideoResponse refreshUploadAuthAndAddress(String videoId) {
        try {
            DefaultAcsClient client = ALiYunVodSDKUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);

            RefreshUploadVideoRequest request = new RefreshUploadVideoRequest();
            request.setVideoId(videoId);

            RefreshUploadVideoResponse response = client.getAcsResponse(request);
            return response;
        } catch (ClientException e) {
            throw new GuliException(20001, "刷新上传凭证失败!");
        }
    }

    @Transactional
    @Override
    public void removeVideoList(List<String> videoIdList) {
        try {
            DefaultAcsClient client = ALiYunVodSDKUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();

                //TODO videoList.size不能大于20

                String videoIdStr = org.apache.commons.lang.StringUtils.join(videoIdList.toArray(), ",");
                request.setVideoIds(videoIdStr);
            DeleteVideoResponse response = client.getAcsResponse(request);
        } catch (ClientException e) {
            throw new GuliException(20001, "视频删除失败");
        }
    }
}
