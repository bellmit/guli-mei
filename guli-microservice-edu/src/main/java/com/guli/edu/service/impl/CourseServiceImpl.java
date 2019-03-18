package com.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.common.constants.PriceConstants;
import com.guli.common.exception.GuliException;
import com.guli.edu.client.OssClient;
import com.guli.edu.constants.CourseStatusConstants;
import com.guli.edu.entity.Course;
import com.guli.edu.entity.CourseDescription;
import com.guli.edu.form.CourseInfoForm;
import com.guli.edu.mapper.CourseMapper;
import com.guli.edu.query.CourseQuery;
import com.guli.edu.service.*;
import com.guli.edu.vo.CoursePublishVo;
import com.guli.edu.vo.CourseWebVo;
import com.guli.edu.vo.HotCourseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Helen
 * @since 2019-02-23
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseDescriptionService courseDescriptionService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private OssClient ossClient;

    @Override
    public boolean getCourseBySubjectId(String subjectId) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("subject_id", subjectId);
        Integer count = baseMapper.selectCount(queryWrapper);
        return null != count && count > 0;
    }

    @Transactional
    @Override
    public String saveCourseInfo(CourseInfoForm courseInfoForm) {
        //保存课程基本信息
        Course course = new Course();
        course.setStatus(CourseStatusConstants.COURSE_DRAFT);
        BeanUtils.copyProperties(courseInfoForm, course);
        boolean resultCourseInfo = this.save(course);
        if (!resultCourseInfo) {
            throw new GuliException(21005, "课程信息保存失败!");
        }
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(course.getId());
        courseDescription.setDescription(courseInfoForm.getDescription());
        boolean resultDescription = courseDescriptionService.save(courseDescription);
        if (!resultDescription) {
            throw new GuliException(21005, "课程详细信息保存失败!");
        }
        return course.getId();
    }

    @Override
    public CourseInfoForm getCourseInfoFormById(String id) {
        Course course = this.getById(id);
        if (course == null) {
            throw new GuliException(20005, "数据不存在");
        }
        CourseInfoForm courseInfoForm = new CourseInfoForm();
        BeanUtils.copyProperties(course, courseInfoForm);

        CourseDescription courseDescription = courseDescriptionService.getById(id);
        if (courseDescription != null) {
            courseInfoForm.setDescription(courseDescription.getDescription());
        }
        courseInfoForm.setPrice(courseInfoForm.getPrice().setScale(PriceConstants.DISPLAY_SCALE, BigDecimal.ROUND_FLOOR));
        return courseInfoForm;
    }

    @Transactional
    @Override
    public void updateCourseInfoById(CourseInfoForm courseInfoForm) {
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoForm, course);
        boolean resultCourseInfo = this.updateById(course);
        if (!resultCourseInfo) {
            throw new GuliException(20004, "课程信息保存失败!");
        }
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseInfoForm.getDescription());
        courseDescription.setId(course.getId());
        boolean resultCourseDescription = courseDescriptionService.updateById(courseDescription);
        if (!resultCourseDescription) {
            courseDescriptionService.save(courseDescription);
        }
    }

    @Override
    public void pageQuery(Page<Course> pageParam, CourseQuery courseQuery) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        //queryWrapper.eq("status","Normal");
        queryWrapper.orderByDesc("gmt_create");
        if (courseQuery == null) {
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }

        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();

        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(teacherId)) {
            queryWrapper.eq("teacher_id", teacherId);
        }
        if (!StringUtils.isEmpty(subjectParentId)) {
            queryWrapper.eq("subject_parent_id", subjectParentId);
        }
        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapper.eq("subject_id", subjectId);
        }
        baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Transactional
    @Override
    public boolean deleteCourseById(String id) {
        boolean deleteVideo = videoService.deleteByCourseId(id);
        boolean deleteCourseDescription = courseDescriptionService.removeById(id);
        boolean deleteChapter = chapterService.deleteByCourseId(id);
        //删除封面
        Course course = baseMapper.selectById(id);
        String cover = course.getCover();
        ossClient.delete(cover);

        boolean deleteCourse = this.removeById(id);
        if (deleteVideo && deleteCourseDescription && deleteChapter && deleteCourse) {
            return true;
        }
        throw new GuliException(20009, "课程删除失败!");
    }

    @Transactional
    @Override
    public boolean publishCourseById(String id) {
        Course course = new Course();
        course.setId(id);
        course.setStatus(CourseStatusConstants.COURSE_NORMAL);
        Integer count = baseMapper.updateById(course);
        return null != count && count > 0;
    }

    @Override
    public List<Course> selectByTeacherId(String teacherId) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id", teacherId);
        queryWrapper.orderByDesc("gmt_modified");
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public Map<String, Object> pageListWeb(Page<Course> pageParam) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status","Normal");
        baseMapper.selectPage(pageParam,queryWrapper);
        List<Course> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }

    @Override
    public CourseWebVo selectInfoWebById(String id) {
        this.updatePageViewCount(id);
        return baseMapper.selectInfoWebById(id);
    }

    @Transactional
    @Override
    public void updatePageViewCount(String id) {
        Course course = baseMapper.selectById(id);
        course.setViewCount(course.getViewCount()+1);
        baseMapper.updateById(course);
    }

    @Override
    public List<HotCourseVo> selectHotCourse() {
        Page<Course> pageParam = new Page<>(1,8);
        List<HotCourseVo> hotCourseVos = new ArrayList<>();
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("buy_count");
        baseMapper.selectPage(pageParam, queryWrapper);
        List<Course> courses = pageParam.getRecords();
        for (int i = 0; i < courses.size(); i++) {
            HotCourseVo hotCourseVo = new HotCourseVo();
            BeanUtils.copyProperties(courses.get(i),hotCourseVo);
            hotCourseVos.add(hotCourseVo);
        }
        return hotCourseVos;
    }

    @Override
    public CoursePublishVo getCoursePublishVoById(String courseId) {
        CoursePublishVo coursePublishVo = baseMapper.selectCoursePublishVoById(courseId);
        return coursePublishVo;
    }
}
