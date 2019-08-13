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
 * 手机端工作人员-会籍前台主管
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
@TableName("biz_staff_special")
public class StaffSpecial extends Model<StaffSpecial> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 俱乐部id
     */
    @TableField("club_id")
    private Integer clubId;
    /**
     * 绑定用户id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 绑定用户昵称
     */
    private String nickname;
    /**
     * 姓名
     */
    private String realname;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 状态 0正常 1异常
     */
    private Integer status;
    /**
     * 截屏提示开关
     */
    @TableField("screenshots_alarm")
    private Integer screenshotsAlarm;
    /**
     * 本月新增会员数量
     */
    @TableField("new_member_counts")
    private Integer newMemberCounts;
    /**
     * 会员数量
     */
    @TableField("member_counts")
    private Integer memberCounts;
    /**
     * 权限json串
     */
    private String auth;
    /**
     * 添加时间
     */
    @TableField("insert_time")
    private Integer insertTime;
    /**
     * 手机端工作人员类型 0会籍 1前台 1主管
     */
    private Integer type;


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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getScreenshotsAlarm() {
        return screenshotsAlarm;
    }

    public void setScreenshotsAlarm(Integer screenshotsAlarm) {
        this.screenshotsAlarm = screenshotsAlarm;
    }

    public Integer getNewMemberCounts() {
        return newMemberCounts;
    }

    public void setNewMemberCounts(Integer newMemberCounts) {
        this.newMemberCounts = newMemberCounts;
    }

    public Integer getMemberCounts() {
        return memberCounts;
    }

    public void setMemberCounts(Integer memberCounts) {
        this.memberCounts = memberCounts;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public Integer getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Integer insertTime) {
        this.insertTime = insertTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "StaffSpecial{" +
        "id=" + id +
        ", clubId=" + clubId +
        ", userId=" + userId +
        ", nickname=" + nickname +
        ", realname=" + realname +
        ", avatar=" + avatar +
        ", status=" + status +
        ", screenshotsAlarm=" + screenshotsAlarm +
        ", newMemberCounts=" + newMemberCounts +
        ", memberCounts=" + memberCounts +
        ", auth=" + auth +
        ", insertTime=" + insertTime +
        ", type=" + type +
        "}";
    }
}
