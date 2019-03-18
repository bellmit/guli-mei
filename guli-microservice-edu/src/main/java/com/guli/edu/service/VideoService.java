package com.guli.edu.service;

import com.guli.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.edu.form.VideoInfoForm;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author Helen
 * @since 2019-02-23
 */
public interface VideoService extends IService<Video> {
    /**
     * 查询课时是否存在
     * @param chapterId
     * @return
     */
    boolean getCountByChapterId(String chapterId);

    /**
     * 根据id更新课时信息
     * @param videoInfoForm
     */
    void updateVideoInfoById(VideoInfoForm videoInfoForm);

    /**
     * 根据id获取课时信息
     * @param id
     * @return
     */
    VideoInfoForm getVideoInfoFormById(String id);

    /**
     * 添加课时信息
     * @param videoInfoForm
     */
    void saveVideoInfo(VideoInfoForm videoInfoForm);

    /**
     *  根据id删除课时信息
     * @param id
     * @return
     */
    boolean removeVideoById(String id);

    /**
     * 根据CourseId删除课时
     * @param id
     * @return
     */
    boolean deleteByCourseId(String id);
}
