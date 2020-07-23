package com.course.business.controller.admin;

import com.course.server.dto.CategoryDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.CategoryService;
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
@RequestMapping("/admin/category")
public class CategoryController {
    private static final Logger LOG = LoggerFactory.getLogger(CategoryController.class);
    public static final String BUSINESS_NAME ="分类";
    @Resource
    private CategoryService categoryService;

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
         categoryService.list(pageDto);
         responseDto.setContent(pageDto);
         return responseDto;
    }

    /**
     * 保存，id有值时更新，无值时新增
     * @param categoryDto
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody CategoryDto categoryDto){
            //保存校验
                    ValidatorUtil.require(categoryDto.getParent(),"父id");
                    ValidatorUtil.require(categoryDto.getName(),"名称");
                    ValidatorUtil.length(categoryDto.getName(),"名称",1,50);

        ResponseDto responseDto = new ResponseDto();
        categoryService.save(categoryDto);
        responseDto.setContent(categoryDto);
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
        categoryService.delete(id);
        return responseDto;
    }
}
