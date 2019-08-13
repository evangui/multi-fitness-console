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
 * 卡消课记录
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
@TableName("biz_card_spend_log")
public class CardSpendLog extends Model<CardSpendLog> {

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
     * 卡类型
     */
    @TableField("log_type")
    private Integer logType;
    
	/**
     * 私教卡ID
     */
    @TableField("card_id")
    private Integer cardId;
    /**
     * 用户id
     */
    @TableField("vip_id")
    private Integer vipId;
    /**
     * 课程id
     */
    @TableField("syllabus_id")
    private Integer syllabusId;
    /**
     * 姓名
     */
    private String realname;
    /**
     * 消耗次数
     */
    private Integer costs;
    /**
     * 录入时间
     */
    @TableField("insert_time")
    private Integer insertTime;
    /**
     * 单价
     */
    @TableField("unit_price")
    private BigDecimal unitPrice;
    /**
     * 私教卡名
     */
    private String title;
    /**
     * 前台ID
     */
    @TableField("rcepteion_id")
    private Integer rcepteionId;
    /**
     * 前台名称
     */
    @TableField("rcepteion_name")
    private String rcepteionName;
    /**
     * 前台备注
     */
    @TableField("reception_remark")
    private String receptionRemark;
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
    @TableField("membership_id")
    private Integer membershipId;
    /**
     * 会籍名称
     */
    @TableField("membership_name")
    private String membershipName;
    /**
     * 经办人用户ID
     */
    @TableField("ope_user_id")
    private Integer opeUserId;
    /**
     * 经办人用户名
     */
    @TableField("ope_username")
    private String opeUsername;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 备注
     */
    private String remark;
    /**
     * 状态 0正常 1 已删除2 已退课
     */
    @TableField("card_status")
    private Integer cardStatus;
    /**
     * 消课端口 0默认 3转卡扣费 4三方消课 5两方消课 6前台代消 7课程预约 8指纹消课 9它店消费 22无记录
     */
    private Integer port;
    /**
     * 课程类型 0私教课 1团体课
     */
    @TableField("is_syllabus_sub")
    private Integer isSyllabusSub;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLogType() {
		return logType;
	}

	public void setLogType(Integer logType) {
		this.logType = logType;
	}
	
    public Integer getClubId() {
        return clubId;
    }

    public void setClubId(Integer clubId) {
        this.clubId = clubId;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public Integer getVipId() {
        return vipId;
    }

    public void setVipId(Integer vipId) {
        this.vipId = vipId;
    }

    public Integer getSyllabusId() {
        return syllabusId;
    }

    public void setSyllabusId(Integer syllabusId) {
        this.syllabusId = syllabusId;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Integer getCosts() {
        return costs;
    }

    public void setCosts(Integer costs) {
        this.costs = costs;
    }

    public Integer getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Integer insertTime) {
        this.insertTime = insertTime;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getRcepteionId() {
        return rcepteionId;
    }

    public void setRcepteionId(Integer rcepteionId) {
        this.rcepteionId = rcepteionId;
    }

    public String getRcepteionName() {
        return rcepteionName;
    }

    public void setRcepteionName(String rcepteionName) {
        this.rcepteionName = rcepteionName;
    }

    public String getReceptionRemark() {
        return receptionRemark;
    }

    public void setReceptionRemark(String receptionRemark) {
        this.receptionRemark = receptionRemark;
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
        return membershipId;
    }

    public void setMembershipId(Integer membershipId) {
        this.membershipId = membershipId;
    }

    public String getMembershipName() {
        return membershipName;
    }

    public void setMembershipName(String membershipName) {
        this.membershipName = membershipName;
    }

    public Integer getOpeUserId() {
        return opeUserId;
    }

    public void setOpeUserId(Integer opeUserId) {
        this.opeUserId = opeUserId;
    }

    public String getOpeUsername() {
        return opeUsername;
    }

    public void setOpeUsername(String opeUsername) {
        this.opeUsername = opeUsername;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(Integer cardStatus) {
        this.cardStatus = cardStatus;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getIsSyllabusSub() {
        return isSyllabusSub;
    }

    public void setIsSyllabusSub(Integer isSyllabusSub) {
        this.isSyllabusSub = isSyllabusSub;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CardPtraincardSpend{" +
        "id=" + id +
        ", clubId=" + clubId +
        ", cardId=" + cardId +
        ", vipId=" + vipId +
        ", syllabusId=" + syllabusId +
        ", realname=" + realname +
        ", costs=" + costs +
        ", insertTime=" + insertTime +
        ", unitPrice=" + unitPrice +
        ", title=" + title +
        ", rcepteionId=" + rcepteionId +
        ", rcepteionName=" + rcepteionName +
        ", receptionRemark=" + receptionRemark +
        ", coachId=" + coachId +
        ", coachName=" + coachName +
        ", membershipId=" + membershipId +
        ", membershipName=" + membershipName +
        ", opeUserId=" + opeUserId +
        ", opeUsername=" + opeUsername +
        ", phone=" + phone +
        ", remark=" + remark +
        ", cardStatus=" + cardStatus +
        ", port=" + port +
        ", isSyllabusSub=" + isSyllabusSub +
        "}";
    }
}
