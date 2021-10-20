package com.cditer.free.data.test.model;

import com.cditer.free.core.message.data.ModelBase;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "test_authority_dept")
public class TestDataDept extends ModelBase {
    @Id
    @Column(name="id")
    private String id;
}
