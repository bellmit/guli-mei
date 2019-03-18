package com.guli.statistics.client;

import com.guli.common.vo.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 统计数据映射接口方法
 * @author mei
 */
@Component
@FeignClient("guli-ucenter")
public interface UcenterClient {
    /**
     * 获取当天注册用户数量
     * @param day
     * @return
     */
    @GetMapping("/admin/ucenter/member/count-register/{day}")
    public R registerCount(@PathVariable("day") String day);
}
