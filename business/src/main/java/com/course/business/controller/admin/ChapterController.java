package com.course.business.controller.admin;

import com.course.server.domain.Chapter;
import com.course.server.dto.ChapterDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.ChapterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zcc
 * @date 2020/4/27 22:57
 * @description
 */
@RestController
@RequestMapping("/admin/chapter")
public class ChapterController {
    private static final Logger LOG = LoggerFactory.getLogger(ChapterController.class);
    @Resource
    private ChapterService chapterService;
    @RequestMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto){
        //pageDto用来接收入参，也用来返回结果.
        //前端传入page和size，通过list()方法，设置pagedto的total和list属性，最终返回给前端
        //因为前端是以流的 方法传递page和size,(不是以表单方式)，所以后端接收要加个@RequestBody
        LOG.info("pageDto{}",pageDto);
        ResponseDto responseDto = new ResponseDto();
         chapterService.list(pageDto);
         responseDto.setContent(pageDto);
         return responseDto;
    }
    @RequestMapping("/save")
    public ResponseDto save(@RequestBody ChapterDto chapterDto){
        LOG.info("chapterDto{}",chapterDto);
        ResponseDto responseDto = new ResponseDto();
        chapterService.save(chapterDto);
        responseDto.setContent(chapterDto);
        return responseDto;
    }
}
