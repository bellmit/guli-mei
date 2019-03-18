package com.guli.edu.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.constants.ResultCodeEnum;
import com.guli.common.exception.GuliException;
import com.guli.common.vo.R;
import com.guli.edu.entity.Teacher;
import com.guli.edu.query.TeacherQuery;
import com.guli.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ：mei
 * @date ：Created in 2019/2/23 0023 上午 11:42
 * @description：
 * @modified By：
 * @version: $
 */
@CrossOrigin
@Api(description = "讲师管理")
@RestController
@RequestMapping("/admin/edu/teacher")
@Slf4j
public class TeacherAdminController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation("所有讲师列表")
    @GetMapping
    public R list(){
        List<Teacher> list = teacherService.list(null);
        return R.ok().data("items",list);
    }

    @ApiOperation("根据id删除讲师")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name="id",value = "讲师id",required = true)
            @PathVariable String id){
        boolean b = teacherService.removeById(id);
        if (b){
            return R.ok();
        }
            return R.error().message("删除失败!");
    }

    @ApiOperation("分页讲师列表")
    @GetMapping("{page}/{limit}")
    public R pageList(
            @ApiParam(name="page",value = "当前页码",required = true)
            @PathVariable Long page,
            @ApiParam(name="limit",value = "每页记录条数",required = true)
            @PathVariable Long limit,
            @ApiParam(name="teacherQuery",value = "查询对象",required = false)
            TeacherQuery teacherQuery){
        if(page <= 0 || limit <= 0){
            throw new GuliException(ResultCodeEnum.PARAM_ERROR);
        }
        Page<Teacher> pageParam = new Page<>(page, limit);
        teacherService.pageQuery(pageParam,teacherQuery);
        long total = pageParam.getTotal();
        List<Teacher> records = pageParam.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    @ApiOperation(value = "新增讲师")
    @PostMapping
    public R save(@ApiParam(name = "teacher",value = "讲师对象",required = true)
                      @RequestBody ()Teacher teacher){
        boolean b = teacherService.save(teacher);
        if(b){
            return R.ok();
        }
        return R.error().message("添加失败!");
    }

    @ApiOperation("根据讲师ID查询")
    @GetMapping("{id}")
    public R getById(@ApiParam(name = "id",value = "讲师id",required = true)
                     @PathVariable String id){
        Teacher teacher = teacherService.getById(id);
        if (StringUtils.isEmpty(teacher)) {
            return R.ok().message("数据不存在!");
        }
        return R.ok().data("item",teacher);
    }

    @ApiOperation("根据讲师ID修改信息")
    @PutMapping("{id}")
    public R updateById(@ApiParam(name = "id",value = "讲师ID",required = true)
                            @PathVariable String id,
                        @ApiParam(name = "teacher",value = "讲师对象",required = true)
                            @RequestBody Teacher teacher){
        teacher.setId(id);
        boolean b = teacherService.updateById(teacher);
        if (b) {
            return R.ok();
        }
        return R.error().message("修改失败!");
    }
}