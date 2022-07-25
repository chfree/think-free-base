package com.cditer.common.collect.javastream.distinct;

import com.cditer.common.collect.model.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author C.H
 * @email chfree365@qq.com
 * @createtime 2022/7/25 22:46
 * @comment
 */

public class DistinctTest {

    @Test
    public void testDis01() {
        List<String> arrs = Arrays.asList("a", "b", "c", "d", "b");

        List<String> disArr = arrs.stream().distinct().collect(Collectors.toList());

        Assert.assertEquals(String.join(",", disArr), "a,b,c,d");
    }

    @Test
    public void testDis02() {
        List<User> userList = new ArrayList<>();
        userList.add(new User("a", 18));
        userList.add(new User("b", 19));
        userList.add(new User("c", 20));
        userList.add(new User("d", 21));
        userList.add(new User("a", 18));

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        List<User> disUserList = userList.stream().filter(item -> {
            return seen.putIfAbsent(item.getName(), Boolean.TRUE) == null;
        }).collect(Collectors.toList());
        Assert.assertTrue(disUserList.size() == 4);

        List<String> nameList = disUserList.stream().map(item -> item.getName()).collect(Collectors.toList());
        Assert.assertEquals(String.join(",", nameList), "a,b,c,d");
    }


    @Test
    public void testDis03() {
        List<User> userList = new ArrayList<>();
        userList.add(new User("a", 18));
        userList.add(new User("b", 19));
        userList.add(new User("c", 20));
        userList.add(new User("d", 21));
        userList.add(new User("a", 18));

        List<User> disUserList = userList.stream().filter(distinctByKey(item -> item.getName())).collect(Collectors.toList());
        Assert.assertTrue(disUserList.size() == 4);

        List<String> nameList = disUserList.stream().map(item -> item.getName()).collect(Collectors.toList());
        Assert.assertEquals(String.join(",", nameList), "a,b,c,d");
    }

    static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
