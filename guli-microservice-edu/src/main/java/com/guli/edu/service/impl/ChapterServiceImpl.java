package com.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.common.exception.GuliException;
import com.guli.edu.entity.Chapter;
import com.guli.edu.entity.Video;
import com.guli.edu.mapper.ChapterMapper;
import com.guli.edu.service.ChapterService;
import com.guli.edu.service.VideoService;
import com.guli.edu.vo.ChapterVo;
import com.guli.edu.vo.VideoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Helen
 * @since 2019-02-23
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private VideoService videoService;

    @Override
    public List<ChapterVo> nestedList(String courseId) {
        ArrayList<ChapterVo> chapterVos = new ArrayList<>();

        QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", courseId);
        chapterQueryWrapper.orderByAsc("sort");
        List<Chapter> chapterList = baseMapper.selectList(chapterQueryWrapper);

        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", courseId);
        videoQueryWrapper.orderByAsc("sort");
        List<Video> videoList = videoService.list(videoQueryWrapper);
        for (Chapter chapter : chapterList) {
            ChapterVo chapterVo = new ChapterVo();
            List<VideoVo> videoVoList = chapterVo.getChildren();
            BeanUtils.copyProperties(chapter, chapterVo);
            for (Video video : videoList) {
                if (chapter.getId().equals(video.getChapterId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    videoVoList.add(videoVo);
                }
            }
            chapterVos.add(chapterVo);
        }
        return chapterVos;
    }

    @Transactional
    @Override
    public boolean removeChapterById(String id) {
        if (videoService.getCountByChapterId(id)) {
            throw new GuliException(20005,"该章节下存在视频课程,请先删除视频课程");
        }
        Integer result = baseMapper.deleteById(id);
        return null != result && result>0;
    }

    @Transactional
    @Override
    public boolean deleteByCourseId(String courseId) {
        QueryWrapper<Chapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        boolean result = this.remove(queryWrapper);
        return result;
    }
}
