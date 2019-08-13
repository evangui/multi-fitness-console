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
 * 会员卡设置
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
@TableName("biz_setting_card")
public class SettingCard extends Model<SettingCard> {

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
     * 最长请假天数
     */
    @TableField("leave_days")
    private Integer leaveDays;
    /**
     * 最短请假天数
     */
    @TableField("less_leave_days")
    private Integer lessLeaveDays;
    /**
     * 最晚开卡期限
     */
    private Integer deadline;
    /**
     * 会员自助查询开关
     */
    private Integer record;
    /**
     * 剩余额度屏蔽
     */
    @TableField("left_amount_hide")
    private Integer leftAmountHide;
    /**
     * 定金卡消费开关
     */
    @TableField("front_consume_switch")
    private Integer frontConsumeSwitch;
    /**
     * 次卡消次密码
     */
    @TableField("once_card_used_pass")
    private String onceCardUsedPass;
    /**
     * 是否限制卡的开始使用时间
     */
    @TableField("card_start_use_time_switch")
    private String cardStartUseTimeSwitch;
    /**
     * 是否开启会员录入密码
     */
    @TableField("vip_passwd")
    private Integer vipPasswd;


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

    public Integer getLeaveDays() {
        return leaveDays;
    }

    public void setLeaveDays(Integer leaveDays) {
        this.leaveDays = leaveDays;
    }

    public Integer getLessLeaveDays() {
        return lessLeaveDays;
    }

    public void setLessLeaveDays(Integer lessLeaveDays) {
        this.lessLeaveDays = lessLeaveDays;
    }

    public Integer getDeadline() {
        return deadline;
    }

    public void setDeadline(Integer deadline) {
        this.deadline = deadline;
    }

    public Integer getRecord() {
        return record;
    }

    public void setRecord(Integer record) {
        this.record = record;
    }

    public Integer getLeftAmountHide() {
        return leftAmountHide;
    }

    public void setLeftAmountHide(Integer leftAmountHide) {
        this.leftAmountHide = leftAmountHide;
    }

    public Integer getFrontConsumeSwitch() {
        return frontConsumeSwitch;
    }

    public void setFrontConsumeSwitch(Integer frontConsumeSwitch) {
        this.frontConsumeSwitch = frontConsumeSwitch;
    }

    public String getOnceCardUsedPass() {
        return onceCardUsedPass;
    }

    public void setOnceCardUsedPass(String onceCardUsedPass) {
        this.onceCardUsedPass = onceCardUsedPass;
    }

    public String getCardStartUseTimeSwitch() {
        return cardStartUseTimeSwitch;
    }

    public void setCardStartUseTimeSwitch(String cardStartUseTimeSwitch) {
        this.cardStartUseTimeSwitch = cardStartUseTimeSwitch;
    }

    public Integer getVipPasswd() {
        return vipPasswd;
    }

    public void setVipPasswd(Integer vipPasswd) {
        this.vipPasswd = vipPasswd;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SettingCard{" +
        "id=" + id +
        ", clubId=" + clubId +
        ", leaveDays=" + leaveDays +
        ", lessLeaveDays=" + lessLeaveDays +
        ", deadline=" + deadline +
        ", record=" + record +
        ", leftAmountHide=" + leftAmountHide +
        ", frontConsumeSwitch=" + frontConsumeSwitch +
        ", onceCardUsedPass=" + onceCardUsedPass +
        ", cardStartUseTimeSwitch=" + cardStartUseTimeSwitch +
        ", vipPasswd=" + vipPasswd +
        "}";
    }
}
