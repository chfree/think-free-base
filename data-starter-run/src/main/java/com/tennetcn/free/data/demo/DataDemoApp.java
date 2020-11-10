package com.tennetcn.free.data.demo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.tennetcn.free.core.cache.ICached;
import com.tennetcn.free.core.util.PkIdUtils;
import com.tennetcn.free.core.util.SnowFlakeIdUtils;
import com.tennetcn.free.data.demo.logical.mapper.LoginUserMapper;
import com.tennetcn.free.data.demo.logical.model.LoginUser;
import com.tennetcn.free.data.demo.logical.service.ILoginUserService;
import com.tennetcn.free.data.demo.logical.viewmodel.TestUser;
import com.tennetcn.free.data.demo.logical.viewmodel.TestUser1;
import com.tennetcn.free.data.utils.ClassAnnotationUtils;
import javafx.concurrent.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;
import tk.mybatis.spring.annotation.MapperScan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class DataDemoApp implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DataDemoApp.class, args);
    }

    @Autowired
    private ILoginUserService loginUserService;

    @Autowired
    LoginUserMapper loginUserMapper;

    @Autowired
    ICached cached;

    private void testSeq(){

        RunTest rt1 = new RunTest(loginUserService);

        rt1.start();
    }

    private void runTestSeq(){
        System.out.println(DateUtil.currentSeconds());
        for(int i = 100; i > 0; i--) {
            testSeq();
        }
        try {
            System.out.println(DateUtil.currentSeconds());
            Thread.sleep(2000);
            System.out.println(DateUtil.currentSeconds());
            System.out.println(RunTest.testMap.keySet().size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run(String... args) throws Exception {
//        String ids = loginUserService.queryAllParentDeptById("4291cb5a-baba-11e9-ba9d-bfe43b78a9e4");
//        System.out.println(ids);
//        LoginUser loginUser = new LoginUser();
//        loginUser.setName("test");
//        int count = loginUserMapper.testGlobal(loginUser);
//        System.out.println(count);

//        String primaryKey = ClassAnnotationUtils.getFirstColumnKey(LoginUser.class);
//        System.out.println(primaryKey);

//        System.out.println("is run success!!!");
//
//        List<LoginUser> loginUsers = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            LoginUser user = new LoginUser();
//            user.setId(PkIdUtils.getId());
//            user.setAccount("batchInsert" + i);
//            user.setName("batch insert" + i);
//            user.setPassword("000000");
//
//            loginUsers.add(user);
//        }
//
//        loginUserService.batchInsert(loginUsers);

//        System.out.println(PkIdUtils.getId());
//        for (int i = 0; i < 10; i++) {
//            //10进制
//            System.out.println(SnowFlakeIdUtils.nextId());
//        }
//
//        int count = loginUserService.queryCount();
//
//        System.out.println("count:" + count);
//
//
//
//        List<LoginUser> loginUsers = loginUserService.queryList();
//
//        System.out.println("query list");
//        loginUsers.forEach(user->{
//            System.out.println(user.getId()+":"+user.getName());
//        });
//
//        LoginUser loginUser = loginUserService.queryModel("123");
//        System.out.println("loginuser model");
//        System.out.println("loginUser:"+loginUser.getName());
//
//        System.out.println("query list mp by ids");
//        List<LoginUser> usersByIds = loginUserService.queryListMPByIds(Arrays.asList("123","1234"));
//        usersByIds.forEach(user->{
//            System.out.println(user.getId()+":"+user.getName());
//        });
//
//        System.out.println("query list test");
//
//        List<LoginUser> testList = loginUserService.queryTest();
//        testList.forEach(test->{
//            System.out.println("markCode:"+test.getMarkCode());
//        });
//
//        System.out.println("query list test user");
//
//        List<TestUser> testUsers = loginUserService.queryTestUsers();
//        System.out.println(JSONUtil.toJsonStr(testUsers));
//        testUsers.forEach(test->{
//            if(!StringUtils.isEmpty(test.getMarkCode())){
//                System.out.println("markCode:"+test.getMarkCode());
//                System.out.println("test:"+test.getTest());
//            }
//
//        });
//
//        System.out.println("query list test usersx");
//
//        List<TestUser> testUsersx = loginUserService.queryTestUserxs();
//        System.out.println(JSONUtil.toJsonStr(testUsersx));
//        testUsersx.forEach(test->{
//            if(!StringUtils.isEmpty(test.getMarkCode())){
//                System.out.println("markCode:"+test.getMarkCode());
//                System.out.println("test:"+test.getTest());
//                System.out.println("testName:"+test.getTestName());
//            }
//
//        });
//
//        System.out.println("query list test user1");
//        List<TestUser1> testUsers1 = loginUserService.queryTestUsers1();
//        System.out.println(JSONUtil.toJsonStr(testUsers1));
//        testUsers1.forEach(test->{
//            if(!StringUtils.isEmpty(test.getMarkCode())){
//                System.out.println("markCode:"+test.getMarkCode());
//                System.out.println("test:"+test.getTest());
//            }
//
//        });
//
//        System.out.println("query list test user");
//
//        List<TestUser> testUsers_new = loginUserService.queryTestUsers();
//        System.out.println(JSONUtil.toJsonStr(testUsers_new));
//        testUsers_new.forEach(test->{
//            if(!StringUtils.isEmpty(test.getMarkCode())){
//                System.out.println("markCode:"+test.getMarkCode());
//                System.out.println("test:"+test.getTest());
//            }
//
//        });

    }
}
