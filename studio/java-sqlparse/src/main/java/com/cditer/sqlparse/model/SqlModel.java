package com.cditer.sqlparse.model;

import lombok.Data;

/**
 * @author C.H
 * @email chfree365@qq.com
 * @createtime 2022/10/14 12:42
 * @comment
 */

@Data
public class SqlModel {
    private String sqlModel;

    private String tableName;

    private String sqlText;

    private String version;

    private String type; // syy/jt/hw


}
