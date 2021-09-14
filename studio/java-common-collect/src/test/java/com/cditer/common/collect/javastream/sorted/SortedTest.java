package com.cditer.common.collect.javastream.sorted;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2021-09-14 12:55
 * @comment
 */

public class SortedTest {

    @Test
    public void sorted(){
        List<Integer> list = new ArrayList<Integer>();
        list.add(8);
        list.add(19);
        list.add(2);
        list.add(-8);

        List<Integer> sortedList = list.stream().sorted().collect(Collectors.toList());

        for (int i = 0; i < sortedList.size(); i++) {
            if(i<sortedList.size()-1) {
                Assert.assertTrue(sortedList.get(i) < sortedList.get(i + 1));
            }
        }

        List<String> sortedStrList = sortedList.stream().map(item -> String.valueOf(item)).collect(Collectors.toList());

        Assert.assertEquals(String.join(",", sortedStrList),"-8,2,8,19");

    }
}
