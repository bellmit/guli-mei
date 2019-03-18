package com.guli.edu.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.constants.ResultCodeEnum;
import com.guli.common.exception.GuliException;
import com.guli.common.vo.R;
import com.guli.edu.entity.Course;
import com.guli.edu.form.CourseInfoForm;
import com.guli.edu.query.CourseQuery;
import com.guli.edu.service.CourseService;
import com.guli.edu.vo.CoursePublishVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ：mei
 * @date ：Created in 2019/3/4 0004 下午 17:03
 * @description：课程发布
 * @modified By：
 * @version: $
 */
@CrossOrigin
@RequestMapping(value = "admin/edu/course")
@RestController
@Api(description = "课程管理")
public class CourseAdminController {

    @Autowired
    private CourseService courseService;

    @ApiOperation(value = "新增课程")
    @PostMapping("save-course-info")
    public R saveCourseInfo(
            @ApiParam(name = "CourseInfoForm", value = "课程基本信息", required = true)
            @RequestBody CourseInfoForm courseInfoForm) {
        String courseId = courseService.saveCourseInfo(courseInfoForm);
        if (!StringUtils.isEmpty(courseId)) {
            return R.ok().data("courseId", courseId);
        }
        return R.error().message("添加失败");
    }

    @ApiOperation(value = "根据ID查询课程")
    @GetMapping("course-info/{id}")
    public R getById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id) {
        CourseInfoForm courseInfoForm = courseService.getCourseInfoFormById(id);
        return R.ok().data("item", courseInfoForm);
    }

    @ApiOperation(value = "更新课程信息")
    @PutMapping("update-course-info/{id}")
    public R updateCourseInfoById(
            @ApiParam(name = "courseInfoForm", value = "课程信息", required = true)
            @RequestBody CourseInfoForm courseInfoForm,
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id) {
        courseService.updateCourseInfoById(courseInfoForm);
        return R.ok();
    }

    @ApiOperation("分页课程列表")
    @GetMapping("{page}/{limit}")
    public R pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录条数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
                    CourseQuery courseQuery) {
        if (page <= 0 || limit <= 0) {
            throw new GuliException(ResultCodeEnum.PARAM_ERROR);
        }
        Page<Course> pageParam = new Page<>(page, limit);
        courseService.pageQuery(pageParam, courseQuery);
        long total = pageParam.getTotal();
        List<Course> records = pageParam.getRecords();
        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "根据ID删除课程")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id) {
        boolean result = courseService.deleteCourseById(id);
        if (result) {
            return R.ok();
        }
        return R.error().message("课程删除失败");
    }

    @ApiOperation(value = "根据课程id获取课程基本预览信息")
    @GetMapping("course-publish-info/{id}")
    public R getCoursePublishInfoById(
            @ApiParam(name = "id", value = "课程id", required = true)
            @PathVariable String id
    ) {
        CoursePublishVo coursePublishVo = courseService.getCoursePublishVoById(id);
        return R.ok().data("item", coursePublishVo);
    }

    @ApiOperation("发布课程")
    @PutMapping("publish-course/{id}")
    public R publishCourse(
            @ApiParam(name = "id",value = "课程id",required = true)
                    @PathVariable String id){
        boolean result = courseService.publishCourseById(id);
        if (!result) {
            throw new GuliException(21004,"发布课程失败!");
        }
        return R.ok();
    }
}
