package com.turnsole.rbac.domain.beans;

import javax.validation.constraints.Min;

/**
 * @author:徐凯
 * @date:2019/8/7,9:30
 * @what I say:just look,do not be be
 */
public class PageQuery {
    @Min(value = 1, message = "当前页码不合法")
    private Integer pageNo = 1;
    @Min(value = 1, message = "每页展示数量不合法")
    private Integer pageSize = 10;

    private Integer offset;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getOffset() {
        return (pageNo - 1) * pageSize;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
