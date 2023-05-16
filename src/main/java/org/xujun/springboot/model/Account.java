package org.xujun.springboot.model;

/**
 * <p>Title : Account</p>
 * <p>Description : Account</p>
 * <p>DevelopTools : Eclipse_x64_v4.13.0R</p>
 * <p>DevelopSystem : Windows7</p>
 * <p>Company : org.xujun</p>
 * @author : XuJun
 * @date : 2019年12月18日 下午3:07:27
 * @version : 1.0.0
 */
public class Account {

    /** ID **/
    private Long id;
    /** 用户名 **/
    private String name;
    /** 密码 **/
    private String passwd;

    public Account() {
        super();
    }
    
    //zheshi这是你一这是一个你好 
    

    public Account(Long id, String name, String passwd) {
        super();
        this.id = id;
        this.name = name;
        this.passwd = passwd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Account [id=");
        builder.append(id);
        builder.append(", name=");
        builder.append(name);
        builder.append(", passwd=");
        builder.append(passwd);
        builder.append("]");
        return builder.toString();
    }

}