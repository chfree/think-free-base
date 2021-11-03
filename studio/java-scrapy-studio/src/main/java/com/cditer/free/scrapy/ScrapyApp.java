package com.cditer.free.scrapy;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.cditer.free.scrapy.core.IParseTask;
import com.cditer.free.scrapy.core.impl.ParseTaskImpl;
import com.cditer.free.scrapy.message.*;

import java.util.List;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2021-11-03 12:20
 * @comment
 */

public class ScrapyApp {

    public static void main(String[] args) {

        IParseTask parseTask = new ParseTaskImpl();

        Task task = new Task();

        Start start = new Start();
        start.setTitle("上海大学学报（自然科学版）");
        start.setUrl("https://www.journal.shu.edu.cn/CN/1007-2861/home.shtml");

        task.setStart(start);

        Step step = new Step();
        step.setOrder(0);
        step.setPageType(PageType.detailed_list);
        step.setListMatch("//div[@class='dqml_qbwz']");
        step.setOpenDetailedMatch(new CaptureRule(null,"//dd/a[@class='biaoti']", "href"));

        step.addRule(new CaptureRule("title", "//dd/a[@class='biaoti']"));
        step.addRule(new CaptureRule("authors", "//dd[@class='zuozhe']"));
        task.addStep(step);

        Step stepDetailed = new Step();
        stepDetailed.setOrder(1);
        stepDetailed.setPageType(PageType.detailed);

        task.addStep(stepDetailed);


        System.out.println(JSONUtil.toJsonStr(task));

        List<PageInfo> pageInfos = parseTask.parsePageTas(task);
        System.out.println(JSONUtil.toJsonStr(pageInfos));
    }
}
