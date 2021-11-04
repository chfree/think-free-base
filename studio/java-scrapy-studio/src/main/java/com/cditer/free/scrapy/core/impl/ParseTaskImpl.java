package com.cditer.free.scrapy.core.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.cditer.free.scrapy.core.IParseTask;
import com.cditer.free.scrapy.message.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
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
    public List<ArticleInfo> parsePageTask(Task task) {
        Document dom = getDom(task.getStart().getUrl());

        List<Step> steps = task.getSteps();
        List<Step> sortedSteps = steps.stream().sorted(Comparator.comparing(item -> item.getOrder())).collect(Collectors.toList());

        Step step = sortedSteps.get(0);

        List<ArticleInfo> allList = new ArrayList<>();

        if(step.getPageType()== PageType.detailed_list){
            List<ArticleInfo> articleInfos = resolveDetailedList(dom, sortedSteps, step);
            if(!CollectionUtils.isEmpty(articleInfos)){
                allList.addAll(articleInfos);
            }
        }else if(step.getPageType() == PageType.category_list){

        }
        return allList;
    }

    public List<ArticleInfo> resolveDetailedList(Document dom, List<Step> sortedSteps, Step step) {
        List<ArticleInfo> articleInfos = new ArrayList<>();

        Elements elements = dom.selectXpath(step.getListMatch());
        for (Element element : elements) {
            ArticleInfo articleInfo = new ArticleInfo();
            articleInfos.add(articleInfo);

            pageFillVal(element, articleInfo, step);

            Step step1 = sortedSteps.get(1);
            if(step1.getPageType()==PageType.detailed){
                String openUrl = getOpenUrl(element, step.getOpenDetailedMatch());
                doDetailedResolve(openUrl, step1, articleInfo);
            }
        }

        return articleInfos;
    }

    public void doDetailedResolve(String openUrl, Step stepDetailed, ArticleInfo articleInfo){
        Document document = getDom(openUrl);
        pageFillVal(document, articleInfo, stepDetailed);
    }

    public String getOpenUrl(Element element, CaptureRule captureRule){
        Elements openDetaileds = element.selectXpath(captureRule.getMatchRule());

        Element defaultDetailed = openDetaileds.get(0);
        if(StringUtils.hasText(captureRule.getAttribute())){
            return defaultDetailed.attr(captureRule.getAttribute());
        }
        return defaultDetailed.text();
    }

    public void pageFillVal(Element element, ArticleInfo articleInfo, Step step){
        List<CaptureRule> captureRules = step.getCaptureRules();
        if(CollectionUtils.isEmpty(captureRules)){
            return;
        }
        for (CaptureRule captureRule : captureRules) {
            Elements elements = element.selectXpath(captureRule.getMatchRule());
            if(CollectionUtils.isEmpty(elements)){
                continue;
            }

            List<String> items = elements.stream().map(item -> {
                if (StringUtils.hasText(captureRule.getAttribute())) {
                    return item.attr(captureRule.getAttribute());
                }
                return item.text();
            }).collect(Collectors.toList());

            doFilter(captureRule, articleInfo, items);
        }
    }

    public void doFilter(CaptureRule captureRule,ArticleInfo articleInfo,List<String> items){
        if(CollectionUtils.isEmpty(items)){
            return;
        }
        String value = String.join(",", items);
        List<Filter> filters = captureRule.getFilters();
        if(CollectionUtils.isEmpty(filters)){
            BeanUtil.setFieldValue(articleInfo, captureRule.getProperty(), value);
            return;
        }

        Object objVal = value;
        for (Filter filter : filters) {
            if(FilterType.trim==filter.getAlias()){
                objVal = objVal.toString().trim();
            }
            if(FilterType.replace == filter.getAlias()){
                if(!StringUtils.hasText(filter.getArgOne())){
                    continue;
                }
                String argTwo = StringUtils.hasText(filter.getArgTwo())?filter.getArgTwo(): "";
                objVal = objVal.toString().replace(filter.getArgOne(), argTwo);
            }
            if(FilterType.fmdate==filter.getAlias()){
                if(!StringUtils.hasText(filter.getArgOne())){
                    objVal = DateUtil.parse(objVal.toString());
                }else{
                    objVal = DateUtil.parse(objVal.toString(), filter.getArgOne());
                }
            }
            if(FilterType.split==filter.getAlias()){
                String argOne = StringUtils.hasText(filter.getArgOne())?filter.getArgTwo(): ",";

                objVal = Arrays.stream(objVal.toString().split(argOne)).filter(item -> StringUtils.hasText(item)).collect(Collectors.toList());

            }
        }
        BeanUtil.setFieldValue(articleInfo, captureRule.getProperty(), objVal);
    }

    public Document getDom(String url){
        try {
            getTrust();
            return Jsoup
                    .connect(url)
                    .header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.54 Safari/537.36 Edg/95.0.1020.40")
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取服务器信任
     */
    private void getTrust() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[] { new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            } }, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
