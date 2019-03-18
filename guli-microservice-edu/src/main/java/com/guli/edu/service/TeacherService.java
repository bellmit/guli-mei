package com.guli.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.edu.entity.Teacher;
import com.guli.edu.query.TeacherQuery;
import com.guli.edu.vo.TeacherFamousVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author Helen
 * @since 2019-02-23
 */
public interface TeacherService extends IService<Teacher> {

    /**
     * 条件查询讲师信息
     * @param pageParam
     * @param teacherQuery
     */
    void pageQuery(Page<Teacher> pageParam, TeacherQuery teacherQuery);

    /**
     * 获取分页讲师列表
     * @param pageParam
     * @return
     */
    Map<String,Object> pageListWeb(Page<Teacher> pageParam);

    /**
     * 获取名师大咖讲师信息
     * @return
     */
    List<TeacherFamousVo> selectFamousTeacher();
}
