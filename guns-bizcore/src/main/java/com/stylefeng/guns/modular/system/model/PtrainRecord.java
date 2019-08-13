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
 * 私教记录
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
@TableName("biz_ptrain_record")
public class PtrainRecord extends Model<PtrainRecord> {

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
     * 会员id
     */
    @TableField("vip_id")
    private Integer vipId;
    /**
     * 次卡id
     */
    @TableField("once_card_id")
    private Integer onceCardId;
    /**
     * 教练id
     */
    @TableField("coach_id")
    private Integer coachId;
    /**
     * 会籍id
     */
    @TableField("membership_id")
    private Integer membershipId;
    /**
     * 前台id
     */
    @TableField("reception_id")
    private Integer receptionId;
    /**
     * 前台备注
     */
    @TableField("reception_remark")
    private String receptionRemark;
    /**
     * 单价
     */
    @TableField("unit_price")
    private Integer unitPrice;
    /**
     * 扣除次数
     */
    private Integer costs;
    /**
     * 类型，含义待定
     */
    private Integer type;
    /**
     * 课程id
     */
    @TableField("syllabus_id")
    private Integer syllabusId;
    /**
     * 课程是否预约
     */
    @TableField("is_syllabus_sub")
    private Integer isSyllabusSub;
    /**
     * 用户手机号
     */
    @TableField("user_phone")
    private String userPhone;
    /**
     * 添加时间
     */
    @TableField("insert_time")
    private String insertTime;


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

    public Integer getVipId() {
        return vipId;
    }

    public void setVipId(Integer vipId) {
        this.vipId = vipId;
    }

    public Integer getOnceCardId() {
        return onceCardId;
    }

    public void setOnceCardId(Integer onceCardId) {
        this.onceCardId = onceCardId;
    }

    public Integer getCoachId() {
        return coachId;
    }

    public void setCoachId(Integer coachId) {
        this.coachId = coachId;
    }

    public Integer getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(Integer membershipId) {
        this.membershipId = membershipId;
    }

    public Integer getReceptionId() {
        return receptionId;
    }

    public void setReceptionId(Integer receptionId) {
        this.receptionId = receptionId;
    }

    public String getReceptionRemark() {
        return receptionRemark;
    }

    public void setReceptionRemark(String receptionRemark) {
        this.receptionRemark = receptionRemark;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getCosts() {
        return costs;
    }

    public void setCosts(Integer costs) {
        this.costs = costs;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSyllabusId() {
        return syllabusId;
    }

    public void setSyllabusId(Integer syllabusId) {
        this.syllabusId = syllabusId;
    }

    public Integer getIsSyllabusSub() {
        return isSyllabusSub;
    }

    public void setIsSyllabusSub(Integer isSyllabusSub) {
        this.isSyllabusSub = isSyllabusSub;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PtrainRecord{" +
        "id=" + id +
        ", clubId=" + clubId +
        ", vipId=" + vipId +
        ", onceCardId=" + onceCardId +
        ", coachId=" + coachId +
        ", membershipId=" + membershipId +
        ", receptionId=" + receptionId +
        ", receptionRemark=" + receptionRemark +
        ", unitPrice=" + unitPrice +
        ", costs=" + costs +
        ", type=" + type +
        ", syllabusId=" + syllabusId +
        ", isSyllabusSub=" + isSyllabusSub +
        ", userPhone=" + userPhone +
        ", insertTime=" + insertTime +
        "}";
    }
}
