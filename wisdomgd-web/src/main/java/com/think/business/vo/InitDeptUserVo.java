package com.think.business.vo;

import lombok.Data;

import java.util.List;

@Data
public class InitDeptUserVo {
    private String deptName;
    private Integer orderNum;
    private String userName;
    private Integer virtualFlag;
    private String role;
    private List<InitDeptUserVo> children;
}
