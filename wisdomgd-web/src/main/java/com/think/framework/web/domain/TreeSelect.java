package com.think.framework.web.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.think.datasource.system.entity.SysDept;
import com.think.datasource.system.entity.SysMenu;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Treeselect树结构实体类
 *
 * @author Gary
 */
@Data
@Accessors(chain = true)
public class TreeSelect implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 节点ID
     */
    private Long id;

    /**
     * 节点名称
     */
    private String label;
    /**
     * 虚拟节点标记 (1-虚拟节点 0-实际业务节点)
     */
    private Integer virtualFlag = 0;
    /**
     * 返回前端使用 (true-虚拟节点 false-实际业务节点)
     */
    private Boolean virtualShow = false;

    /**
     * 子节点
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelect> children;

    public TreeSelect() {

    }

    public TreeSelect(SysDept dept) {
        this.id = dept.getDeptId();
        this.label = dept.getDeptName();
        this.virtualFlag = dept.getVirtualFlag();
        this.virtualShow = (this.virtualFlag == 1 ? true : false);
        this.children = dept.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    public TreeSelect(SysMenu menu) {
        this.id = menu.getMenuId();
        this.label = menu.getMenuName();
        this.children = menu.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }
}
