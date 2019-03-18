package com.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.common.exception.GuliException;
import com.guli.edu.client.VodClient;
import com.guli.edu.entity.Video;
import com.guli.edu.form.VideoInfoForm;
import com.guli.edu.mapper.VideoMapper;
import com.guli.edu.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author Helen
 * @since 2019-02-23
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Autowired
    private VodClient vodClient;

    @Override
    public boolean getCountByChapterId(String chapterId) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id", chapterId);
        Integer count = baseMapper.selectCount(queryWrapper);
        return null != count && count > 0;
    }

    @Transactional
    @Override
    public void updateVideoInfoById(VideoInfoForm videoInfoForm) {
        Video video = new Video();
        BeanUtils.copyProperties(videoInfoForm, video);
        boolean result = this.updateById(video);
        if (!result) {
            throw new GuliException(20001, "课时更新失败!");
        }
    }

    @Override
    public VideoInfoForm getVideoInfoFormById(String id) {
        Video video = baseMapper.selectById(id);
        if (video == null) {
            throw new GuliException(20001, "课时不存在");
        }
        VideoInfoForm videoInfoForm = new VideoInfoForm();
        BeanUtils.copyProperties(video, videoInfoForm);
        return videoInfoForm;
    }

    @Transactional
    @Override
    public void saveVideoInfo(VideoInfoForm videoInfoForm) {
        Video video = new Video();
        BeanUtils.copyProperties(videoInfoForm, video);
        boolean result = this.save(video);
        if (!result) {
            throw new GuliException(20001, "添加失败");
        }
    }

    @Transactional
    @Override
    public boolean removeVideoById(String id) {
        //获取阿里云视频id
        Video video = baseMapper.selectById(id);
        String videoSourceId = video.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)) {
            //删除阿里云上的视频
            vodClient.removeVideo(videoSourceId);
        }
        Integer result = baseMapper.deleteById(id);
        return null != result && result > 0;
    }

    @Transactional
    @Override
    public boolean deleteByCourseId(String courseId) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        List<Video> videoList = baseMapper.selectList(queryWrapper);
        if (videoList != null) {
            List<String> videoSourceIdList = new ArrayList<>();
            for (Video video : videoList) {
                String videoSourceId = video.getVideoSourceId();
                if (!StringUtils.isEmpty(videoSourceId)) {
                    videoSourceIdList.add(videoSourceId);
                }
            }
            vodClient.removeVideoList(videoSourceIdList);
        }

        boolean result = this.remove(queryWrapper);
        return result;
    }
}
