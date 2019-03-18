package com.guli.edu.service;

import com.guli.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.edu.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Helen
 * @since 2019-02-23
 */
public interface ChapterService extends IService<Chapter> {

    /**
     * 根据课程id获取课程列表
     * @param courseId
     * @return
     */
    List<ChapterVo> nestedList(String courseId);

    /**
     * 根据id 删除章节
     * @param id
     * @return
     */
    boolean removeChapterById(String id);

    /**
     * 根据CourseId删除课程
     * @param id
     * @return
     */
    boolean deleteByCourseId(String id);
}
