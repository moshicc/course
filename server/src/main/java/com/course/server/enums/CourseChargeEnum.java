package com.course.server.enums;

/**
 * @author zcc
 * @date 2020/5/22 17:35
 * @description
 */

public enum CourseChargeEnum {

    CHARGE("C","收费"),
    FREE("F","免费");

    private String code;

    private String desc;

    CourseChargeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
