package com.cditer.flux.demo.test;

import org.junit.Test;
import reactor.core.publisher.Flux;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2023-09-13 12:42
 * @comment
 */

public class FirstTest {

    @Test
    public void test01(){
        getData();
    }


    private void getData(){
        Flux<String> just = Flux.just("aa", "bb");

        System.out.println(just.blockFirst());
    }
}
