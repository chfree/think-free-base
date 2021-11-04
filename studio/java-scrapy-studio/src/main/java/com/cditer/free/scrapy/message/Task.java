package com.cditer.free.scrapy.message;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2021-11-03 12:22
 * @comment
 */

@Data
public class Task {
    private Start start;

    private End end;

    private List<Step> steps = new ArrayList<>();

    public void addStep(Step step){
        steps.add(step);
    }
}
