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
 * 储值卡消费记录
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
@TableName("biz_card_storedvaluecard_spend")
public class CardStoredvaluecardSpend extends Model<CardStoredvaluecardSpend> {

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
     * 卡ID
     */
    @TableField("card_id")
    private Integer cardId;
    /**
     * 卡号码
     */
    @TableField("card_number")
    private Integer cardNumber;
    /**
     * 私教卡名
     */
    private String title;
    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 姓名
     */
    private String realname;
    /**
     * 消费金额
     */
    @TableField("spend_money")
    private BigDecimal spendMoney;
    /**
     * 录入时间
     */
    @TableField("insert_time")
    private String insertTime;
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
     * 财务备注
     */
    private String bankremark;
    /**
     * 状态 0正常 1 已删除2 退款
     */
    @TableField("card_status")
    private Integer cardStatus;
    /**
     * 消次端口 0默认 1收银台 2前台代消 3APP收银 4APP商城 5转卡扣费 6课程预约 7它店消费 8门禁扣费 9场地预订 11活动报名
     */
    private Integer port;


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

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public Integer getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Integer cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public BigDecimal getSpendMoney() {
        return spendMoney;
    }

    public void setSpendMoney(BigDecimal spendMoney) {
        this.spendMoney = spendMoney;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
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

    public String getBankremark() {
        return bankremark;
    }

    public void setBankremark(String bankremark) {
        this.bankremark = bankremark;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CardStoredvaluecardSpend{" +
        "id=" + id +
        ", clubId=" + clubId +
        ", cardId=" + cardId +
        ", cardNumber=" + cardNumber +
        ", title=" + title +
        ", userId=" + userId +
        ", realname=" + realname +
        ", spendMoney=" + spendMoney +
        ", insertTime=" + insertTime +
        ", rcepteionId=" + rcepteionId +
        ", rcepteionName=" + rcepteionName +
        ", coachId=" + coachId +
        ", coachName=" + coachName +
        ", membershipId=" + membershipId +
        ", membershipName=" + membershipName +
        ", opeUserId=" + opeUserId +
        ", opeUsername=" + opeUsername +
        ", phone=" + phone +
        ", remark=" + remark +
        ", bankremark=" + bankremark +
        ", cardStatus=" + cardStatus +
        ", port=" + port +
        "}";
    }
}
