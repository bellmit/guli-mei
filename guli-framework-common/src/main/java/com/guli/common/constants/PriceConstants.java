package com.guli.common.constants;

import java.math.BigDecimal;

/**
 * @author ：mei
 * @date ：Created in 2019/3/5 0005 上午 11:25
 * @description：金额数字精度常量
 * @modified By：
 * @version: $
 */
public class PriceConstants {
    /**
     * 存储精度
     */
    public static final int STORE_SCALE = 4;
    /**
     * 运算精度
     */
    public static final int CALL_SCALE = 8;
    /**
     * 显示精度
     */
    public static final int DISPLAY_SCALE = 2;
    /**
     * 系统级别的0
     */
    public static final BigDecimal ZERO = new BigDecimal("0.0000");
}
