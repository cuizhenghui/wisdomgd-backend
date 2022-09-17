package com.think.business.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FileVo {
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件url
     */
    private String fileUrl;
    /**
     * 系统内名称
     */
    private String resourcePath;
}
