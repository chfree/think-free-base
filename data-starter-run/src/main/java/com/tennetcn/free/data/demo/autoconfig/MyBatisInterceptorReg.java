package com.tennetcn.free.data.demo.autoconfig;

import com.tennetcn.free.data.boot.autoconfig.mybatis.MapperAutoConfiguration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-10-28 10:30
 * @comment
 */

//@Configuration
//@AutoConfigureAfter(MapperAutoConfiguration.class)
public class MyBatisInterceptorReg {
    @Autowired
    private List<SqlSessionFactory> sqlSessionFactoryList;

    @PostConstruct
    public void addPageInterceptor() {
        GlobalParamInterceptor globalParamInterceptor = new GlobalParamInterceptor();

        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            sqlSessionFactory.getConfiguration().addInterceptor(globalParamInterceptor);
        }
    }
}
