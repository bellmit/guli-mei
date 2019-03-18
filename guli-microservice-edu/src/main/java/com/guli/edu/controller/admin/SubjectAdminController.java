package com.guli.edu.controller.admin;

import com.guli.common.exception.GuliException;
import com.guli.common.vo.R;
import com.guli.edu.entity.Subject;
import com.guli.edu.service.SubjectService;
import com.guli.edu.vo.SubjectVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author ：mei
 * @date ：Created in 2019/3/1 0001 下午 23:21
 * @description：课程分类管理
 * @modified By：
 * @version: $
 */
@CrossOrigin
@RestController
@Api(description = "课程分类管理")
@RequestMapping(value = "/admin/edu/subject")
public class SubjectAdminController {

    @Autowired
    private SubjectService subjectService;

    @ApiOperation(value = "Excel批量导入")
    @PostMapping(value = "import")
    public R addUser(
            @ApiParam(name = "file", value = "Excel文件", required = true)
            @RequestParam(value = "file") MultipartFile file) {
        List<String> msg = subjectService.batchImport(file);
        if (msg.size() == 0) {
            return R.ok().message("批量导入成功");
        } else {
            return R.error().message("部分数据导入失败").data("messageList", msg);
        }
    }


    @ApiOperation(value = "嵌套数据列表")
    @GetMapping
    public R getNestedTreeList() {
        List<SubjectVo> children = subjectService.getNestedTreeList();
        return R.ok().data("items", children);
    }

    @ApiOperation(value = "删除分类")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "课程id", required = true)
            @PathVariable String id) {
        boolean removeById = subjectService.removeById(id);
        if (removeById) {
            return R.ok();
        }
        throw new GuliException(21003, "删除失败");
    }


    @ApiOperation(value = "添加一级分类")
    @PostMapping(value = "save-level-one")
    public R saveLevelOne(
            @ApiParam(name = "subject", value = "一级分类名", required = true)
            @RequestBody Subject subject
    ) {
        boolean result = subjectService.saveSubjectLevelOne(subject);
        if (result) {
            return R.ok();
        }
        throw new GuliException(20001, "保存失败");
    }


    @ApiOperation(value = "添加二级菜单")
    @PostMapping(value = "save-level-two")
    public R saveLevelTwo(
            @ApiParam(name = "subejct", value = "二级菜单", required = true)
            @RequestBody Subject subject) {
        boolean result = subjectService.saveSubjectLevelTwo(subject);
        if (result) {
            return R.ok();
        }
        throw new GuliException(20003, "添加失败");
    }
}

