package com.guli.ucenter.controller.api;

import com.google.gson.Gson;
import com.guli.common.exception.GuliException;
import com.guli.ucenter.entity.Member;
import com.guli.ucenter.service.MemberService;
import com.guli.ucenter.util.ConstantPropertiesUtil;
import com.guli.ucenter.util.HttpClientUtils;
import com.guli.ucenter.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：mei
 * @date ：Created in 2019/3/15 0015 下午 20:10
 * @description：微信扫描登录服务
 * @modified By：
 * @version: $
 */
@CrossOrigin
@Controller
@RequestMapping("/api/ucenter/wx")
public class WxApiController {

    @Autowired
    private MemberService memberService;

    @GetMapping("login")
    public String getQrConnect() {
        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        // 回调地址
        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL; //获取业务服务器重定向地址
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8"); //url编码
        } catch (UnsupportedEncodingException e) {
            throw new GuliException(20001, e.getMessage());
        }
        // 防止csrf攻击（跨站请求伪造攻击）
        //String state = UUID.randomUUID().toString().replaceAll("-", "");
        //System.out.println(state);
        String state = "meida";

        // 采用redis等进行缓存state 使用sessionId为key 30分钟后过期，可配置
        // TODO

        //生成qrcodeUrl
        String qrcodeUrl = String.format(
                baseUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                redirectUrl,
                state);

        return "redirect:" + qrcodeUrl;
    }


    @RequestMapping("callback")
    public String callback(String code, String state) {
        //得到授权临时票据code
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";
        String accessTokenUrl = String.format(baseAccessTokenUrl, ConstantPropertiesUtil.WX_OPEN_APP_ID, ConstantPropertiesUtil.WX_OPEN_APP_SECRET, code);
        String result = null;
        try {
            result = HttpClientUtils.get(accessTokenUrl);
        } catch (Exception e) {
            throw new GuliException(20003, "获取access_token失败");
        }
        Gson gson = new Gson();
        Map<String, Object> map = gson.fromJson(result, HashMap.class);
        if (map.get("errcode") != null) {
            throw new GuliException(20003, "获取access_token失败");
        }
        String accessToken = (String) map.get("access_token");
        String openid = (String) map.get("openid");
        String unionid = (String) map.get("unionid");
        if (StringUtils.isEmpty(accessToken) || StringUtils.isEmpty(openid) || StringUtils.isEmpty(unionid)) {
            throw new GuliException(20003, "获取access_token失败");
        }
        Member member = memberService.getByOpenid(openid);
        if (member == null) {
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
            String resultUserInfo = null;
            try {
                resultUserInfo = HttpClientUtils.get(userInfoUrl);
            } catch (Exception e) {
                throw new GuliException(20003, "获取用户信息失败");
            }
            Map<String, Object> resultUserInfoMap = gson.fromJson(resultUserInfo, HashMap.class);
            if (resultUserInfoMap.get("errcode") != null) {
                throw new GuliException(20003, "获取用户信息失败");
            }
            String nickname = (String) resultUserInfoMap.get("nickname");
            String headimgurl = (String) resultUserInfoMap.get("headimgurl");
            member = new Member();
            member.setOpenid(openid);
            member.setNickname(nickname);
            member.setAvatar(headimgurl);
            memberService.save(member);
        }
        String token = JwtUtils.geneJsonWebToken(member);
        return "redirect:http://localhost:3000?token=" + token;
    }
}
