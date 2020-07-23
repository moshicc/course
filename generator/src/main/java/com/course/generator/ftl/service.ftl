package com.course.server.service;

import com.course.server.domain.${Domain};
import com.course.server.domain.${Domain}Example;
import com.course.server.dto.${Domain}Dto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.${Domain}Mapper;
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
<#list typeSet as type>
    <#if type ='Date'>
import java.util.Date;
    </#if>
</#list>
/**
* @author zcc
* @date 2020/4/28 13:50
* @description
*/
@Service
public class ${Domain}Service {
@Resource
private ${Domain}Mapper ${domain}Mapper;

/**
* 列表查询
* @param pageDto
*/
public void list(PageDto pageDto){
//设置查询页，每页数
PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
${Domain}Example ${domain}Example = new ${Domain}Example();
<#list fieldList as field>
    <#if field.nameHump =='sort'>
        ${domain}Example.setOrderByClause("sort asc");
    </#if>
</#list>
List<${Domain}> ${domain}List = ${domain}Mapper.selectByExample(${domain}Example);
    //设置pageDto的total属性，总条数
    PageInfo<${Domain}> pageInfo = new PageInfo<>(${domain}List);
        pageDto.setTotal(pageInfo.getTotal());

        List<${Domain}Dto> ${domain}DtoList = CopyUtil.copyList(${domain}List,${Domain}Dto.class);

            //        for (int i = 0, l =${domain}List.size(); i < l; i++) {
            //            ${Domain} ${domain} = ${domain}List.get(i);
            //            ${Domain}Dto ${domain}Dto = new ${Domain}Dto();
            //            BeanUtils.copyProperties(${domain},${domain}Dto);
            //            ${domain}DtoList.add(${domain}Dto);
            //        }
            //设置pageDto的list属性，返回给前端的查询结果（封装在${domain}dto里面了）
            pageDto.setList(${domain}List);
            //最终返回pagedto，因为这个pagedto是从前端传入的，不返回前端也能拿到这个pagedto
            }

            /**
            * 保存。id有值时更新，无值时新增
            * @param ${domain}Dto
            */
            public void save(${Domain}Dto ${domain}Dto){
            //将传入的${domain}Dto复制转换成${domain}
            ${Domain} ${domain} = CopyUtil.copy(${domain}Dto,${Domain}.class);
            if (StringUtils.isEmpty(${domain}Dto.getId())){
            //如果id为空就插入该数据
            this.insert(${domain});
            }else {
            //如果id不为空，就修改
            this.update(${domain});
            }
            }

            /**
            * 新增
            * @param ${domain}
            */
            private void insert(${Domain} ${domain}){
            <#list typeSet as type>
                <#if type ='Date'>
                    Date now = new Date();
                </#if>
            </#list>
            <#list fieldList as field>
                <#if field.nameHump =='createdAt'>
                    ${domain}.setCreatedAt(now);
                </#if>
                <#if field.nameHump =='updatedAt'>
                    ${domain}.setUpdatedAt(now);
                </#if>
            </#list>
            //先生成新的 id
            ${domain}.setId(UuidUtil.getShortUuid());
            //执行插入操作
            ${domain}Mapper.insert(${domain});
            }

            /**
            * 更新
            * @param ${domain}
            */
            private void update(${Domain} ${domain}){
    <#list fieldList as field>
        <#if field.nameHump =='updatedAt'>
            ${domain}.setUpdatedAt(new Date());
        </#if>
    </#list>
            //因为是更新操作，所以${domain}中已经有id了
            ${domain}Mapper.updateByPrimaryKey(${domain});
            }

            /**
            * 删除
            * @param id
            */
            public void delete(String id){
            ${domain}Mapper.deleteByPrimaryKey(id);
            }
            }
