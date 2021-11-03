package com.cditer.free.scrapy.core.impl;

import cn.hutool.core.bean.BeanUtil;
import com.cditer.free.scrapy.core.IParseTask;
import com.cditer.free.scrapy.message.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2021-11-03 12:26
 * @comment
 */

@Component
public class ParseTaskImpl implements IParseTask {



    @Override
    public List<PageInfo> parsePageTas(Task task) {
        Document dom = getDom(task);

        List<Step> steps = task.getSteps();
        List<Step> sortedSteps = steps.stream().sorted(Comparator.comparing(item -> item.getOrder())).collect(Collectors.toList());

        Step step = sortedSteps.get(0);
        List<PageInfo> pageInfos = new ArrayList<>();

        if(step.getPageType()== PageType.detailed_list){
            Elements elements = dom.selectXpath("");
            for (Element element : elements) {
                PageInfo pageInfo = new PageInfo();
                pageInfos.add(pageInfo);

                pageFillVal(element, pageInfo, step);

                Step step1 = sortedSteps.get(1);
                if(step1.getPageType()==PageType.detailed){
                    Elements elements1 = element.selectXpath(step.getListMatch());
                    for (Element element1 : elements1) {
                        Document document = null;
                        try {
                            document = Jsoup.connect(element1.data()).get();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        pageFillVal(document, pageInfo, step1);
                    }
                }
            }
        }


        return null;
    }

    private void pageFillVal(Element element, PageInfo pageInfo,Step step){
        List<CaptureRule> captureRules = step.getCaptureRules();
        if(CollectionUtils.isEmpty(captureRules)){
            return;
        }
        for (CaptureRule captureRule : captureRules) {
            Elements elements = element.selectXpath(captureRule.getMatchRule());
            if(CollectionUtils.isEmpty(elements)){
                continue;
            }
            BeanUtil.setFieldValue(pageInfo, captureRule.getProperty(),elements.get(0).data());
        }
    }

    private Document getDom(Task task){
        try {
            return Jsoup.connect(task.getStart().getUrl()).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
