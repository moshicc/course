package com.course.server.service;

import com.course.server.domain.Course;
import com.course.server.domain.CourseExample;
import com.course.server.dto.CourseDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.CourseMapper;
import com.course.server.mapper.my.MyCourseMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author zcc
 * @date 2020/4/28 13:50
 * @description
 */
@Service
public class CourseService {
    private static final Logger LOG = LoggerFactory.getLogger(CourseService.class);
    @Resource
    private CourseMapper courseMapper;
    @Resource
    private MyCourseMapper myCourseMapper;
    @Resource
    private CourseCategoryService courseCategoryService;

    /**
     * 列表查询
     *
     * @param pageDto
     */
    public void list(PageDto pageDto) {
//设置查询页，每页数
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        CourseExample courseExample = new CourseExample();
        courseExample.setOrderByClause("sort asc");
        List<Course> courseList = courseMapper.selectByExample(courseExample);
        //设置pageDto的total属性，总条数
        PageInfo<Course> pageInfo = new PageInfo<>(courseList);
        pageDto.setTotal(pageInfo.getTotal());

        List<CourseDto> courseDtoList = CopyUtil.copyList(courseList, CourseDto.class);

        //        for (int i = 0, l =courseList.size(); i < l; i++) {
        //            Course course = courseList.get(i);
        //            CourseDto courseDto = new CourseDto();
        //            BeanUtils.copyProperties(course,courseDto);
        //            courseDtoList.add(courseDto);
        //        }
        //设置pageDto的list属性，返回给前端的查询结果（封装在coursedto里面了）
        pageDto.setList(courseDtoList);
        //最终返回pagedto，因为这个pagedto是从前端传入的，不返回前端也能拿到这个pagedto
    }

    /**
     * 保存。id有值时更新，无值时新增
     *
     * @param courseDto
     */
    public void save(CourseDto courseDto) {
        //将传入的courseDto复制转换成course
        Course course = CopyUtil.copy(courseDto, Course.class);
        if (StringUtils.isEmpty(courseDto.getId())) {
            //如果id为空就插入该数据
            this.insert(course);
        } else {
            //如果id不为空，就修改
            this.update(course);
        }
        //批量保存课程分类
        courseCategoryService.saveBatch(courseDto.getId(),courseDto.getCategorys());
    }

    /**
     * 新增
     *
     * @param course
     */
    private void insert(Course course) {
        Date now = new Date();
        course.setCreatedAt(now);
        course.setUpdatedAt(now);
        //先生成新的 id
        course.setId(UuidUtil.getShortUuid());
        //执行插入操作
        courseMapper.insert(course);
    }

    /**
     * 更新
     *
     * @param course
     */
    private void update(Course course) {
        course.setUpdatedAt(new Date());
        //因为是更新操作，所以course中已经有id了
        courseMapper.updateByPrimaryKey(course);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void delete(String id) {
        courseMapper.deleteByPrimaryKey(id);
    }
    /**
     * 更新课程时长
     */
    public void updateTime(String courseId){
        LOG.info("更新课程时长{}",courseId);
        myCourseMapper.updateTime(courseId);
    }
}
