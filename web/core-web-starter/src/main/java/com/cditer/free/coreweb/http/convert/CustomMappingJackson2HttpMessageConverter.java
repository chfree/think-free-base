package com.cditer.free.coreweb.http.convert;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2021-09-09 12:11
 * @comment
 */

public class CustomMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
    public CustomMappingJackson2HttpMessageConverter(){
        super();
        List<MediaType> mediaTypeList = new ArrayList<>();
        mediaTypeList.add(MediaType.TEXT_HTML);
        mediaTypeList.add(MediaType.TEXT_PLAIN);
        mediaTypeList.add(MediaType.APPLICATION_JSON);
        mediaTypeList.add(MediaType.APPLICATION_FORM_URLENCODED);

        setSupportedMediaTypes(mediaTypeList);
    }
}
