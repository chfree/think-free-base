package com.cditer.common.collect.generic;

import com.cditer.common.collect.model.Children;
import com.cditer.common.collect.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListMapGenericTest {

    // 正确写法
    public List<? extends User> getUserList(){
        return new ArrayList<Children>();
    }

    // error
//    public List<User> getUserList(){
//        return new ArrayList<Children>();
//    }

    // 正确写法
    public Map<String, ? extends List<? extends User>> getUserMap(){
        return new HashMap<String, List<Children>>();
    }

    // error
//    public Map<String, List<User>> getUserMap(){
//        return new HashMap<String, List<Children>>();
//    }
}
