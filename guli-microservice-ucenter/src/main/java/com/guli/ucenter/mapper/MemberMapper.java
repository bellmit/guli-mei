package com.guli.ucenter.mapper;

import com.guli.ucenter.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author Mei
 * @since 2019-03-12
 */
public interface MemberMapper extends BaseMapper<Member> {

    /**
     * 获取当天创建用户数
     * @param day
     * @return
     */
    Integer selectRegisterCount(String day);
}
