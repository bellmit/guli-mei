package com.guli.ucenter.controller;


import com.guli.common.vo.R;
import com.guli.ucenter.util.JwtUtils;
import com.guli.ucenter.vo.LoginInfoVo;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author Mei
 * @since 2019-03-12
 */
@CrossOrigin
@RestController
@RequestMapping("/ucenter/member")
public class MemberController {

    public R getInfoByToken(@PathVariable String token){
        Claims claims = JwtUtils.checkJWT(token);
        String nickname = (String) claims.get("nickname");
        String avatar = (String)claims.get("avatar");
        String id = (String)claims.get("id");
        LoginInfoVo loginInfoVo = new LoginInfoVo();
        loginInfoVo.setId(id);
        loginInfoVo.setAvatar(avatar);
        loginInfoVo.setNickname(nickname);
    }

}

