package com.think.business.docx;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class DocxVo {
    /**
     * 标题
     */
    private String title;
    /**
     * 子内容
     */
    private String subTitle;
    /**
     * 宽度
     */
    private String witdh;
    /**
     * 表格行列表
     */
    private List<RowVo> rowList;
}
