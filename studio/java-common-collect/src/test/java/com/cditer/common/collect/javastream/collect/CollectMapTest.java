package com.cditer.common.collect.javastream.collect;

import com.cditer.common.collect.model.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2021-11-05 12:13
 * @comment
 */

public class CollectMapTest {

    @Test
    public void testToMap(){
        List<User> users = new ArrayList<>();
        User u1 = new User();
        u1.setId("123");
        u1.setName("CH123");
        users.add(u1);

        User u2 = new User();
        u2.setId("456");
        u2.setName("CH456");
        users.add(u2);

        Map<String, User> maps = users.stream().collect(Collectors.toMap(item -> item.getId(), item -> item, (v1, v2) -> v1));

        Assert.assertEquals("123,456", String.join(",",maps.keySet()));
    }

    @Test
    public void testToMap1(){
        List<User> users = new ArrayList<>();
        User u1 = new User();
        u1.setId("123");
        u1.setName("CH123");
        users.add(u1);

        User u2 = new User();
        u2.setId("123");
        u2.setName("CH456");
        users.add(u2);

        Map<String, User> maps = users.stream().collect(Collectors.toMap(item -> item.getId(), item -> item, (v1, v2) -> v1));

        Assert.assertEquals("123", String.join(",",maps.keySet()));
        Assert.assertEquals(maps.get("123").getName(), "CH123");

        maps = users.stream().collect(Collectors.toMap(item -> item.getId(), item -> item, (v1, v2) -> v2));
        Assert.assertEquals("123", String.join(",",maps.keySet()));
        Assert.assertEquals(maps.get("123").getName(), "CH456");
    }
}
