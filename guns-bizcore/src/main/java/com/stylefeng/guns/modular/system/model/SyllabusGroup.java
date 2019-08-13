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
 * 课程种类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
@TableName("biz_syllabus_group")
public class SyllabusGroup extends Model<SyllabusGroup> {

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
     * 课程类型，是否需要预约
     */
    @TableField("course_type")
    private Integer courseType;
    /**
     * 类型 0团课 1操课
     */
    private Integer type;
    /**
     * 课程名称
     */
    private String title;
    /**
     * 上课地点
     */
    private String place;
    /**
     * 教室ID
     */
    @TableField("classroom_id")
    private Integer classroomId;
    /**
     * 预约上限
     */
    @TableField("people_count")
    private Integer peopleCount;
    /**
     * 开课人数
     */
    @TableField("lower_limit")
    private Integer lowerLimit;
    /**
     * 预约时是否要选座号
     */
    @TableField("is_select_num")
    private Integer isSelectNum;
    /**
     * 预约时是否先扣费
     */
    @TableField("is_sub_deduct_num")
    private Integer isSubDeductNum;
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
     * 关键词
     */
    private String tags;
    /**
     * 简介
     */
    private String description;
    /**
     * 内容
     */
    private String content;
    /**
     * 非会员能否预约
     */
    @TableField("is_vip_user_sub")
    private Integer isVipUserSub;
    /**
     * 课程图片，单张
     */
    private String image;
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
     * 开课说明
     */
    @TableField("open_group_desc")
    private String openGroupDesc;
    /**
     * 录入时间
     */
    @TableField("insert_time")
    private String insertTime;
    /**
     * 用户能否取消
     */
    @TableField("self_cancel")
    private Integer selfCancel;
    /**
     * 是否隐藏
     */
    private Integer hidden;


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

    public Integer getCourseType() {
        return courseType;
    }

    public void setCourseType(Integer courseType) {
        this.courseType = courseType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Integer classroomId) {
        this.classroomId = classroomId;
    }

    public Integer getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(Integer peopleCount) {
        this.peopleCount = peopleCount;
    }

    public Integer getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(Integer lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public Integer getIsSelectNum() {
        return isSelectNum;
    }

    public void setIsSelectNum(Integer isSelectNum) {
        this.isSelectNum = isSelectNum;
    }

    public Integer getIsSubDeductNum() {
        return isSubDeductNum;
    }

    public void setIsSubDeductNum(Integer isSubDeductNum) {
        this.isSubDeductNum = isSubDeductNum;
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIsVipUserSub() {
        return isVipUserSub;
    }

    public void setIsVipUserSub(Integer isVipUserSub) {
        this.isVipUserSub = isVipUserSub;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getOpenGroupDesc() {
        return openGroupDesc;
    }

    public void setOpenGroupDesc(String openGroupDesc) {
        this.openGroupDesc = openGroupDesc;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public Integer getSelfCancel() {
        return selfCancel;
    }

    public void setSelfCancel(Integer selfCancel) {
        this.selfCancel = selfCancel;
    }

    public Integer getHidden() {
        return hidden;
    }

    public void setHidden(Integer hidden) {
        this.hidden = hidden;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SyllabusGroup{" +
        "id=" + id +
        ", clubId=" + clubId +
        ", courseType=" + courseType +
        ", type=" + type +
        ", title=" + title +
        ", place=" + place +
        ", classroomId=" + classroomId +
        ", peopleCount=" + peopleCount +
        ", lowerLimit=" + lowerLimit +
        ", isSelectNum=" + isSelectNum +
        ", isSubDeductNum=" + isSubDeductNum +
        ", coachId=" + coachId +
        ", coachName=" + coachName +
        ", temporaryCoach=" + temporaryCoach +
        ", tags=" + tags +
        ", description=" + description +
        ", content=" + content +
        ", isVipUserSub=" + isVipUserSub +
        ", image=" + image +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", openGroupDesc=" + openGroupDesc +
        ", insertTime=" + insertTime +
        ", selfCancel=" + selfCancel +
        ", hidden=" + hidden +
        "}";
    }
}
