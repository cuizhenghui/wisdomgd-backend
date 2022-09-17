package com.think.datasource.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.think.common.constant.UserConstants;
import com.think.common.exception.CustomException;
import com.think.common.utils.StringUtils;
import com.think.datasource.system.entity.SysPost;
import com.think.datasource.system.mapper.SysPostMapper;
import com.think.datasource.system.service.ISysPostService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 岗位信息 服务层处理
 *
 * @author Gary
 */
@Service
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements ISysPostService {

    /**
     * 查询岗位信息集合
     *
     * @param post 岗位信息
     * @return 岗位信息集合
     */
    @Override
    public IPage<SysPost> selectPostList(SysPost post) {
        QueryWrapper<SysPost> qw = new QueryWrapper<>();
        if (StrUtil.isNotBlank(post.getPostCode())) {
            qw.like("post_code", post.getPostCode());
        }
        if (ObjectUtil.isNotNull(post.getStatus())) {
            qw.eq("status", post.getStatus());
        }
        if (StrUtil.isNotBlank(post.getPostName())) {
            qw.like("post_name", post.getPostName());
        }
        IPage<SysPost> page = null;
        if (post.getPageNum() > 0 && post.getPageSize() > 0) {
            page = new Page<>(post.getPageNum(), post.getPageSize());
        } else {
            page = new Page<>(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT);
        }
        return baseMapper.selectPage(page, qw);
    }

    /**
     * 根据用户ID获取岗位选择框列表
     *
     * @param userId 用户ID
     * @return 选中岗位ID列表
     */
    public List<Integer> selectPostListByUserId(Long userId) {
        return baseMapper.selectPostListByUserId(userId);
    }

    /**
     * 校验岗位名称是否唯一
     *
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public String checkPostNameUnique(SysPost post) {
        Long postId = ObjectUtil.isNull(post.getPostId()) ? -1L : post.getPostId();
        QueryWrapper<SysPost> qw = new QueryWrapper<>();
        qw.eq("post_name", post.getPostName());
        SysPost info = baseMapper.selectOne(qw);
        if (StringUtils.isNotNull(info) && info.getPostId().longValue() != postId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验岗位编码是否唯一
     *
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public String checkPostCodeUnique(SysPost post) {
        Long postId = ObjectUtil.isNull(post.getPostId()) ? -1L : post.getPostId();
        QueryWrapper<SysPost> qw = new QueryWrapper<>();
        qw.eq("post_code", post.getPostCode());
        SysPost info = baseMapper.selectOne(qw);
        if (StringUtils.isNotNull(info) && info.getPostId().longValue() != postId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 通过岗位ID查询岗位使用数量
     *
     * @param postId 岗位ID
     * @return 结果
     */
    @Override
    public int countUserPostById(Long postId) {
        //TODO 修改
        /*QueryWrapper<SysUserPost> qw = new QueryWrapper<>();
        qw.eq("post_id", postId);
        return userPostService.count(qw);*/
        return 0;
    }
    /**
     * 批量删除岗位信息
     *
     * @param postIds 需要删除的岗位ID
     * @return 结果
     * @throws Exception 异常
     */
    public int deletePostByIds(Long[] postIds) {
        for (Long postId : postIds) {
            SysPost post = baseMapper.selectById(postId);
            if (countUserPostById(postId) > 0) {
                throw new CustomException(String.format("%1$s已分配,不能删除", post.getPostName()));
            }
        }
        return baseMapper.deleteBatchIds(CollUtil.toList(postIds));
    }

    /**
     * 查询用户所属岗位组
     *
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public List<SysPost> selectPostsByUserName(String userName) {
        return baseMapper.selectPostsByUserName(userName);
    }
}
