package com.cditer.free.data.test.model;

import com.cditer.free.core.message.data.ModelBase;
import lombok.Data;
import tk.mybatis.mapper.annotation.ColumnType;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
public class TestNoDbData extends ModelBase {

    @Column(name="id")
    private String id;

    @ColumnType(column = "name")
    private String name;

    private String age;
}
