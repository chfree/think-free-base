package com.cditer.free.core.util;

import com.cditer.free.core.cache.ICached;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

public class SpringContextUtilsTest extends BaseTest{

    @Test
    public void setApplicationContext() {
    }

    @Test
    public void getCurrentContext() {
    }

    @Test
    public void setCurrentContext() {
    }

    @Test
    public void getBean() {
        ICached cached = SpringContextUtils.getBean(ICached.class);

        Assert.assertNotNull(cached);
    }
}