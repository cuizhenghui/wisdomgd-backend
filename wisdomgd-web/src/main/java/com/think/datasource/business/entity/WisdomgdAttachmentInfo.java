package com.think.datasource.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.think.framework.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 附件表
 * </p>
 *
 * @author LF
 * @since 2022-09-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class WisdomgdAttachmentInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 附件ID
     */
    @TableId(value = "atta_id", type = IdType.AUTO)
    private Long attaId;

    /**
     * 附件简述
     */
    private String attaIntro;

    /**
     * 附件地址（绝对路径）
     */
    private String attaUrl;

    /**
     * 附件地址（相对路径）
     */
    private String attaAddress;

    /**
     * 附件类型（image-图片；video-视频；radio-音频；other-其他；）
     */
    private String attaType;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;


}
