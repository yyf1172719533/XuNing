package com.timain.common.core.domain;

import java.io.Serializable;

/**
 * Ztree树结构实体类
 * @author yyf
 * @version 1.0
 * @date 2020/8/13 14:27
 */
public class Ztree implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /** 节点ID */
    private Long id;
    
    /** 节点父ID */
    private Long pId;
    
    /** 节点名称 */
    private String name;
    
    /** 节点标题 */
    private String title;
    
    /** 是否勾选 */
    private boolean checked = false;
    
    /** 是否展开 */
    private boolean open = false;
    
    /** 是否能勾选 */
    private boolean noCheck = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isNoCheck() {
        return noCheck;
    }

    public void setNoCheck(boolean noCheck) {
        this.noCheck = noCheck;
    }
}
