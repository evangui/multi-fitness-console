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
 * 私教课预约记录
 * </p>
 *
 * @author guiyj007123
 * @since 2018-08-18
 */
@TableName("biz_ptrain_bespeak_record")
public class PtrainBespeakRecord extends Model<PtrainBespeakRecord> {

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
     * 预约开始时间
     */
    @TableField("from_time")
    private Integer fromTime;
    /**
     * 预约结束时间
     */
    @TableField("to_time")
    private Integer toTime;
    /**
     * 预约日期，格式YY-mm-dd
     */
    private String date;
    /**
     * 会籍ID
     */
    @TableField("membership_id")
    private Integer membershipId;
    /**
     * 会籍名称
     */
    @TableField("membership_name")
    private String membershipName;
    /**
     * 预约时间
     */
    @TableField("insert_time")
    private Integer insertTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 卡类型
     */
    @TableField("card_type")
    private String cardType;
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
     * 预约记录类型 0未知 1学生约教练 2教练约学生 5教练休息-非预约 
     */
    @TableField("record_type")
    private Integer recordType;
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

    public Integer getFromTime() {
        return fromTime;
    }

    public void setFromTime(Integer fromTime) {
        this.fromTime = fromTime;
    }

    public Integer getToTime() {
        return toTime;
    }

    public void setToTime(Integer toTime) {
        this.toTime = toTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getmembershipId() {
        return membershipId;
    }

    public void setmembershipId(Integer membershipId) {
        this.membershipId = membershipId;
    }

    public String getmembershipName() {
        return membershipName;
    }

    public void setmembershipName(String membershipName) {
        this.membershipName = membershipName;
    }

    public Integer getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Integer insertTime) {
        this.insertTime = insertTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
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

    public Integer getRecordType() {
        return recordType;
    }

    public void setRecordType(Integer recordType) {
        this.recordType = recordType;
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
        return "PtrainBespeakRecord{" +
        "id=" + id +
        ", clubId=" + clubId +
        ", coachId=" + coachId +
        ", coachName=" + coachName +
        ", vipId=" + vipId +
        ", realname=" + realname +
        ", phone=" + phone +
        ", fromTime=" + fromTime +
        ", toTime=" + toTime +
        ", date=" + date +
        ", membershipId=" + membershipId +
        ", membershipName=" + membershipName +
        ", insertTime=" + insertTime +
        ", remark=" + remark +
        ", cardType=" + cardType +
        ", cardId=" + cardId +
        ", cardDeductNum=" + cardDeductNum +
        ", recordType=" + recordType +
        ", userType=" + userType +
        ", status=" + status +
        "}";
    }
}
