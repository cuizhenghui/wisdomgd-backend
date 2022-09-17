package com.think.datasource.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.think.datasource.system.entity.SysDictData;
import org.apache.ibatis.annotations.Param;

/**
 * 字典表 数据层
 *
 * @author Gary
 */
public interface SysDictDataMapper extends BaseMapper<SysDictData> {

    Integer selectMaxDictValue(@Param("dictType") String dictType);
}
