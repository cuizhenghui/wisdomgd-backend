package com.think.business.docx;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class RowVo {

    private List<CellVo> cells;
}
