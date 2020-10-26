package com.tennetcn.free.data.demo;

import com.tennetcn.free.data.demo.logical.service.ILoginUserService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-10-26 23:19
 * @comment
 */

public class RunTest extends Thread{

    public static Map<String,String> testMap = new ConcurrentHashMap();

    ILoginUserService loginUserService;

    public RunTest(ILoginUserService loginUserService){
        this.loginUserService = loginUserService;
    }

    @Override
    public void run() {
        getSeq();
    }

    private void getSeq(){
        for(int i = 2; i > 0; i--) {
            int seq_approve_no = loginUserService.querySeq("seq_approve_no");
            try {
                Thread.sleep(10*i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String s = String.valueOf(seq_approve_no);
            testMap.put(s,s);
            System.out.println(this.getName()+":"+seq_approve_no);
        }
    }
}
