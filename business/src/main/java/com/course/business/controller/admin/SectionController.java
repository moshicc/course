package com.course.business.controller.admin;

import com.course.server.dto.ResponseDto;
import com.course.server.dto.SectionDto;
import com.course.server.dto.SectionPageDto;
import com.course.server.service.SectionService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zcc
 * @date 2020/4/27 22:57
 * @description
 */
@RestController
@RequestMapping("/admin/section")
public class SectionController {
    private static final Logger LOG = LoggerFactory.getLogger(SectionController.class);
    public static final String BUSINESS_NAME ="小节";
    @Resource
    private SectionService sectionService;

    /**
     * 列表查询
     * @param
     * @return
     */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody SectionPageDto sectionPageDto){
        //pageDto用来接收入参，也用来返回结果.
        //前端传入page和size，通过list()方法，设置pagedto的total和list属性，最终返回给前端
        //因为前端是以流的 方法传递page和size,(不是以表单方式)，所以后端接收要加个@RequestBody
        ResponseDto responseDto = new ResponseDto();
        ValidatorUtil.require(sectionPageDto.getCourseId(),"课程id");
        ValidatorUtil.require(sectionPageDto.getChapterId(),"大章id");
         sectionService.list(sectionPageDto);
         responseDto.setContent(sectionPageDto);
         return responseDto;
    }

    /**
     * 保存，id有值时更新，无值时新增
     * @param sectionDto
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody SectionDto sectionDto){
            //保存校验
                    ValidatorUtil.require(sectionDto.getTitle(),"标题");
                    ValidatorUtil.length(sectionDto.getTitle(),"标题",1,50);
                    ValidatorUtil.length(sectionDto.getVideo(),"视频",1,200);

        ResponseDto responseDto = new ResponseDto();
        sectionService.save(sectionDto);
        responseDto.setContent(sectionDto);
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
        sectionService.delete(id);
        return responseDto;
    }
}
