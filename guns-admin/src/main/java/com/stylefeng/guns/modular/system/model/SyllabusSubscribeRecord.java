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
 * 团操课用户预约记录
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
@TableName("biz_syllabus_subscribe_record")
public class SyllabusSubscribeRecord extends Model<SyllabusSubscribeRecord> {

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
     * 课程id
     */
    @TableField("syllabus_id")
    private Integer syllabusId;
    /**
     * 课程名称
     */
    @TableField("syllabus_name")
    private String syllabusName;
    /**
     * 课程种类id
     */
    @TableField("group_id")
    private Integer groupId;
    /**
     * 用户id
     */
    @TableField("vip_id")
    private Integer vipId;
    /**
     * 姓名
     */
    private String realname;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 教室id
     */
    @TableField("classroom_id")
    private Integer classroomId;
    /**
     * 座位号
     */
    @TableField("seat_number")
    private Integer seatNumber;
    /**
     * 上课开始时间
     */
    @TableField("class_start_time")
    private Integer classStartTime;
    /**
     * 上课结束时间
     */
    @TableField("class_end_time")
    private Integer classEndTime;
    /**
     * 卡类型
     */
    @TableField("card_type")
    private Integer cardType;
    /**
     * 卡id
     */
    @TableField("card_id")
    private Integer cardId;
    /**
     * 扣除次数
     */
    @TableField("card_deduct_num")
    private Integer cardDeductNum;
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
     * 会籍ID
     */
    @TableField("Membership_id")
    private Integer MembershipId;
    /**
     * 会籍名称
     */
    @TableField("Membership_name")
    private String MembershipName;
    /**
     * 备注
     */
    private String remark;
    /**
     * 预约时间
     */
    @TableField("insert_time")
    private Integer insertTime;
    /**
     * 预约人类型 0非会员 1vip会员 2私教会员 
     */
    @TableField("user_type")
    private Integer userType;
    /**
     * 当前预约状态，0正常 1未确认 2已确认 3学员已取消 4 教练已取消
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

    public Integer getSyllabusId() {
        return syllabusId;
    }

    public void setSyllabusId(Integer syllabusId) {
        this.syllabusId = syllabusId;
    }

    public String getSyllabusName() {
        return syllabusName;
    }

    public void setSyllabusName(String syllabusName) {
        this.syllabusName = syllabusName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getVipId() {
        return vipId;
    }

    public void setVipId(Integer vipId) {
        this.vipId = vipId;
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

    public Integer getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Integer classroomId) {
        this.classroomId = classroomId;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Integer getClassStartTime() {
        return classStartTime;
    }

    public void setClassStartTime(Integer classStartTime) {
        this.classStartTime = classStartTime;
    }

    public Integer getClassEndTime() {
        return classEndTime;
    }

    public void setClassEndTime(Integer classEndTime) {
        this.classEndTime = classEndTime;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public Integer getCardDeductNum() {
        return cardDeductNum;
    }

    public void setCardDeductNum(Integer cardDeductNum) {
        this.cardDeductNum = cardDeductNum;
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

    public Integer getMembershipId() {
        return MembershipId;
    }

    public void setMembershipId(Integer MembershipId) {
        this.MembershipId = MembershipId;
    }

    public String getMembershipName() {
        return MembershipName;
    }

    public void setMembershipName(String MembershipName) {
        this.MembershipName = MembershipName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Integer insertTime) {
        this.insertTime = insertTime;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
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
        return "SyllabusSubscribeRecord{" +
        "id=" + id +
        ", clubId=" + clubId +
        ", syllabusId=" + syllabusId +
        ", syllabusName=" + syllabusName +
        ", groupId=" + groupId +
        ", vipId=" + vipId +
        ", realname=" + realname +
        ", phone=" + phone +
        ", classroomId=" + classroomId +
        ", seatNumber=" + seatNumber +
        ", classStartTime=" + classStartTime +
        ", classEndTime=" + classEndTime +
        ", cardType=" + cardType +
        ", cardId=" + cardId +
        ", cardDeductNum=" + cardDeductNum +
        ", coachId=" + coachId +
        ", coachName=" + coachName +
        ", MembershipId=" + MembershipId +
        ", MembershipName=" + MembershipName +
        ", remark=" + remark +
        ", insertTime=" + insertTime +
        ", userType=" + userType +
        ", status=" + status +
        "}";
    }
}
