package com.course.server.service;

import com.course.server.domain.Section;
import com.course.server.domain.SectionExample;
import com.course.server.dto.SectionDto;
import com.course.server.dto.SectionPageDto;
import com.course.server.enums.SectionChargeEnum;
import com.course.server.mapper.SectionMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
public class SectionService {
    @Resource
    private SectionMapper sectionMapper;
    @Resource
    private CourseService courseService;

    /**
     * 列表查询
     *
     * @param
     */
    public void list(SectionPageDto sectionPageDto) {
//设置查询页，每页数
        PageHelper.startPage(sectionPageDto.getPage(), sectionPageDto.getSize());
        SectionExample sectionExample = new SectionExample();
//这句相当于写where 语句
        SectionExample.Criteria criteria = sectionExample.createCriteria();
        if (!StringUtils.isEmpty(sectionPageDto.getCourseId())) {
            criteria.andCourseIdEqualTo(sectionPageDto.getCourseId());
        }
        if (!StringUtils.isEmpty(sectionPageDto.getChapterId())) {
            criteria.andChapterIdEqualTo(sectionPageDto.getChapterId());
        }
        sectionExample.setOrderByClause("sort asc");
        List<Section> sectionList = sectionMapper.selectByExample(sectionExample);
        //设置pageDto的total属性，总条数
        PageInfo<Section> pageInfo = new PageInfo<>(sectionList);
        sectionPageDto.setTotal(pageInfo.getTotal());

        List<SectionDto> sectionDtoList = CopyUtil.copyList(sectionList, SectionDto.class);

        //        for (int i = 0, l =sectionList.size(); i < l; i++) {
        //            Section section = sectionList.get(i);
        //            SectionDto sectionDto = new SectionDto();
        //            BeanUtils.copyProperties(section,sectionDto);
        //            sectionDtoList.add(sectionDto);
        //        }
        //设置pageDto的list属性，返回给前端的查询结果（封装在sectiondto里面了）
        sectionPageDto.setList(sectionDtoList);
        //最终返回pagedto，因为这个pagedto是从前端传入的，不返回前端也能拿到这个pagedto
    }

    /**
     * 保存。id有值时更新，无值时新增
     *
     * @param sectionDto
     */
    public void save(SectionDto sectionDto) {
        //将传入的sectionDto复制转换成section
        Section section = CopyUtil.copy(sectionDto, Section.class);
        if (StringUtils.isEmpty(sectionDto.getId())) {
            //如果id为空就插入该数据
            this.insert(section);
        } else {
            //如果id不为空，就修改
            this.update(section);
        }
        courseService.updateTime(sectionDto.getCourseId());
    }

    /**
     * 新增
     *
     * @param section
     */
    private void insert(Section section) {
        Date now = new Date();
        section.setCreatedAt(now);
        section.setUpdatedAt(now);
        //先生成新的 id
        section.setId(UuidUtil.getShortUuid());
        section.setCharge(SectionChargeEnum.CHARGE.getCode());
        //执行插入操作
        sectionMapper.insert(section);
    }

    /**
     * 更新
     *
     * @param section
     */
    private void update(Section section) {
        section.setUpdatedAt(new Date());
        //因为是更新操作，所以section中已经有id了
        sectionMapper.updateByPrimaryKey(section);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void delete(String id) {
        sectionMapper.deleteByPrimaryKey(id);
    }
}
