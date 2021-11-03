package com.cditer.free.scrapy;

import com.cditer.free.scrapy.core.IParseTask;
import com.cditer.free.scrapy.core.impl.ParseTaskImpl;
import com.cditer.free.scrapy.message.*;

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
        step.addRule(new CaptureRule("title", ".list .title"));
        step.addRule(new CaptureRule("authors", ".list .author"));
        task.addStep(step);

        Step stepDetailed = new Step();
        stepDetailed.setOrder(1);
        step.setPageType(PageType.detailed);

        task.addStep(stepDetailed);

        parseTask.parsePageTas(task);
    }
}
