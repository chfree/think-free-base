package com.cditer.free.data.test.viewmodel;

import com.cditer.free.data.test.model.TestDataUser;
import lombok.Data;

@Data
public class TestDataUserView extends TestDataUser {
    private String testName;
}
