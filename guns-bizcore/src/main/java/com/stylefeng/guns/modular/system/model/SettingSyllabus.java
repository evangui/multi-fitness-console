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
 * 团操课设置
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
@TableName("biz_setting_syllabus")
public class SettingSyllabus extends Model<SettingSyllabus> {

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
     * 预约开始时间（秒）
     */
    @TableField("subscribe_start_time")
    private String subscribeStartTime;
    /**
     * 预约结束时间
     */
    @TableField("subscribe_end_time")
    private String subscribeEndTime;
    /**
     * 人数不足自动取消时间
     */
    @TableField("not_enough_cancel_time")
    private String notEnoughCancelTime;
    /**
     * 会员自主取消时间
     */
    @TableField("self_cancel_time")
    private String selfCancelTime;
    /**
     * 团操课查看开始时间戳
     */
    @TableField("start_date")
    private Integer startDate;
    /**
     * 团操课查看结束时间戳
     */
    @TableField("end_date")
    private Integer endDate;


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

    public String getSubscribeStartTime() {
        return subscribeStartTime;
    }

    public void setSubscribeStartTime(String subscribeStartTime) {
        this.subscribeStartTime = subscribeStartTime;
    }

    public String getSubscribeEndTime() {
        return subscribeEndTime;
    }

    public void setSubscribeEndTime(String subscribeEndTime) {
        this.subscribeEndTime = subscribeEndTime;
    }

    public String getNotEnoughCancelTime() {
        return notEnoughCancelTime;
    }

    public void setNotEnoughCancelTime(String notEnoughCancelTime) {
        this.notEnoughCancelTime = notEnoughCancelTime;
    }

    public String getSelfCancelTime() {
        return selfCancelTime;
    }

    public void setSelfCancelTime(String selfCancelTime) {
        this.selfCancelTime = selfCancelTime;
    }

    public Integer getStartDate() {
        return startDate;
    }

    public void setStartDate(Integer startDate) {
        this.startDate = startDate;
    }

    public Integer getEndDate() {
        return endDate;
    }

    public void setEndDate(Integer endDate) {
        this.endDate = endDate;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SettingSyllabus{" +
        "id=" + id +
        ", clubId=" + clubId +
        ", subscribeStartTime=" + subscribeStartTime +
        ", subscribeEndTime=" + subscribeEndTime +
        ", notEnoughCancelTime=" + notEnoughCancelTime +
        ", selfCancelTime=" + selfCancelTime +
        ", startDate=" + startDate +
        ", endDate=" + endDate +
        "}";
    }
}
