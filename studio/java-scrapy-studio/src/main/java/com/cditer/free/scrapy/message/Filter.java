package com.cditer.free.scrapy.message;

import lombok.Data;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2021-11-04 12:06
 * @comment
 */

@Data
public class Filter {
    /**
     * 别名
     * fmdate:格式化时间，转换为date,args[0]为时间格式
     * trim:去空格
     * replace:替换文本,args[0],源字符，args[1]目标字符，不填则为空
     * split:分隔，args[0]分隔符，默认逗号
     */
    private FilterType alias;

    /**
     * 参数，多个以逗号分隔
     */
    private String args;

    public List<String> getArgList(){
        if(!StringUtils.hasText(args)){
            return null;
        }
        return Arrays.asList(args.split(","));
    }

    public String getArgOne(){
        List<String> argList = getArgList();
        if(CollectionUtils.isEmpty(argList)){
            return null;
        }
        return argList.get(0);
    }

    public String getArgTwo(){
        List<String> argList = getArgList();
        if(CollectionUtils.isEmpty(argList)||argList.size()<=1){
            return null;
        }
        return argList.get(1);
    }

    public String getArgThreee(){
        List<String> argList = getArgList();
        if(CollectionUtils.isEmpty(argList)||argList.size()<=2){
            return null;
        }
        return argList.get(2);
    }

    public Filter(){}

    public Filter(FilterType alias){
        this.alias = alias;
    }

    public Filter(FilterType alias,String args){
        this.alias = alias;
        this.args = args;
    }
}
