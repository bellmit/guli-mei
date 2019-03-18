package com.guli.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.statistics.client.UcenterClient;
import com.guli.statistics.entity.Daily;
import com.guli.statistics.mapper.DailyMapper;
import com.guli.statistics.service.DailyService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author Mei
 * @since 2019-03-12
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {

    @Autowired
    private UcenterClient ucenterClient;

    @Transactional
    @Override
    public void createStatisticsByDay(String day) {
        QueryWrapper<Daily> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date_calculated", day);
        Integer count = baseMapper.selectCount(queryWrapper);
        if (count > 0) {
            baseMapper.delete(queryWrapper);
        }
        //获取统计信息
        Integer registerNum = (Integer) ucenterClient.registerCount(day).getData().get("countRegister");
        Integer loginNum = RandomUtils.nextInt(100, 200);
        Integer videoViewNum = RandomUtils.nextInt(100, 200);
        Integer courseNum = RandomUtils.nextInt(100, 200);
        Daily daily = new Daily(null, day, registerNum, loginNum, videoViewNum, courseNum, null, null);
        baseMapper.insert(daily);
    }

    @Override
    public Map<String, Object> getChartData(String begin, String end, String type) {
        QueryWrapper<Daily> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(type,"date_calculated");
        queryWrapper.between("date_calculated",begin,end);
        List<Daily> dailyList = baseMapper.selectList(queryWrapper);

        Map<String, Object> map = new HashMap<String, Object>();
        List<Integer> dataList = new ArrayList<Integer>();
        List<String> dateList = new ArrayList<String>();
        map.put("dataList",dataList);
        map.put("dateList",dateList);

        for (Daily daily : dailyList) {
            dateList.add(daily.getDateCalculated());
            switch (type){
                case "register_num":
                    dataList.add(daily.getLoginNum());
                    break;
                case "login_num":
                    dataList.add(daily.getLoginNum());
                    break;
                case "video_view_num":
                    dataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    dataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        return map;
    }
}
