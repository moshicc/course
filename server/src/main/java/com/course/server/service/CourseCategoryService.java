package com.course.server.service;

import com.course.server.domain.CourseCategory;
import com.course.server.domain.CourseCategoryExample;
import com.course.server.dto.CategoryDto;
import com.course.server.dto.CourseCategoryDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.CourseCategoryMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
* @author zcc
* @date 2020/4/28 13:50
* @description
*/
@Service
public class CourseCategoryService {
@Resource
private CourseCategoryMapper courseCategoryMapper;

/**
* 列表查询
* @param pageDto
*/
public void list(PageDto pageDto){
//设置查询页，每页数
PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
CourseCategoryExample courseCategoryExample = new CourseCategoryExample();
List<CourseCategory> courseCategoryList = courseCategoryMapper.selectByExample(courseCategoryExample);
    //设置pageDto的total属性，总条数
    PageInfo<CourseCategory> pageInfo = new PageInfo<>(courseCategoryList);
        pageDto.setTotal(pageInfo.getTotal());

        List<CourseCategoryDto> courseCategoryDtoList = CopyUtil.copyList(courseCategoryList,CourseCategoryDto.class);
            pageDto.setList(courseCategoryList);
            }

            /**
            * 保存。id有值时更新，无值时新增
            * @param courseCategoryDto
            */
            public void save(CourseCategoryDto courseCategoryDto){
            //将传入的courseCategoryDto复制转换成courseCategory
            CourseCategory courseCategory = CopyUtil.copy(courseCategoryDto,CourseCategory.class);
            if (StringUtils.isEmpty(courseCategoryDto.getId())){
            //如果id为空就插入该数据
            this.insert(courseCategory);
            }else {
            //如果id不为空，就修改
            this.update(courseCategory);
            }
            }

            /**
            * 新增
            * @param courseCategory
            */
            private void insert(CourseCategory courseCategory){
            //先生成新的 id
            courseCategory.setId(UuidUtil.getShortUuid());
            //执行插入操作
            courseCategoryMapper.insert(courseCategory);
            }

            /**
            * 更新
            * @param courseCategory
            */
            private void update(CourseCategory courseCategory){
            //因为是更新操作，所以courseCategory中已经有id了
            courseCategoryMapper.updateByPrimaryKey(courseCategory);
            }

            /**
            * 删除
            * @param id
            */
            public void delete(String id){
            courseCategoryMapper.deleteByPrimaryKey(id);
            }
            /* *
             * @description:批量保存
             * @param courseId
             * @param dtoList
             * @return
             * @throws
             * @author zcc13
             * @date 2020/8/17 22:27
             */
            @Transactional
            public void saveBatch(String courseId, List<CategoryDto> dtoList){
                //批量保存之前先清空
                CourseCategoryExample example = new CourseCategoryExample();
                //根据courseId来删除
                example.createCriteria().andCourseIdEqualTo(courseId);
                courseCategoryMapper.deleteByExample(example);

                for (int i = 0, l =dtoList.size(); i < l; i++) {
                    CategoryDto categoryDto = dtoList.get(i);
                    CourseCategory courseCategory = new CourseCategory();
                    courseCategory.setId(UuidUtil.getShortUuid());
                    courseCategory.setCourseId(courseId);
                    courseCategory.setCategoryId(categoryDto.getId());
                    insert(courseCategory);
                }
            }
            /* *
             * @description:根据课程id 查找分类
             * @param courseId
             * @return {@link java.util.List<com.course.server.dto.CourseCategoryDto>}
             * @throws
             * @author zcc13
             * @date 2020/8/17 22:37
             */
            public List<CourseCategoryDto> listByCourse(String courseId){
                CourseCategoryExample example = new CourseCategoryExample();
                example.createCriteria().andCourseIdEqualTo(courseId);
                List<CourseCategory> courseCategoryList = courseCategoryMapper.selectByExample(example);
                return CopyUtil.copyList(courseCategoryList,CourseCategoryDto.class);
            }
            }


