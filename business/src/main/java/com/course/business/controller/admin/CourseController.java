package com.course.business.controller.admin;

import com.course.server.dto.CourseCategoryDto;
import com.course.server.dto.CourseDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.CourseCategoryService;
import com.course.server.service.CourseService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zcc
 * @date 2020/4/27 22:57
 * @description
 */
@RestController
@RequestMapping("/admin/course")
public class CourseController {
    private static final Logger LOG = LoggerFactory.getLogger(CourseController.class);
    public static final String BUSINESS_NAME ="课程";
    @Resource
    private CourseService courseService;
    @Resource
    private CourseCategoryService courseCategoryService;

    /**
     * 列表查询
     * @param pageDto
     * @return
     */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto){
        //pageDto用来接收入参，也用来返回结果.
        //前端传入page和size，通过list()方法，设置pagedto的total和list属性，最终返回给前端
        //因为前端是以流的 方法传递page和size,(不是以表单方式)，所以后端接收要加个@RequestBody
        ResponseDto responseDto = new ResponseDto();
         courseService.list(pageDto);
         responseDto.setContent(pageDto);
         return responseDto;
    }

    /**
     * 保存，id有值时更新，无值时新增
     * @param courseDto
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody CourseDto courseDto){
            //保存校验
                    ValidatorUtil.require(courseDto.getName(),"名称");
                    ValidatorUtil.length(courseDto.getName(),"名称",1,50);
                    ValidatorUtil.length(courseDto.getSummary(),"概述",1,2000);
                    ValidatorUtil.length(courseDto.getImage(),"封面",1,100);

        ResponseDto responseDto = new ResponseDto();
        courseService.save(courseDto);
        responseDto.setContent(courseDto);
        return responseDto;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id){
        ResponseDto responseDto = new ResponseDto();
        courseService.delete(id);
        return responseDto;
    }
    /* *
     * @description: 根据课程id 查找课程分类信息
     * @param courseId
     * @return {@link com.course.server.dto.ResponseDto}
     * @throws
     * @author zcc13
     * @date 2020/8/17 22:43
     */
    @PostMapping("/list-category/{courseId}")
    public ResponseDto listCategory(@PathVariable(value ="courseId") String courseId) {
        ResponseDto responseDto = new ResponseDto();
        List<CourseCategoryDto> dtoList = courseCategoryService.listByCourse(courseId);
        responseDto.setContent(dtoList);
        return responseDto;
    }
}
