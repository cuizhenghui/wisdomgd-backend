package com.think.datasource.system.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.think.common.constant.UserConstants;
import com.think.common.utils.StringUtils;
import com.think.datasource.system.entity.SysDictData;
import com.think.datasource.system.entity.SysDictType;
import com.think.datasource.system.mapper.SysDictTypeMapper;
import com.think.datasource.system.service.ISysDictDataService;
import com.think.datasource.system.service.ISysDictTypeService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 字典 业务层处理
 *
 * @author Gary
 */
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements ISysDictTypeService {

    @Autowired
    ISysDictDataService sysDictDataService;
    /**
     * 根据条件分页查询字典类型
     *
     * @param dictType 字典类型信息
     * @return 字典类型集合信息
     */
    @Override
    public IPage<SysDictType> selectDictTypeList(SysDictType dictType) {
        QueryWrapper<SysDictType> qw = new QueryWrapper<>();
        if (StrUtil.isNotBlank(dictType.getDictName())) {
            qw.like("dict_name", dictType.getDictName());
        }
        if (ObjectUtil.isNotNull(dictType.getStatus())) {
            qw.eq("status", dictType.getStatus());
        }
        if (StrUtil.isNotBlank(dictType.getDictType())) {
            qw.like("dict_type", dictType.getDictType());
        }
        if (StrUtil.isNotBlank(dictType.getBeginTime())) {
            qw.ge("create_time", DateUtil.parseDate(dictType.getBeginTime()));
        }
        if (StrUtil.isNotBlank(dictType.getEndTime())) {
            qw.le("create_time", DateUtil.parseDate(dictType.getEndTime()));
        }
        IPage<SysDictType> page = null;
        if (ObjectUtil.isNotNull(dictType.getPageNum()) && ObjectUtil.isNotNull(dictType.getPageSize())
                && dictType.getPageNum() > 0 && dictType.getPageSize() > 0) {
            page = new Page<>(dictType.getPageNum(), dictType.getPageSize());
        } else {
            page = new Page<>(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT);
        }
        return baseMapper.selectPage(page, qw);
    }

    /**
     * 修改保存字典类型信息
     *
     * @param dictType 字典类型信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateDictType(SysDictType dictType) {
        SysDictType oldDict = baseMapper.selectById(dictType.getDictId());
        SysDictData dictData = new SysDictData();
        dictData.setDictType(dictType.getDictType());
        UpdateWrapper<SysDictData> uw = new UpdateWrapper<>();
        uw.eq("dict_type", oldDict.getDictType());
        sysDictDataService.update(dictData, uw);
        return baseMapper.updateById(dictType);
    }

    /**
     * 校验字典类型称是否唯一
     *
     * @param dict 字典类型
     * @return 结果
     */
    @Override
    public String checkDictTypeUnique(SysDictType dict) {
        Long dictId = ObjectUtil.isNull(dict.getDictId()) ? -1L : dict.getDictId();
        QueryWrapper<SysDictType> qw = new QueryWrapper<>();
        qw.eq("dict_type", dict.getDictType());
        SysDictType dictType = baseMapper.selectOne(qw);
        if (StringUtils.isNotNull(dictType) && dictType.getDictId().longValue() != dictId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
}
