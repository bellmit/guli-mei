package com.guli.edu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guli.edu.entity.Course;
import com.guli.edu.vo.CoursePublishVo;
import com.guli.edu.vo.CourseWebVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author Helen
 * @since 2019-02-23
 */
public interface CourseMapper extends BaseMapper<Course> {

    /**
     * 根据id获取课程发布信息
     * @param id
     * @return
     */
    CoursePublishVo selectCoursePublishVoById(String id);

    /**
     * 查询课程和讲师信息
     * @param courseId
     * @return
     */
    CourseWebVo selectInfoWebById(String courseId);
}
