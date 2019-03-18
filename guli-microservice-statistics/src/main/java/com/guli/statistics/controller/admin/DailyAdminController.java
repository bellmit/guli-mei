package com.guli.statistics.controller.admin;

import com.guli.common.vo.R;
import com.guli.statistics.service.DailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author ：mei
 * @date ：Created in 2019/3/12 0012 下午 21:16
 * @description：每日数据统计相关
 * @modified By：
 * @version: $
 */
@Api(description = "生成每日统计数据")
@CrossOrigin
@RestController
@RequestMapping("/admin/statistics/daily")
public class DailyAdminController {

    @Autowired
    private DailyService dailyService;

    @ApiOperation("统计当日注册人数")
    @PostMapping("{day}")
    public R createStatisticsByDate(
            @PathVariable String day){
        dailyService.createStatisticsByDay(day);
        return R.ok();
    }

    @ApiOperation("统计时间段内的某类数据")
    @GetMapping("show-chart/{begin}/{end}/{type}")
    public R showChart(
            @PathVariable String type,
            @PathVariable String begin,
            @PathVariable String end
    ){
        Map<String, Object> map = dailyService.getChartData(begin, end, type);
        return R.ok().data(map);
    }

}
