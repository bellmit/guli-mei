package com.guli.vod.service;

import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import com.aliyuncs.vod.model.v20170321.RefreshUploadVideoResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 视频管理
 * @author mei
 */
public interface VideoService {
    /**
     * 视频上传
     * @param file
     * @return
     */
    String uploadVideo(MultipartFile file);

    /**
     * 根据id删除视频
     * @param videoId
     */
    void removeVideo(String videoId);

    /**
     * 获取视频上传地址和凭证
     * @param title
     * @param fileName
     * @return
     */
    CreateUploadVideoResponse getUploadAuthAndAddress(String title, String fileName);

    /**
     * 刷新上传凭证
     * @param videoId
     * @return
     */
    RefreshUploadVideoResponse refreshUploadAuthAndAddress(String videoId);

    /**
     *根据视频id批量删除视频
     * @param videoIdList
     */
    void removeVideoList(List<String> videoIdList);
}
