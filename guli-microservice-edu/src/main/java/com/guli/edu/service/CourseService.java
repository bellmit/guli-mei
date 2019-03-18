package com.guli.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.edu.entity.Course;
import com.guli.edu.form.CourseInfoForm;
import com.guli.edu.query.CourseQuery;
import com.guli.edu.vo.CoursePublishVo;
import com.guli.edu.vo.CourseWebVo;
import com.guli.edu.vo.HotCourseVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Helen
 * @since 2019-02-23
 */
public interface CourseService extends IService<Course> {

    /**
     * 根据类别id查询课程
     * @param subjectId
     * @return
     */
    boolean getCourseBySubjectId(String subjectId);

    /**
     * 保存课程和课程详情信息
     * @param courseInfoForm
     * @return
     */
    String saveCourseInfo(CourseInfoForm courseInfoForm);

    /**
     * 根据id获取课程信息和课程详情
     * @param id
     * @return
     */
    CourseInfoForm getCourseInfoFormById(String id);

    /**
     * 根据Id更新课程信息
     * @param courseInfoForm
     */
    void updateCourseInfoById(CourseInfoForm courseInfoForm);


    /**
     * 获取课程分页数据
     * @param pageParam
     * @param courseQuery
     */
    void pageQuery(Page<Course> pageParam, CourseQuery courseQuery);

    /**
     * 根据课程ID删除课程
     * @param id
     * @return
     */
    boolean deleteCourseById(String id);

    /**
     * 根据课程ID获取课程预览信息
     * @param courseId
     * @return
     */
    CoursePublishVo getCoursePublishVoById(String courseId);

    /**
     * 根据课程id发布课程
     * @param id
     * @return
     */
    boolean publishCourseById(String id);

    /**
     * 根据讲师id查询讲师所讲的课程列表
     * @param teacherId
     * @return
     */
    List<Course> selectByTeacherId(String teacherId);

    /**
     * 获取分页课程列表
     * @param pageParam
     * @return
     */
    Map<String,Object> pageListWeb(Page<Course> pageParam);

    /**
     * 获取课程信息
     * @param id
     * @return
     */
    CourseWebVo selectInfoWebById(String id);

    /**
     * 更新课程浏览数
     * @param id
     */
    void updatePageViewCount(String id);

    /**
     * 获取热门课程
     * @return
     */
    List<HotCourseVo> selectHotCourse();
}
