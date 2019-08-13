package com.stylefeng.guns.modular.system.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 活动
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
@TableName("biz_activity")
public class Activity extends Model<Activity> {

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
     * 活动类型：1 课程预约 2打卡有礼 3大转盘 4 自定义 5 健身体验券
     */
    private Integer type;
    /**
     * 活动名称
     */
    private String title;
    /**
     * 封面
     */
    private String cover;
    /**
     * 后台活动编辑密码
     */
    @TableField("edit_pwd")
    private String editPwd;
    /**
     * 上课地点
     */
    @TableField("course_address")
    private String courseAddress;
    /**
     * 上课时间
     */
    @TableField("course_time")
    private String courseTime;
    /**
     * 人数
     */
    private Integer number;
    /**
     * 签到密码
     */
    @TableField("sign_code")
    private String signCode;
    /**
     * 课程介绍
     */
    private String content;
    /**
     * 授课人信息，多个name avatar goodAt desc
     */
    private String teachers;
    /**
     * 最多购买次数
     */
    @TableField("buy_times")
    private String buyTimes;
    /**
     * 总费用
     */
    @TableField("total_fee")
    private BigDecimal totalFee;
    /**
     * 活动地点
     */
    @TableField("field_address")
    private String fieldAddress;
    /**
     * 联系电话
     */
    @TableField("field_phone")
    private String fieldPhone;
    /**
     * 报名开始时间
     */
    @TableField("enroll_start_time")
    private String enrollStartTime;
    /**
     * 报名结束时间
     */
    @TableField("enroll_end_time")
    private String enrollEndTime;
    /**
     * 活动开始时间
     */
    @TableField("start_time")
    private String startTime;
    /**
     * 活动开始时间
     */
    @TableField("end_time")
    private String endTime;
    /**
     * 自定义字段 category inputType optionInfo required sort
     */
    @TableField("custom_fields")
    private String customFields;
    /**
     * 活动项目 title amount number
     */
    private String projects;
    /**
     * 添加时间
     */
    @TableField("insert_time")
    private String insertTime;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 是否隐藏
     */
    private Integer hidden;
    /**
     * 活动状态
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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getEditPwd() {
        return editPwd;
    }

    public void setEditPwd(String editPwd) {
        this.editPwd = editPwd;
    }

    public String getCourseAddress() {
        return courseAddress;
    }

    public void setCourseAddress(String courseAddress) {
        this.courseAddress = courseAddress;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getSignCode() {
        return signCode;
    }

    public void setSignCode(String signCode) {
        this.signCode = signCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTeachers() {
        return teachers;
    }

    public void setTeachers(String teachers) {
        this.teachers = teachers;
    }

    public String getBuyTimes() {
        return buyTimes;
    }

    public void setBuyTimes(String buyTimes) {
        this.buyTimes = buyTimes;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public String getFieldAddress() {
        return fieldAddress;
    }

    public void setFieldAddress(String fieldAddress) {
        this.fieldAddress = fieldAddress;
    }

    public String getFieldPhone() {
        return fieldPhone;
    }

    public void setFieldPhone(String fieldPhone) {
        this.fieldPhone = fieldPhone;
    }

    public String getEnrollStartTime() {
        return enrollStartTime;
    }

    public void setEnrollStartTime(String enrollStartTime) {
        this.enrollStartTime = enrollStartTime;
    }

    public String getEnrollEndTime() {
        return enrollEndTime;
    }

    public void setEnrollEndTime(String enrollEndTime) {
        this.enrollEndTime = enrollEndTime;
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

    public String getCustomFields() {
        return customFields;
    }

    public void setCustomFields(String customFields) {
        this.customFields = customFields;
    }

    public String getProjects() {
        return projects;
    }

    public void setProjects(String projects) {
        this.projects = projects;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getHidden() {
        return hidden;
    }

    public void setHidden(Integer hidden) {
        this.hidden = hidden;
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
        return "Activity{" +
        "id=" + id +
        ", clubId=" + clubId +
        ", type=" + type +
        ", title=" + title +
        ", cover=" + cover +
        ", editPwd=" + editPwd +
        ", courseAddress=" + courseAddress +
        ", courseTime=" + courseTime +
        ", number=" + number +
        ", signCode=" + signCode +
        ", content=" + content +
        ", teachers=" + teachers +
        ", buyTimes=" + buyTimes +
        ", totalFee=" + totalFee +
        ", fieldAddress=" + fieldAddress +
        ", fieldPhone=" + fieldPhone +
        ", enrollStartTime=" + enrollStartTime +
        ", enrollEndTime=" + enrollEndTime +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", customFields=" + customFields +
        ", projects=" + projects +
        ", insertTime=" + insertTime +
        ", sort=" + sort +
        ", hidden=" + hidden +
        ", status=" + status +
        "}";
    }
}
