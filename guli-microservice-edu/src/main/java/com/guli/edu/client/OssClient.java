package com.guli.edu.client;

import com.guli.common.vo.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author mei
 * 阿里云OSS文件管理映射接口方法
 */
@Component
@FeignClient("guli-oss")
public interface OssClient {
    /**
     * 删除封面
     * @param cover
     * @return
     */
    @DeleteMapping("/admin/oss/file/delete")
    public R delete(@RequestParam("cover") String cover);
}
