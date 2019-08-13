package com.stylefeng.guns.rest.common;

/**
 * 测试用的
 *
 * @author fengshuonan
 * @date 2017-08-25 16:47
 */
public class SimpleObject {

    @Override
	public String toString() {
		return "SimpleObject [user=" + user + ", name=" + name + ", tips=" + tips + ", age=" + age + "]";
	}

	private String user;

    private String name;

    private String tips;

    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
