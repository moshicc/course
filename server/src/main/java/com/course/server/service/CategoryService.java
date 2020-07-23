package com.course.server.service;

import com.course.server.domain.Category;
import com.course.server.domain.CategoryExample;
import com.course.server.dto.CategoryDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.CategoryMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
/**
* @author zcc
* @date 2020/4/28 13:50
* @description
*/
@Service
public class CategoryService {
@Resource
private CategoryMapper categoryMapper;

    /**
     * 列表查询
     * @param
     */
    public  List<CategoryDto>  all(){
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);
        List<CategoryDto> categoryDtoList = CopyUtil.copyList(categoryList,CategoryDto.class);
        return categoryDtoList;
    }

/**
* 列表查询
* @param pageDto
*/
public void list(PageDto pageDto){
//设置查询页，每页数
PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
List<Category> categoryList = categoryMapper.selectByExample(categoryExample);
    //设置pageDto的total属性，总条数
    PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
        pageDto.setTotal(pageInfo.getTotal());

        List<CategoryDto> categoryDtoList = CopyUtil.copyList(categoryList,CategoryDto.class);

            //        for (int i = 0, l =categoryList.size(); i < l; i++) {
            //            Category category = categoryList.get(i);
            //            CategoryDto categoryDto = new CategoryDto();
            //            BeanUtils.copyProperties(category,categoryDto);
            //            categoryDtoList.add(categoryDto);
            //        }
            //设置pageDto的list属性，返回给前端的查询结果（封装在categorydto里面了）
            pageDto.setList(categoryList);
            //最终返回pagedto，因为这个pagedto是从前端传入的，不返回前端也能拿到这个pagedto
            }

            /**
            * 保存。id有值时更新，无值时新增
            * @param categoryDto
            */
            public void save(CategoryDto categoryDto){
            //将传入的categoryDto复制转换成category
            Category category = CopyUtil.copy(categoryDto,Category.class);
            if (StringUtils.isEmpty(categoryDto.getId())){
            //如果id为空就插入该数据
            this.insert(category);
            }else {
            //如果id不为空，就修改
            this.update(category);
            }
            }

            /**
            * 新增
            * @param category
            */
            private void insert(Category category){
            //先生成新的 id
            category.setId(UuidUtil.getShortUuid());
            //执行插入操作
            categoryMapper.insert(category);
            }

            /**
            * 更新
            * @param category
            */
            private void update(Category category){
            //因为是更新操作，所以category中已经有id了
            categoryMapper.updateByPrimaryKey(category);
            }

            /**
            * 删除
            * @param id
            */
            public void delete(String id){
            categoryMapper.deleteByPrimaryKey(id);
            }
            }
