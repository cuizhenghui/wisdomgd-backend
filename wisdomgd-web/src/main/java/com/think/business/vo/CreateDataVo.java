package com.think.business.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author Gary
 **/
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateDataVo {
    private String title;
    @NotNull
    private Integer dataSource;
    @NotNull
    private Integer dataType;
    private String dataSourceName;
    @NotEmpty
    private String content;
    private String url;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date publishTime = new Date();
    /**
     * 舆情账户ID
     */
    private Long userId;
    /**
     * 舆情账户的来源
     */
    private Integer siteCls;

    private String addContent;

    private Long adminDept;
    private Integer dataFrom;
    private Long createDeptId;
    private Long createUserId;
    private boolean isEnterProcess;
}
