package com.think.datasource.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.think.datasource.system.entity.SysDictData;

import java.util.List;
import java.util.Map;

/**
 * 字典 业务层
 *
 * @author Gary
 */
public interface ISysDictDataService  extends IService<SysDictData> {
    /**
     * 根据条件分页查询字典数据
     *
     * @param dictData 字典数据信息
     * @return 字典数据集合信息
     */
    public IPage<SysDictData> selectDictDataList(SysDictData dictData);

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    public List<SysDictData> selectDictDataByType(String dictType);

    /**
     * 根据字典类型查询字典数据, 返回 map<value, label>
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    public Map<String, String> selectDictDataByTypeForMap(String dictType);

}
