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
 * 俱乐部卡种
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
@TableName("biz_card_category")
public class CardCategory extends Model<CardCategory> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 俱乐部id
     */
    @TableField("club_id")
    private Integer clubId;
    /**
     * 卡种名 
     */
    private String title;
    /**
     * 到期时间
     */
    @TableField("expire_time")
    private Integer expireTime;
    /**
     * 到期天数
     */
    @TableField("expire_day")
    private Integer expireDay;
    /**
     * 充值天数 
     */
    private Integer value;
    /**
     * 可请假天数 
     */
    @TableField("leave_max_day")
    private Integer leaveMaxDay;
    /**
     * 是否可用
     */
    private Integer disabled;
    /**
     * 是否限制分享
     */
    @TableField("share_restrict")
    private Integer shareRestrict;
    /**
     * 卡类型1 时间卡 2私教卡 3次卡4储值卡
     */
    @TableField("card_type")
    private Integer cardType;


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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Integer expireTime) {
        this.expireTime = expireTime;
    }

    public Integer getExpireDay() {
        return expireDay;
    }

    public void setExpireDay(Integer expireDay) {
        this.expireDay = expireDay;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getLeaveMaxDay() {
        return leaveMaxDay;
    }

    public void setLeaveMaxDay(Integer leaveMaxDay) {
        this.leaveMaxDay = leaveMaxDay;
    }

    public Integer getdisabled() {
        return disabled;
    }

    public void setdisabled(Integer disabled) {
        this.disabled = disabled;
    }

    public Integer getShareRestrict() {
        return shareRestrict;
    }

    public void setShareRestrict(Integer shareRestrict) {
        this.shareRestrict = shareRestrict;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CardCategory{" +
        "id=" + id +
        ", clubId=" + clubId +
        ", title=" + title +
        ", expireTime=" + expireTime +
        ", expireDay=" + expireDay +
        ", value=" + value +
        ", leaveMaxDay=" + leaveMaxDay +
        ", disabled=" + disabled +
        ", shareRestrict=" + shareRestrict +
        ", cardType=" + cardType +
        "}";
    }
}
