package com.course.server.service;

import com.course.server.domain.Chapter;
import com.course.server.domain.ChapterExample;
import com.course.server.dto.ChapterDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.ChapterMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
/**
 * @author zcc
 * @date 2020/4/28 13:50
 * @description
 */
@Service
public class ChapterService {
    @Resource
    private ChapterMapper chapterMapper;

    public void list(PageDto pageDto){
        //设置查询页，每页数
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        ChapterExample chapterExample = new ChapterExample();
        List<Chapter> chapterList = chapterMapper.selectByExample(chapterExample);
        //设置pageDto的total属性，总条数
        PageInfo<Chapter> pageInfo = new PageInfo<>(chapterList);
        pageDto.setTotal(pageInfo.getTotal());

        List<ChapterDto> chapterDtoList = CopyUtil.copyList(chapterList,ChapterDto.class);

//        for (int i = 0, l =chapterList.size(); i < l; i++) {
//            Chapter chapter = chapterList.get(i);
//            ChapterDto chapterDto = new ChapterDto();
//            BeanUtils.copyProperties(chapter,chapterDto);
//            chapterDtoList.add(chapterDto);
//        }
        //设置pageDto的list属性，返回给前端的查询结果（封装在chapterdto里面了）
        pageDto.setList(chapterList);
        //最终返回pagedto，因为这个pagedto是从前端传入的，不返回前端也能拿到这个pagedto
    }

    public void save(ChapterDto chapterDto){
        //将传入的chapterDto复制转换成chapter
        Chapter chapter = CopyUtil.copy(chapterDto,Chapter.class);
        if (StringUtils.isEmpty(chapterDto.getId())){
            //如果id为空就插入该数据
            this.insert(chapter);
        }else {
            //如果id不为空，就修改
            this.update(chapter);
        }
    }

    private void insert(Chapter chapter){
        //先生成新的 id
        chapter.setId(UuidUtil.getShortUuid());
        //执行插入操作
        chapterMapper.insert(chapter);
    }

    private void update(Chapter chapter){
        //因为是更新操作，所以chapter中已经有id了
        chapterMapper.updateByPrimaryKey(chapter);
    }
}
