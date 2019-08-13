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
 * 团操课排期表课程
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
@TableName("biz_syllabus_item")
public class SyllabusItem extends Model<SyllabusItem> {

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
     * 课程种类id
     */
    @TableField("group_id")
    private Integer groupId;
    /**
     * 课程排期设置id
     */
    @TableField("schedule_setting_id")
    private Integer scheduleSettingId;
    /**
     * 日
     */
    private Integer day;
    /**
     * 开始时间
     */
    @TableField("start_time")
    private Integer startTime;
    /**
     * 结束时间
     */
    @TableField("end_time")
    private Integer endTime;
    /**
     * 已预约人数
     */
    @TableField("has_subscribe")
    private Integer hasSubscribe;
    /**
     * 课程状态  1未上课  2已上课 3已取消
     */
    private Integer status;
    /**
     * 课程名称
     */
    private String title;
    /**
     * 所属教练ID
     */
    @TableField("coach_id")
    private Integer coachId;
    /**
     * 所属教练名称
     */
    @TableField("coach_name")
    private String coachName;
    /**
     * 临时教练
     */
    @TableField("temporary_coach")
    private String temporaryCoach;
    /**
     * 录入时间
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

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getScheduleSettingId() {
        return scheduleSettingId;
    }

    public void setScheduleSettingId(Integer scheduleSettingId) {
        this.scheduleSettingId = scheduleSettingId;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public Integer getHasSubscribe() {
        return hasSubscribe;
    }

    public void setHasSubscribe(Integer hasSubscribe) {
        this.hasSubscribe = hasSubscribe;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCoachId() {
        return coachId;
    }

    public void setCoachId(Integer coachId) {
        this.coachId = coachId;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public String getTemporaryCoach() {
        return temporaryCoach;
    }

    public void setTemporaryCoach(String temporaryCoach) {
        this.temporaryCoach = temporaryCoach;
    }

    public Integer getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Integer insertTime) {
        this.insertTime = insertTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SyllabusItem{" +
        "id=" + id +
        ", clubId=" + clubId +
        ", groupId=" + groupId +
        ", scheduleSettingId=" + scheduleSettingId +
        ", day=" + day +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", hasSubscribe=" + hasSubscribe +
        ", status=" + status +
        ", title=" + title +
        ", coachId=" + coachId +
        ", coachName=" + coachName +
        ", temporaryCoach=" + temporaryCoach +
        ", insertTime=" + insertTime +
        "}";
    }
}
