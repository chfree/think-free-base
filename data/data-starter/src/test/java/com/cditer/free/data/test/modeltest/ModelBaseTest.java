package com.cditer.free.data.test.modeltest;

import cn.hutool.json.JSONUtil;
import com.cditer.free.data.test.model.TestDataUser;
import org.junit.Test;

public class ModelBaseTest {
    @Test
    public void test01(){
        TestDataUser testDataUser = new TestDataUser();

        testDataUser.autoPkIdAndStatus();

        System.out.println(JSONUtil.toJsonPrettyStr(testDataUser));
    }
}
