package com.stylefeng.guns.modular.system.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 俱乐部管理员表（电脑端管理员）
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-25
 */
@TableName("biz_club_admin")
public class ClubAdmin extends Model<ClubAdmin> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 俱乐部id
     */
    @TableField("club_id")
    private Integer clubId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * md5密码盐
     */
    private String salt;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 姓名
     */
    private String realname;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 添加时间
     */
    @TableField("insert_time")
    private String insertTime;
    /**
     * 登录次数
     */
    @TableField("login_count")
    private Integer loginCount;
    /**
     * 上次登录时间
     */
    @TableField("last_login_time")
    private String lastLoginTime;
    /**
     * json权限数组
     */
    private String authority;
    /**
     * 角色id 2超级管理员 10普通管理员
     */
    private Integer roles;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClubId() {
        return clubId;
    }

    public void setClubId(Integer clubId) {
        this.clubId = clubId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Integer getRoles() {
        return roles;
    }

    public void setRoles(Integer roles) {
        this.roles = roles;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ClubAdmin{" +
        "id=" + id +
        ", clubId=" + clubId +
        ", username=" + username +
        ", password=" + password +
        ", salt=" + salt +
        ", nickname=" + nickname +
        ", realname=" + realname +
        ", phone=" + phone +
        ", insertTime=" + insertTime +
        ", loginCount=" + loginCount +
        ", lastLoginTime=" + lastLoginTime +
        ", authority=" + authority +
        ", roles=" + roles +
        "}";
    }
}
