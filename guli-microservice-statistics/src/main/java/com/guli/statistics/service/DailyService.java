package com.guli.statistics.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.statistics.entity.Daily;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author Mei
 * @since 2019-03-12
 */
public interface DailyService extends IService<Daily> {

    /**
     * 统计生成每日注册人数
     * @param day
     */
    void createStatisticsByDay(String day);

    /**
     * 展示统计数据
     * @param begin
     * @param end
     * @param type
     * @return
     */
    Map<String, Object> getChartData(String begin, String end, String type);

}
