package com.course.server.mapper.my;

import org.apache.ibatis.annotations.Param;

/**
 * @author zcc
 * @date 2020/6/9 22:23
 * @description
 */

public interface MyCourseMapper {
    int updateTime(@Param("courseId") String courseId );
}
