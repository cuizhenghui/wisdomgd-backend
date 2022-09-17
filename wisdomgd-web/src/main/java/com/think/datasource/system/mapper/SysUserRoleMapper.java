package com.think.datasource.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.think.datasource.system.entity.SysUserRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户与角色关联表 数据层
 *
 * @author Gary
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
    /**
     * 批量删除用户和角色关联
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteUserRole(Long[] ids);

    /**
     * 删除用户和角色关联信息
     *
     * @param userRole 用户和角色关联信息
     * @return 结果
     */
    public int deleteUserRoleInfo(SysUserRole userRole);

    /**
     * 批量取消授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要删除的用户数据ID
     * @return 结果
     */
    public int deleteUserRoleInfos(@Param("roleId") Long roleId, @Param("userIds") Long[] userIds);

    @Select("SELECT u.user_id FROM sys_user u, sys_user_role ur, sys_role r WHERE u.user_id = ur.user_id AND ur.role_id = r.role_id AND r.role_key = #{rowKey} AND u.del_flag = 0 AND u.STATUS = 0")
    List<Long> getUserIdsByRoleKey(@Param("roleKey") String roleKey);
}
