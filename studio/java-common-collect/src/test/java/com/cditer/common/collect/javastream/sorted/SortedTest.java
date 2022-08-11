package com.cditer.common.collect.javastream.sorted;

import com.cditer.common.collect.model.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
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
    public void sorted() {
        List<Integer> list = new ArrayList<Integer>();
        list.add(8);
        list.add(19);
        list.add(2);
        list.add(-8);

        List<Integer> sortedList = list.stream().sorted().collect(Collectors.toList());

        for (int i = 0; i < sortedList.size(); i++) {
            if (i < sortedList.size() - 1) {
                Assert.assertTrue(sortedList.get(i) < sortedList.get(i + 1));
            }
        }

        List<String> sortedStrList = sortedList.stream().map(item -> String.valueOf(item)).collect(Collectors.toList());

        Assert.assertEquals(String.join(",", sortedStrList), "-8,2,8,19");

    }

    @Test
    public void sortedDesc() {
        List<Integer> list = new ArrayList<Integer>();
        list.add(8);
        list.add(19);
        list.add(2);
        list.add(-8);

        List<Integer> sortedList = list.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());

        for (int i = 0; i < sortedList.size(); i++) {
            if (i < sortedList.size() - 1) {
                Assert.assertTrue(sortedList.get(i) > sortedList.get(i + 1));
            }
        }

        List<String> sortedStrList = sortedList.stream().map(item -> String.valueOf(item)).collect(Collectors.toList());

        Assert.assertEquals(String.join(",", sortedStrList), "19,8,2,-8");

    }

    @Test
    public void testModelSorted() {
        List<User> users = new ArrayList<>();
        users.add(new User("cheng", 18));
        users.add(new User("chfree", 16));
        users.add(new User("ch", 20));

        List<User> sortedUsers = users.stream().sorted(Comparator.comparing(item -> item.getAge())).collect(Collectors.toList());

        List<String> ages = sortedUsers.stream().map(item -> String.valueOf(item.getAge())).collect(Collectors.toList());

        Assert.assertEquals("16,18,20", String.join(",", ages));
    }

    @Test
    public void testModelSortedDesc() {
        List<User> users = new ArrayList<>();
        users.add(new User("cheng", 18));
        users.add(new User("chfree", 16));
        users.add(new User("ch", 20));

        List<User> sortedUsers = users.stream().sorted(Comparator.comparing(item -> item.getAge(), Comparator.reverseOrder())).collect(Collectors.toList());

        List<String> ages = sortedUsers.stream().map(item -> String.valueOf(item.getAge())).collect(Collectors.toList());

        Assert.assertEquals("20,18,16", String.join(",", ages));
    }
}
