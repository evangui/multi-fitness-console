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
 * 团操课排期设置
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
@TableName("biz_syllabus_schedule_setting")
public class SyllabusScheduleSetting extends Model<SyllabusScheduleSetting> {

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
     * 开始时间
     */
    @TableField("start_time")
    private String startTime;
    /**
     * 结束时间
     */
    @TableField("end_time")
    private String endTime;
    /**
     * 删除时间
     */
    @TableField("del_time")
    private String delTime;
    /**
     * 总课程数
     */
    @TableField("lesson_total")
    private Integer lessonTotal;
    /**
     * 操作人
     */
    @TableField("del_manager")
    private String delManager;
    /**
     * 周每日排期配置
     */
    private String week;
    /**
     * 录入时间
     */
    @TableField("insert_time")
    private String insertTime;
    /**
     * 状态 1正常 0已删除
     */
    private Integer status;


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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDelTime() {
        return delTime;
    }

    public void setDelTime(String delTime) {
        this.delTime = delTime;
    }

    public Integer getLessonTotal() {
        return lessonTotal;
    }

    public void setLessonTotal(Integer lessonTotal) {
        this.lessonTotal = lessonTotal;
    }

    public String getDelManager() {
        return delManager;
    }

    public void setDelManager(String delManager) {
        this.delManager = delManager;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SyllabusScheduleSetting{" +
        "id=" + id +
        ", clubId=" + clubId +
        ", groupId=" + groupId +
        ", title=" + title +
        ", coachId=" + coachId +
        ", coachName=" + coachName +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", delTime=" + delTime +
        ", lessonTotal=" + lessonTotal +
        ", delManager=" + delManager +
        ", week=" + week +
        ", insertTime=" + insertTime +
        ", status=" + status +
        "}";
    }
}
