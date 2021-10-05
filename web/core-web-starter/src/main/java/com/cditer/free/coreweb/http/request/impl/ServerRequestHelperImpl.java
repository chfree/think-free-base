package com.cditer.free.coreweb.http.request.impl;

import com.cditer.free.core.util.SpringContextUtils;
import com.cditer.free.coreweb.http.entity.RequestEntityEx;
import com.cditer.free.coreweb.http.entity.ResponseEntityEx;
import com.cditer.free.coreweb.http.handle.IResponseEntityResolveInceptor;
import com.cditer.free.coreweb.http.request.IServerRequestHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2021-09-08 13:05
 * @comment
 */

@Slf4j
@Component
public class ServerRequestHelperImpl implements IServerRequestHelper {

    @Autowired
    @Qualifier("restTemplate")
    RestTemplate restTemplate;


    @Override
    public RestTemplate getRestTemplate() {
        try {
            Object eurekaRestTemplate = SpringContextUtils.getCurrentContext().getBean("eurekaRestTemplate");

            if (eurekaRestTemplate == null) {
                return restTemplate;
            }
            return (RestTemplate) eurekaRestTemplate;
        }catch (NoSuchBeanDefinitionException exception){
            return restTemplate;
        }
    }

    @Override
    public <T> ResponseEntityEx<T> request(RequestEntityEx requestEntity, Class<T> responseType) {
        ResponseEntity<T> exchange = getRestTemplate().exchange(requestEntity, responseType);

        return resolveResponseEntity(exchange);
    }

    @Override
    public <T> ResponseEntityEx<T> postForEntity(RequestEntityEx requestEntity, Class<T> responseType) {
        ResponseEntity<T> responseEntity = getRestTemplate().postForEntity(requestEntity.getUrl(), requestEntity, responseType);

        return resolveResponseEntity(responseEntity);
    }

    public <T> ResponseEntityEx<T> getForEntity(RequestEntityEx requestEntity, Class<T> responseType) {
        ResponseEntity<T> getForEntity = getRestTemplate().getForEntity(requestEntity.getUrlStr(), responseType, requestEntity.getParam());

        return resolveResponseEntity(getForEntity);
    }

    public <T> ResponseEntityEx<T> resolveResponseEntity(ResponseEntity<T> responseEntity){
        ResponseEntityEx<T> exchangeEx = new ResponseEntityEx<T>(responseEntity.getBody(), responseEntity.getHeaders(), responseEntity.getStatusCode());

        // 自定义解析 inceptor
        doResponseEntityResolveInceptor(exchangeEx);

        return exchangeEx;
    }


    private <T> void doResponseEntityResolveInceptor(ResponseEntityEx<T> exchangeEx){
        Map<String, IResponseEntityResolveInceptor> resolveInceptorMap = SpringContextUtils.getCurrentContext().getBeansOfType(IResponseEntityResolveInceptor.class);

        if(resolveInceptorMap==null|| CollectionUtils.isEmpty(resolveInceptorMap.keySet())){
            return;
        }

        List<IResponseEntityResolveInceptor> resolveList = resolveInceptorMap.values().stream().sorted(Comparator.comparing(IResponseEntityResolveInceptor::getOrder)).collect(Collectors.toList());

        for (IResponseEntityResolveInceptor responseEntityResolveInceptor : resolveList) {
            responseEntityResolveInceptor.resovle(exchangeEx);
        }
    }
}
