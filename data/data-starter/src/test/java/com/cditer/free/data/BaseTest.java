package com.cditer.free.data;

import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tk.mybatis.spring.annotation.MapperScan;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TestDataApp.class})
@MapperScan(value = {"com.cditer.free.data.test.mapper"})
public abstract class BaseTest {
}
