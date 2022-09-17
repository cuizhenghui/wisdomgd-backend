package com.think.datasource.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.think.framework.aspectj.lang.annotation.Excel;
import com.think.framework.aspectj.lang.annotation.Excel.ColumnType;
import com.think.framework.aspectj.lang.annotation.Excels;
import com.think.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
@ApiModel(value = "用户信息",description = "用户POJO")
public class SysUser extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Excel(name = "用户序号", cellType = ColumnType.NUMERIC, prompt = "用户编号")
    @TableId(value = "user_id", type = IdType.AUTO)
    @ApiModelProperty(value = "用户id",dataType = "long")
    private Long userId;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 岗位ID
     */
    private Long postId;

    /**
     * 用户账号
     */
    @Excel(name = "登录名称")
    @NotBlank(message = "用户账号不能为空")
    @Size(min = 0, max = 30, message = "用户账号长度不能超过30个字符")
    private String userName;

    /**
     * 密码(密文)
     */
    private String password;

    /**
     * 微信openid
     */
    private String wxOpenId;

    /**
     * 用户昵称（微信昵称）
     */
    @Excel(name = "用户名称")
    @Size(min = 0, max = 30, message = "用户昵称长度不能超过30个字符")
    private String nickName;

    /**
     * 用户类型（SYSTEM-系统用户；WXMINI-微信小程序）
     */
    private String userType;

    /**
     * 用户邮箱
     */
    @Excel(name = "用户邮箱")
    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
    private String email;

    /**
     * 手机号码
     */
    @Excel(name = "手机号码")
    @Size(min = 0, max = 11, message = "手机号码长度不能超过11个字符")
    private String phone;

    /**
     * 用户性别（male-男；female-女； unkown-未知）
     */
    @Excel(name = "用户性别", readConverterExp = "male=男,female=女,unkown=未知")
    private String sex;

    /**
     * 用户头像
     */
    private String avatar;


    /**
     * 盐加密
     */
    @TableField(exist = false)
    private String salt;

    /**
     * 帐号状态（0-正常；1-停用）
     */
    private Integer status;

    /**
     * 是否删除（0-否；1-是）
     */
    private Integer delFlag;

    /**
     * 最后登陆IP
     */
    @Excel(name = "最后登陆IP")
    private String loginIp;

    /**
     * 最后登陆时间
     */
    @Excel(name = "最后登陆时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date loginDate;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;
    /**
     * 部门对象
     */
    @TableField(exist = false)
    @Excels({@Excel(name = "部门名称", targetAttr = "deptName"), @Excel(name = "部门负责人", targetAttr = "leader")})
    private SysDept dept;

    /**
     * 角色对象
     */
    @TableField(exist = false)
    private List<SysRole> roles;

    /**
     * 角色组
     */
    @TableField(exist = false)
    private Long[] roleIds;

    /**
     * 页面uuid
     */
    @TableField(exist = false)
    private String uuid;

    /**
     * 图形验证码
     */
    @TableField(exist = false)
    private String captcha;

    /**
     * 岗位组
     */
    @TableField(exist = false)
    private Long[] postIds;

    public SysUser(Long userId) {
        this.userId = userId;
    }

    public boolean isAdmin() {
        return isAdmin(this.userId);
    }

    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }
}
