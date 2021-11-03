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
            Elements elements = dom.selectXpath(step.getListMatch());
            for (Element element : elements) {
                PageInfo pageInfo = new PageInfo();
                pageInfos.add(pageInfo);

                pageFillVal(element, pageInfo, step);

                Step step1 = sortedSteps.get(1);
                if(step1.getPageType()==PageType.detailed){

//                    String openUrl = getOpenUrl(element, step.getOpenDetailedMatch());
//                    Document document = null;
//                    try {
//                        document = Jsoup.connect(openUrl).get();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                    // pageFillVal(document, pageInfo, step1);
                }
            }
        }


        return pageInfos;
    }

    private String getOpenUrl(Element element, CaptureRule captureRule){
        Elements openDetaileds = element.selectXpath(captureRule.getMatchRule());

        Element defaultDetailed = openDetaileds.get(0);
        if(StringUtils.hasText(captureRule.getAttribute())){
            return defaultDetailed.attr(captureRule.getAttribute());
        }
        return defaultDetailed.text();
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
            BeanUtil.setFieldValue(pageInfo, captureRule.getProperty(),elements.get(0).text());
        }
    }

    private Document getDom(Task task){
        try {
            getTrust();
            return Jsoup
                    .connect(task.getStart().getUrl())
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
