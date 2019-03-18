package com.guli.sysuser.controller;

import com.guli.common.vo.R;
import com.guli.sysuser.entity.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

/**
 * @author ：mei
 * @date ：Created in 2019/3/2 0002 上午 11:26
 * @description：系统用户
 * @modified By：
 * @version: $
 */
@Api(description = "系统用户中心")
@RestController
@CrossOrigin
@RequestMapping(value = "admin/sysuser")
public class SysUserController {

    @ApiOperation(value = "用户登录")
    @PostMapping(value = "login")
    public R login(
            @ApiParam(name = "sysUser",value = "用户信息",required = true)
            @RequestBody SysUser sysUser
    ){

        return R.ok().data("token","admin");
    }

    @ApiOperation(value = "用户信息")
    @GetMapping(value = "info")
    public R info(@RequestParam String token){

        return R.ok().data("name","admin").data("roles",new Object[]{"admin"})
                .data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

    @ApiOperation(value = "用户登出")
    @PostMapping(value = "logout")
    public R logout(){
        return R.ok();
    }
}
