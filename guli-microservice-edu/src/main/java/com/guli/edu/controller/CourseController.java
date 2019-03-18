package com.guli.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.vo.R;
import com.guli.edu.entity.Course;
import com.guli.edu.service.ChapterService;
import com.guli.edu.service.CourseService;
import com.guli.edu.service.TeacherService;
import com.guli.edu.vo.ChapterVo;
import com.guli.edu.vo.CourseWebVo;
import com.guli.edu.vo.HotCourseVo;
import com.guli.edu.vo.TeacherFamousVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Helen
 * @since 2019-02-23
 */
@CrossOrigin
@Api(description = "课程管理")
@RestController
@RequestMapping("/edu/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private TeacherService teacherService;

    @ApiOperation(value = "分页课程列表")
    @GetMapping(value = "{page}/{limit}")
    public R pageList(
            @ApiParam(name = "page", value = "页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit) {
        Page<Course> pageParam = new Page<Course>(page, limit);
        Map<String, Object> map = courseService.pageListWeb(pageParam);
        return R.ok().data(map);
    }

    @ApiOperation(value = "根据ID查询课程")
    @GetMapping(value = "{courseId}")
    public R getById(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable String courseId) {

        //查询课程信息和讲师信息
        CourseWebVo courseWebVo = courseService.selectInfoWebById(courseId);

        //查询当前课程的章节信息
        List<ChapterVo> chapterVoList = chapterService.nestedList(courseId);

        return R.ok().data("course", courseWebVo).data("chapterVoList", chapterVoList);
    }

    @ApiOperation(value = "热门课程列表,名师列表")
    @GetMapping(value = "hot-course-and-teacher")
    public R getHotCourse() {
        List<HotCourseVo> courseList = courseService.selectHotCourse();
        List<TeacherFamousVo> teacherList = teacherService.selectFamousTeacher();
        HashMap<String, Object> map = new HashMap<>();
        map.put("courseList", courseList);
        map.put("teacherList", teacherList);
        return R.ok().data(map);
    }

}

