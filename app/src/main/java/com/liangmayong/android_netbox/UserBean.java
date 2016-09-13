package com.liangmayong.android_netbox;

/**
 * Created by liangmayong on 2016/9/13.
 */
public class UserBean {
    private String user, age;

    public String getAge() {
        return age;
    }

    public String getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "user='" + user + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
