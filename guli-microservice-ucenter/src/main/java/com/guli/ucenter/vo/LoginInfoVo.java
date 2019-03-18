package com.guli.ucenter.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ：mei
 * @date ：Created in 2019/3/16 0016 下午 17:02
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class LoginInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String nickname;
    private String avatar;

}
