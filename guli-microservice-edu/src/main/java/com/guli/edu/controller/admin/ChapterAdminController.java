package com.guli.edu.controller.admin;

import com.guli.common.exception.GuliException;
import com.guli.common.vo.R;
import com.guli.edu.entity.Chapter;
import com.guli.edu.service.ChapterService;
import com.guli.edu.vo.ChapterVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ：mei
 * @date ：Created in 2019/3/5 0005 下午 16:36
 * @description：章节和课时功能
 * @modified By：
 * @version: $
 */
@Api(description = "章节课时功能")
@CrossOrigin
@RestController
@RequestMapping("/admin/edu/chapter")
public class ChapterAdminController {

    @Autowired
    private ChapterService chapterService;

    @ApiOperation("根据课程id获取课程列表")
    @GetMapping("nested-list/{courseId}")
    public R nestedChapterList(
            @ApiParam(name = "courseId", value = "课程id", required = true)
            @PathVariable String courseId) {
        List<ChapterVo> chapterVoList = chapterService.nestedList(courseId);
        return R.ok().data("items", chapterVoList);
    }

    @ApiOperation("增加章节")
    @PostMapping
    public R save(
            @ApiParam(name = "chapter", value = "章节信息", required = true)
            @RequestBody Chapter chapter) {
        boolean result = chapterService.save(chapter);
        if (result) {
            return R.ok();
        }
        throw new GuliException(20003, "添加失败!");
    }

    @ApiOperation("根据ID获取章节信息")
    @GetMapping("{id}")
    public R getById(@ApiParam(name = "id", value = "章节id", required = true)
                     @PathVariable String id) {
        Chapter chapter = chapterService.getById(id);
        return R.ok().data("item", chapter);
    }

    @ApiOperation("根据ID更新章节信息")
    @PutMapping("{id}")
    public R updateById(
            @ApiParam(name = "id", value = "章节id", required = false)
            @PathVariable String id,
            @ApiParam(name = "chapter", value = "章节对象", required = true)
            @RequestBody Chapter chapter) {
        boolean result = chapterService.updateById(chapter);
        if (result) {
            return R.ok();
        }
        throw new GuliException(20004,"更新失败");
    }

    @ApiOperation("根据ID更新章节信息")
    @DeleteMapping("{id}")
    public R deleteById(@ApiParam(name = "id", value = "章节id", required = false)
                        @PathVariable String id) {
        boolean result = chapterService.removeChapterById(id);
        if (result) {
            return R.ok();
        }
        throw new GuliException(20004, "删除失败");
    }

}
