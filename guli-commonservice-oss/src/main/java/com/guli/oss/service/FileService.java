package com.guli.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    /**
     * 文件上传至阿里云
     * @param file
     * @return
     */
    String upload(MultipartFile file);

    /**
     * 删除阿里云上的文件
     * @param cover
     */
    void delete(String cover);
}
