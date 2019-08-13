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
 * 会员储值卡
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
@TableName("biz_card_storedvaluecard")
public class CardStoredvaluecard extends Model<CardStoredvaluecard> {

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
     * 用户id
     */
    @TableField("vip_id")
    private Integer vipId;
    /**
     * 姓名
     */
    private String realname;
    /**
     * 卡名
     */
    private String title;
    /**
     * 卡种类型ID
     */
    @TableField("card_type_id")
    private Integer cardTypeId;
    /**
     * 卡种类型名
     */
    @TableField("card_type_name")
    private String cardTypeName;
    /**
     * 实际添加时间
     */
    @TableField("actual_insert_time")
    private String actualInsertTime;
    /**
     * 开卡时间
     */
    @TableField("open_card_time")
    private String openCardTime;
    /**
     * 开始使用时间
     */
    @TableField("start_use_time")
    private String startUseTime;
    /**
     * 到期时间
     */
    @TableField("expire_time")
    private String expireTime;
    /**
     * 录入时间
     */
    @TableField("insert_time")
    private String insertTime;
    /**
     * 总面额
     */
    @TableField("sum_money")
    private BigDecimal sumMoney;
    /**
     * 已用面额
     */
    @TableField("left_money")
    private BigDecimal leftMoney;
    /**
     * 实收金额
     */
    @TableField("actual_money")
    private BigDecimal actualMoney;
    /**
     * 尾款金额
     */
    private BigDecimal retainage;
    /**
     * 是否自动开卡
     */
    @TableField("auto_open_card_status")
    private Integer autoOpenCardStatus;
    /**
     * 余额
     */
    private BigDecimal balance;
    /**
     * 财务是否确认
     */
    private Integer banksure;
    /**
     * 合同编号
     */
    @TableField("contract_number")
    private String contractNumber;
    /**
     * 是否定金方式
     */
    @TableField("front_money_status")
    private Integer frontMoneyStatus;
    /**
     * 是否续卡
     */
    @TableField("is_renew_card")
    private Integer isRenewCard;
    /**
     * 是否通店卡
     */
    @TableField("is_united_card")
    private Integer isUnitedCard;
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
     * 支付方式
     */
    @TableField("pay_method")
    private String payMethod;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 客户来源id
     */
    @TableField("source_id")
    private Integer sourceId;
    /**
     * 客户来源名
     */
    @TableField("source_name")
    private String sourceName;
    /**
     * 备注
     */
    private String remark;
    /**
     * 端口 1 充值新卡 2 续费
     */
    private Integer port;
    /**
     * 充值记录ID
     */
    @TableField("recharge_id")
    private Integer rechargeId;
    /**
     * 是否启用
     */
    private Integer enabled;
    /**
     * 禁用备注
     */
    @TableField("enable_remark")
    private String enableRemark;


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

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCardTypeId() {
        return cardTypeId;
    }

    public void setCardTypeId(Integer cardTypeId) {
        this.cardTypeId = cardTypeId;
    }

    public String getCardTypeName() {
        return cardTypeName;
    }

    public void setCardTypeName(String cardTypeName) {
        this.cardTypeName = cardTypeName;
    }

    public String getActualInsertTime() {
        return actualInsertTime;
    }

    public void setActualInsertTime(String actualInsertTime) {
        this.actualInsertTime = actualInsertTime;
    }

    public String getOpenCardTime() {
        return openCardTime;
    }

    public void setOpenCardTime(String openCardTime) {
        this.openCardTime = openCardTime;
    }

    public String getStartUseTime() {
        return startUseTime;
    }

    public void setStartUseTime(String startUseTime) {
        this.startUseTime = startUseTime;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public BigDecimal getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(BigDecimal sumMoney) {
        this.sumMoney = sumMoney;
    }

    public BigDecimal getLeftMoney() {
        return leftMoney;
    }

    public void setLeftMoney(BigDecimal leftMoney) {
        this.leftMoney = leftMoney;
    }

    public BigDecimal getActualMoney() {
        return actualMoney;
    }

    public void setActualMoney(BigDecimal actualMoney) {
        this.actualMoney = actualMoney;
    }

    public BigDecimal getRetainage() {
        return retainage;
    }

    public void setRetainage(BigDecimal retainage) {
        this.retainage = retainage;
    }

    public Integer getAutoOpenCardStatus() {
        return autoOpenCardStatus;
    }

    public void setAutoOpenCardStatus(Integer autoOpenCardStatus) {
        this.autoOpenCardStatus = autoOpenCardStatus;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getBanksure() {
        return banksure;
    }

    public void setBanksure(Integer banksure) {
        this.banksure = banksure;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public Integer getFrontMoneyStatus() {
        return frontMoneyStatus;
    }

    public void setFrontMoneyStatus(Integer frontMoneyStatus) {
        this.frontMoneyStatus = frontMoneyStatus;
    }

    public Integer getIsRenewCard() {
        return isRenewCard;
    }

    public void setIsRenewCard(Integer isRenewCard) {
        this.isRenewCard = isRenewCard;
    }

    public Integer getIsUnitedCard() {
        return isUnitedCard;
    }

    public void setIsUnitedCard(Integer isUnitedCard) {
        this.isUnitedCard = isUnitedCard;
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

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getRechargeId() {
        return rechargeId;
    }

    public void setRechargeId(Integer rechargeId) {
        this.rechargeId = rechargeId;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public String getEnableRemark() {
        return enableRemark;
    }

    public void setEnableRemark(String enableRemark) {
        this.enableRemark = enableRemark;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CardStoredvaluecard{" +
        "id=" + id +
        ", clubId=" + clubId +
        ", vipId=" + vipId +
        ", realname=" + realname +
        ", title=" + title +
        ", cardTypeId=" + cardTypeId +
        ", cardTypeName=" + cardTypeName +
        ", actualInsertTime=" + actualInsertTime +
        ", openCardTime=" + openCardTime +
        ", startUseTime=" + startUseTime +
        ", expireTime=" + expireTime +
        ", insertTime=" + insertTime +
        ", sumMoney=" + sumMoney +
        ", leftMoney=" + leftMoney +
        ", actualMoney=" + actualMoney +
        ", retainage=" + retainage +
        ", autoOpenCardStatus=" + autoOpenCardStatus +
        ", balance=" + balance +
        ", banksure=" + banksure +
        ", contractNumber=" + contractNumber +
        ", frontMoneyStatus=" + frontMoneyStatus +
        ", isRenewCard=" + isRenewCard +
        ", isUnitedCard=" + isUnitedCard +
        ", rcepteionId=" + rcepteionId +
        ", rcepteionName=" + rcepteionName +
        ", membershipId=" + membershipId +
        ", membershipName=" + membershipName +
        ", opeUserId=" + opeUserId +
        ", opeUsername=" + opeUsername +
        ", payMethod=" + payMethod +
        ", phone=" + phone +
        ", sourceId=" + sourceId +
        ", sourceName=" + sourceName +
        ", remark=" + remark +
        ", port=" + port +
        ", rechargeId=" + rechargeId +
        ", enabled=" + enabled +
        ", enableRemark=" + enableRemark +
        "}";
    }
}
