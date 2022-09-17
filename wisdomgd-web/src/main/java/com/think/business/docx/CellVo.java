package com.think.business.docx;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CellVo {

    /**
     * 单元格内容
     */
    private String text;
    /**
     * 宽度
     */
    private String width;
    /**
     * 水平位置
     */
    private String align;
    /**
     * 是否纵向合并单元格
     */
    private boolean vMerge = false;
    /**
     * 是否纵向合并单元格的第一行
     */
    private boolean startMerge = false;
    /**
     * 背景颜色
     */
    private String color;
    /**
     * 横向合并
     */
    private String gridSpan;
}
