package com.cmos.edccommon.web.pojo;

import com.cmos.common.bean.GenericBean;

/**
 * Created by alen on 16-12-6.
 */
public class UserInfo extends GenericBean {

    private String name;
    private Integer age;

    public UserInfo(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }
}
