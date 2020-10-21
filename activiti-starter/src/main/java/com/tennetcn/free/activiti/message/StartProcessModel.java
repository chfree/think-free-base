package com.tennetcn.free.activiti.message;

import com.tennetcn.free.core.util.StringHelper;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-09-18 12:40
 * @comment
 */

@Data
public class StartProcessModel {
    private String key;

    private String pdId;

    private String businessId;

    private List<String> users;

    private List<String> groups;

    private String assignee;

    private Map<String,Object> vars;

    public void setGroups(List<String> groups){
        this.groups = groups;
    }

    public void setGroups(String ...groups){
        this.groups = Arrays.asList(groups);
    }

    public void setUsers(List<String> users){
        this.users = users;
    }

    public void setUsers(String ...users){
        this.users = Arrays.asList(users);
    }

    public String getCanUsers(){
        if(this.users==null){
            return "";
        }
        return StringHelper.join(this.users);
    }

    public String getCanGroups(){
        if(this.groups==null){
            return "";
        }
        return StringHelper.join(this.groups);
    }
}
