package com.cditer.free.core.util;

import com.cditer.free.core.message.data.PagerModel;
import com.cditer.free.core.message.web.BasePagerReq;
import com.cditer.free.core.message.web.BasePagerResp;
import com.cditer.free.core.message.web.BaseResponse;
import com.cditer.free.core.message.web.ResponseStatus;
import com.cditer.free.core.util.model.TestUser;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ReqRespTest {
    @Test
    public void test01(){
        BasePagerReq<TestUser> req = new BasePagerReq<>();
        PagerModel pager = req.getPager();
        TestUser search = req.getSearch();

        BasePagerResp<TestUser> resp = new BasePagerResp<>();
        resp.setTotalCount(10);
        resp.setData(new TestUser());

        Assert.assertTrue(resp.getTotalCount()==10);
        Assert.assertNotNull(resp.getData());
    }

    @Test
    public void test02(){
        BasePagerResp<List<TestUser>> resp = new BasePagerResp<>();
        resp.setTotalCount(10);
        resp.setData(Arrays.asList(new TestUser()));

        Assert.assertTrue(resp.getTotalCount()==10);
        Assert.assertTrue(resp.getData().size()==1);
    }

    @Test
    public void test03(){
        BasePagerResp<List<TestUser>> resp = BasePagerResp.success(Arrays.asList(new TestUser()), 10);

        Assert.assertTrue(resp.getTotalCount()==10);
        Assert.assertTrue(resp.getData().size()==1);
    }

    @Test
    public void test04(){
        BaseResponse resp = BaseResponse.success();
        BaseResponse<Boolean> resp1 = BaseResponse.success(true);
        BaseResponse<String> resp2 = BaseResponse.success("haha");

        Assert.assertTrue(resp.getStatus()== ResponseStatus.SUCCESS);
        Assert.assertTrue(resp1.getData());
        Assert.assertEquals(resp2.getData(),"haha");
    }
}
