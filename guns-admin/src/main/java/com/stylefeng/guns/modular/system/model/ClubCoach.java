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
 * 教练
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
@TableName("biz_club_coach")
public class ClubCoach extends Model<ClubCoach> {

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
     * 介绍
     */
    private String desc;
    /**
     * 擅长
     */
    @TableField("good_at")
    private String goodAt;
    /**
     * APP排序
     */
    private Integer order;
    /**
     * 状态 0正常 1异常
     */
    private Integer status;
    /**
     * 本月私教次数
     */
    @TableField("month_ptrain_count")
    private Integer monthPtrainCount;
    /**
     * 本月总体评分
     */
    @TableField("month_avg_score")
    private Integer monthAvgScore;
    /**
     * 学员个数
     */
    @TableField("vip_count")
    private Integer vipCount;
    /**
     * 权限json串
     */
    private String auth;
    
    private String fingerprint;
    
	/**
     * 添加时间
     */
    @TableField("insert_time")
    private Integer insertTime;


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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getGoodAt() {
        return goodAt;
    }

    public void setGoodAt(String goodAt) {
        this.goodAt = goodAt;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getMonthPtrainCount() {
        return monthPtrainCount;
    }

    public void setMonthPtrainCount(Integer monthPtrainCount) {
        this.monthPtrainCount = monthPtrainCount;
    }

    public Integer getMonthAvgScore() {
        return monthAvgScore;
    }

    public void setMonthAvgScore(Integer monthAvgScore) {
        this.monthAvgScore = monthAvgScore;
    }

    public Integer getVipCount() {
        return vipCount;
    }

    public void setVipCount(Integer vipCount) {
        this.vipCount = vipCount;
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

    public String getFingerprint() {
		return fingerprint;
	}

	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}
	
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ClubCoach{" +
        "id=" + id +
        ", clubId=" + clubId +
        ", userId=" + userId +
        ", nickname=" + nickname +
        ", realname=" + realname +
        ", avatar=" + avatar +
        ", desc=" + desc +
        ", goodAt=" + goodAt +
        ", order=" + order +
        ", status=" + status +
        ", monthPtrainCount=" + monthPtrainCount +
        ", monthAvgScore=" + monthAvgScore +
        ", vipCount=" + vipCount +
        ", auth=" + auth +
        ", fingerprint=" + fingerprint +
        ", insertTime=" + insertTime +
        "}";
    }
}
