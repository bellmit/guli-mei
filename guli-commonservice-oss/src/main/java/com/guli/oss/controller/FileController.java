package com.guli.oss.controller;

import com.guli.common.vo.R;
import com.guli.oss.service.FileService;
import com.guli.oss.util.ConstantPropertiesUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ：mei
 * @date ：Created in 2019/2/27 0027 下午 20:02
 * @description：文件管理
 * @modified By：
 * @version: $
 */
@Api(description = "阿里云文件管理工具")
@CrossOrigin
@RestController
@RequestMapping("/admin/oss/file")
@Slf4j
public class FileController {
    @Autowired
    private FileService fileService;

    @ApiOperation(value = "文件上传")
    @PostMapping("upload")
    public R upload(
            @ApiParam(name = "file",value = "文件上传",required = true)
                    @RequestParam(value = "file")MultipartFile file,
            @ApiParam(name = "host",value = "自定义目录",required = false)
            @RequestParam(value = "host",required = false) String host){
        if(!StringUtils.isEmpty(host)){
            ConstantPropertiesUtil.FILE_HOST= host;
        }
        String uploadUrl = fileService.upload(file);
        return R.ok().message("文件上传成功!").data("url",uploadUrl);
    }

    @ApiOperation(value = "文件删除")
    @DeleteMapping("delete")
    public R delete(
            @ApiParam(name = "cover",value = "文件url",required = true)
            @RequestParam String cover){
        fileService.delete(cover);
        return R.ok().message("文件删除成功!");
    }
}
