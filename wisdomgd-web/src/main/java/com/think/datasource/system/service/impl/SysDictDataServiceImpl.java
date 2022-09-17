package com.think.datasource.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.think.datasource.system.entity.SysDictData;
import com.think.datasource.system.mapper.SysDictDataMapper;
import com.think.datasource.system.service.ISysDictDataService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典 业务层处理
 *
 * @author Gary
 */
@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements ISysDictDataService {
    /**
     * 根据条件分页查询字典数据
     *
     * @param dictData 字典数据信息
     * @return 字典数据集合信息
     */
    @Override
    public IPage<SysDictData> selectDictDataList(SysDictData dictData) {
        QueryWrapper<SysDictData> qw = new QueryWrapper<>();
        if (StrUtil.isNotBlank(dictData.getDictType())) {
            qw.eq("dict_type", dictData.getDictType());
        }
        if (StrUtil.isNotBlank(dictData.getDictValue())) {
            qw.like("dict_value", dictData.getDictValue());
        }
        if (ObjectUtil.isNotNull(dictData.getStatus())) {
            qw.eq("status", dictData.getStatus());
        }
        IPage<SysDictData> page = null;
        if (dictData.getPageNum() > 0 && dictData.getPageSize() > 0) {
            page = new Page<>(dictData.getPageNum(), dictData.getPageSize());
        } else {
            page = new Page<>(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT);
        }
        return baseMapper.selectPage(page, qw);
    }

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    @Override
    public List<SysDictData> selectDictDataByType(String dictType) {
        QueryWrapper<SysDictData> qw = new QueryWrapper<>();
        qw.eq("status", "0").eq("dict_type", dictType).orderByAsc("dict_sort");
        return baseMapper.selectList(qw);
    }

    /**
     * 根据字典类型查询字典数据, 返回 map<value, label>
     * @param dictType 字典类型
     * @return
     */
    @Override
    public Map<String, String> selectDictDataByTypeForMap(String dictType) {
        QueryWrapper<SysDictData> qw = new QueryWrapper<>();
        qw.eq("status", "0").eq("dict_type", dictType).orderByAsc("dict_sort");
        List<SysDictData> dictList = baseMapper.selectList(qw);
        Map<String, String> dictMap = new HashMap<>();
        dictList.forEach(d -> {
            dictMap.put(d.getDictKey(), d.getDictValue());
        });
        return dictMap;
    }
}
