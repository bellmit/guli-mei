package com.guli.common.constants;

import lombok.Getter;

/**
 * @author mei
 * 统一结果返回
 */
@Getter
public enum ResultCodeEnum {

    /**
     *统一返回结果对象枚举
     */
    SUCCESS(true , 20000 , "成功"),
    UNKNOW_REASON(false , 20001 , "未知错误"),
    BAD_SQL_GRAMMAR(false , 21001, "sql语法错误"),
    JSON_PARSE_ERROR(false , 21002, "json解析异常"),
    PARAM_ERROR(false , 21003 , "参数错误"),
    FILE_UPLOAD_ERROR(false , 21004 , "文件上传错误"),
    EXCEL_DATA_IMPORT_ERROR(false , 21005 , "Excel数据导入错误");

    private Boolean success;
    private Integer code;
    private String message;

    private ResultCodeEnum(Boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }


}
