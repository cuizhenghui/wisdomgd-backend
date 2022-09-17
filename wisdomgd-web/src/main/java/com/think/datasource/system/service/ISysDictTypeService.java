package com.think.datasource.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.think.datasource.system.entity.SysDictType;

/**
 * 字典 业务层
 *
 * @author Gary
 */
public interface ISysDictTypeService  extends IService<SysDictType> {
    /**
     * 根据条件分页查询字典类型
     *
     * @param dictType 字典类型信息
     * @return 字典类型集合信息
     */
    public IPage<SysDictType> selectDictTypeList(SysDictType dictType);

    /**
     * 修改保存字典类型信息
     *
     * @param dictType 字典类型信息
     * @return 结果
     */
    public int updateDictType(SysDictType dictType);

    /**
     * 校验字典类型称是否唯一
     *
     * @param dictType 字典类型
     * @return 结果
     */
    public String checkDictTypeUnique(SysDictType dictType);
}
