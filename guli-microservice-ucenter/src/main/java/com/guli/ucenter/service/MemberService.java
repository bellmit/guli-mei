package com.guli.ucenter.service;

import com.guli.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author Mei
 * @since 2019-03-12
 */
public interface MemberService extends IService<Member> {

    /**
     * 获取当天创建的用户的数量
     * @param day
     * @return
     */
    Integer countRegisterByDay(String day);

    /**
     * 根据openid获取用户信息
     * @param openid
     * @return
     */
    Member getByOpenid(String openid);
}
