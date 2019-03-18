package com.guli.ucenter.controller.admin;

import com.guli.common.vo.R;
import com.guli.ucenter.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ：mei
 * @date ：Created in 2019/3/12 0012 下午 20:52
 * @description：用户中心服务
 * @modified By：
 * @version: $
 */
@Api(description = "用户中心")
@RestController
@CrossOrigin
@RequestMapping("/admin/ucenter/member")
public class MemberAdminController {

    @Autowired
    private MemberService memberService;

    @ApiOperation("当天创建用户的数量")
    @GetMapping("count-register/{day}")
    public R registerCount(
            @ApiParam(name = "day",value = "日期",required = true)
                    @PathVariable String day) {
        Integer count = memberService.countRegisterByDay(day);
        return R.ok().data("countRegister", count);
    }
}
