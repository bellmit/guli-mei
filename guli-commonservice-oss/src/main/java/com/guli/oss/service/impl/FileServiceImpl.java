package com.guli.oss.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.guli.common.constants.ResultCodeEnum;
import com.guli.common.exception.GuliException;
import com.guli.oss.service.FileService;
import com.guli.oss.util.ConstantPropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author ：mei
 * @date ：Created in 2019/2/27 0027 下午 19:27
 * @description：文件管理类
 * @modified By：
 * @version: $
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Transactional
    @Override
    public String upload(MultipartFile file) {
        String uploadUrl = null;
        //获取阿里云存储相关常量
        String endpoint = ConstantPropertiesUtil.END_POINT;
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String fileHost = ConstantPropertiesUtil.FILE_HOST;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
        // 创建OSSClient实例。
        try {
            OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            if (!ossClient.doesBucketExist(bucketName)) {
                //创建bucket
                ossClient.createBucket(bucketName);
                //设置OSS实例的访问权限
                ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            }
            //获取文件上传流
            InputStream inputStream = file.getInputStream();
            //构建日期路径：avatar/2019/02/26/文件名
            String filePath = new DateTime().toString("yyyy/MM/dd");
            //文件名 uuid.扩展名
            String original = file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            String fileType = original.substring(original.lastIndexOf("."));
            String newName = fileName + fileType;
            String fileUrl = fileHost + "/" + filePath + "/" + newName;
            // 上传文件。
            ossClient.putObject(bucketName, fileUrl, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();
            //获取URL地址
            uploadUrl = "http://" + bucketName + "." + endpoint + "/" + fileUrl;
        } catch (IOException e) {
            e.printStackTrace();
            throw new GuliException(ResultCodeEnum.FILE_UPLOAD_ERROR);
        }
        return uploadUrl;
    }

    @Transactional
    @Override
    public void delete(String cover) {
        String endpoint = ConstantPropertiesUtil.END_POINT;
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
        //获取文件名称
        int index = cover.indexOf("com/");
        String objectName = cover.substring(index+4);
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        ossClient.deleteObject(bucketName,objectName);
        ossClient.shutdown();
    }
}
