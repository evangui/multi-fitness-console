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
 * VIP会员表
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
@TableName("biz_vip_user")
public class VipUser extends Model<VipUser> {

    private static final long serialVersionUID = 1L;

    /**
     * 会员id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
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
     * 昵称
     */
    private String nickname;
    /**
     * 会员头像
     */
    private String realavatar;
    /**
     * 支付密码
     */
    @TableField("pay_passwd")
    private String payPasswd;
    /**
     * 会员性别（0：女，1：男）
     */
    private Integer gender;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 生日
     */
    private Integer birthday;
    /**
     * 身份证号
     */
    @TableField("id_card_number")
    private String idCardNumber;
    /**
     * 会员卡ID
     */
    @TableField("card_id")
    private Integer cardId;
    /**
     * 会员卡号
     */
    @TableField("card_number")
    private String cardNumber;
    /**
     * 会员卡类型文字，如：月卡，年卡等自定义
     */
    @TableField("card_type")
    private String cardType;
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
     * 介绍会员数
     */
    @TableField("introduce_person_count")
    private Integer introducePersonCount;
    /**
     * 介绍人ID
     */
    @TableField("introduce_person_id")
    private Integer introducePersonId;
    /**
     * 介绍人名称
     */
    @TableField("introduce_person_name")
    private String introducePersonName;
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
     * 国家号码编号
     */
    private String country;
    /**
     * 家庭住址
     */
    private String address;
    /**
     * 备注
     */
    private String remark;
    /**
     * 积分
     */
    private Integer points;
    /**
     * 已用积分
     */
    @TableField("used_points")
    private Integer usedPoints;
    /**
     * 是否是导入
     */
    @TableField("is_import")
    private Integer isImport;
    /**
     * 是否通用vip
     */
    @TableField("is_universe_vip")
    private Integer isUniverseVip;
    /**
     * 最近跟进记录
     */
    @TableField("last_maintain_record")
    private String lastMaintainRecord;
    /**
     * 上次维护时间
     */
    @TableField("last_maintain_time")
    private String lastMaintainTime;
    /**
     * 上次签到时间
     */
    @TableField("last_sign_time")
    private String lastSignTime;
    /**
     * 入会时间
     */
    @TableField("admission_time")
    private String admissionTime;
    /**
     * 入会备注
     */
    @TableField("admission_remark")
    private String admissionRemark;
    /**
     * 添加时间
     */
    @TableField("insert_time")
    private String insertTime;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private String updateTime;
    /**
     * 客户来源
     */
    @TableField("source_id")
    private Integer sourceId;
    /**
     * 私教卡已用次数
     */
    @TableField("personal_trainer_card_used_counts")
    private Integer personalTrainerCardUsedCounts;
    /**
     * 私教卡总次数
     */
    @TableField("personal_trainer_card_counts")
    private Integer personalTrainerCardCounts;
    /**
     * 私教卡数量
     */
    @TableField("personal_trainer_card_num")
    private Integer personalTrainerCardNum;
    /**
     * 次卡已用次数
     */
    @TableField("once_card_used_counts")
    private Integer onceCardUsedCounts;
    /**
     * 次卡总次数
     */
    @TableField("once_card_counts")
    private Integer onceCardCounts;
    /**
     * 次卡数量
     */
    @TableField("once_card_num")
    private Integer onceCardNum;
    /**
     * 储值卡已用次数
     */
    @TableField("stored_value_card_used_counts")
    private Integer storedValueCardUsedCounts;
    /**
     * 储值卡总次数
     */
    @TableField("stored_value_card_counts")
    private Integer storedValueCardCounts;
    /**
     * 储值卡数量
     */
    @TableField("stored_value_card_num")
    private Integer storedValueCardNum;
    /**
     * 时间卡过期时间
     */
    @TableField("expire_time")
    private String expireTime;
    /**
     * 时间卡状态（0无卡 1未开卡 2正常 3已过期 ）
     */
    @TableField("expire_status")
    private Integer expireStatus;
    /**
     * 维护跟进的关注度：0未设置 1普通 2高关注 3不维护
     */
    @TableField("label_levels")
    private Integer labelLevels;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealavatar() {
        return realavatar;
    }

    public void setRealavatar(String realavatar) {
        this.realavatar = realavatar;
    }

    public String getPayPasswd() {
        return payPasswd;
    }

    public void setPayPasswd(String payPasswd) {
        this.payPasswd = payPasswd;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getBirthday() {
        return birthday;
    }

    public void setBirthday(Integer birthday) {
        this.birthday = birthday;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
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

    public Integer getIntroducePersonCount() {
        return introducePersonCount;
    }

    public void setIntroducePersonCount(Integer introducePersonCount) {
        this.introducePersonCount = introducePersonCount;
    }

    public Integer getIntroducePersonId() {
        return introducePersonId;
    }

    public void setIntroducePersonId(Integer introducePersonId) {
        this.introducePersonId = introducePersonId;
    }

    public String getIntroducePersonName() {
        return introducePersonName;
    }

    public void setIntroducePersonName(String introducePersonName) {
        this.introducePersonName = introducePersonName;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getUsedPoints() {
        return usedPoints;
    }

    public void setUsedPoints(Integer usedPoints) {
        this.usedPoints = usedPoints;
    }

    public Integer getIsImport() {
        return isImport;
    }

    public void setIsImport(Integer isImport) {
        this.isImport = isImport;
    }

    public Integer getIsUniverseVip() {
        return isUniverseVip;
    }

    public void setIsUniverseVip(Integer isUniverseVip) {
        this.isUniverseVip = isUniverseVip;
    }

    public String getLastMaintainRecord() {
        return lastMaintainRecord;
    }

    public void setLastMaintainRecord(String lastMaintainRecord) {
        this.lastMaintainRecord = lastMaintainRecord;
    }

    public String getLastMaintainTime() {
        return lastMaintainTime;
    }

    public void setLastMaintainTime(String lastMaintainTime) {
        this.lastMaintainTime = lastMaintainTime;
    }

    public String getLastSignTime() {
        return lastSignTime;
    }

    public void setLastSignTime(String lastSignTime) {
        this.lastSignTime = lastSignTime;
    }

    public String getAdmissionTime() {
        return admissionTime;
    }

    public void setAdmissionTime(String admissionTime) {
        this.admissionTime = admissionTime;
    }

    public String getAdmissionRemark() {
        return admissionRemark;
    }

    public void setAdmissionRemark(String admissionRemark) {
        this.admissionRemark = admissionRemark;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getPersonalTrainerCardUsedCounts() {
        return personalTrainerCardUsedCounts;
    }

    public void setPersonalTrainerCardUsedCounts(Integer personalTrainerCardUsedCounts) {
        this.personalTrainerCardUsedCounts = personalTrainerCardUsedCounts;
    }

    public Integer getPersonalTrainerCardCounts() {
        return personalTrainerCardCounts;
    }

    public void setPersonalTrainerCardCounts(Integer personalTrainerCardCounts) {
        this.personalTrainerCardCounts = personalTrainerCardCounts;
    }

    public Integer getPersonalTrainerCardNum() {
        return personalTrainerCardNum;
    }

    public void setPersonalTrainerCardNum(Integer personalTrainerCardNum) {
        this.personalTrainerCardNum = personalTrainerCardNum;
    }

    public Integer getOnceCardUsedCounts() {
        return onceCardUsedCounts;
    }

    public void setOnceCardUsedCounts(Integer onceCardUsedCounts) {
        this.onceCardUsedCounts = onceCardUsedCounts;
    }

    public Integer getOnceCardCounts() {
        return onceCardCounts;
    }

    public void setOnceCardCounts(Integer onceCardCounts) {
        this.onceCardCounts = onceCardCounts;
    }

    public Integer getOnceCardNum() {
        return onceCardNum;
    }

    public void setOnceCardNum(Integer onceCardNum) {
        this.onceCardNum = onceCardNum;
    }

    public Integer getStoredValueCardUsedCounts() {
        return storedValueCardUsedCounts;
    }

    public void setStoredValueCardUsedCounts(Integer storedValueCardUsedCounts) {
        this.storedValueCardUsedCounts = storedValueCardUsedCounts;
    }

    public Integer getStoredValueCardCounts() {
        return storedValueCardCounts;
    }

    public void setStoredValueCardCounts(Integer storedValueCardCounts) {
        this.storedValueCardCounts = storedValueCardCounts;
    }

    public Integer getStoredValueCardNum() {
        return storedValueCardNum;
    }

    public void setStoredValueCardNum(Integer storedValueCardNum) {
        this.storedValueCardNum = storedValueCardNum;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public Integer getExpireStatus() {
        return expireStatus;
    }

    public void setExpireStatus(Integer expireStatus) {
        this.expireStatus = expireStatus;
    }

    public Integer getLabelLevels() {
        return labelLevels;
    }

    public void setLabelLevels(Integer labelLevels) {
        this.labelLevels = labelLevels;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "VipUser{" +
        "id=" + id +
        ", userId=" + userId +
        ", realname=" + realname +
        ", nickname=" + nickname +
        ", realavatar=" + realavatar +
        ", payPasswd=" + payPasswd +
        ", gender=" + gender +
        ", phone=" + phone +
        ", birthday=" + birthday +
        ", idCardNumber=" + idCardNumber +
        ", cardId=" + cardId +
        ", cardNumber=" + cardNumber +
        ", cardType=" + cardType +
        ", coachId=" + coachId +
        ", coachName=" + coachName +
        ", introducePersonCount=" + introducePersonCount +
        ", introducePersonId=" + introducePersonId +
        ", introducePersonName=" + introducePersonName +
        ", membershipId=" + membershipId +
        ", membershipName=" + membershipName +
        ", country=" + country +
        ", address=" + address +
        ", remark=" + remark +
        ", points=" + points +
        ", usedPoints=" + usedPoints +
        ", isImport=" + isImport +
        ", isUniverseVip=" + isUniverseVip +
        ", lastMaintainRecord=" + lastMaintainRecord +
        ", lastMaintainTime=" + lastMaintainTime +
        ", lastSignTime=" + lastSignTime +
        ", admissionTime=" + admissionTime +
        ", admissionRemark=" + admissionRemark +
        ", insertTime=" + insertTime +
        ", updateTime=" + updateTime +
        ", sourceId=" + sourceId +
        ", personalTrainerCardUsedCounts=" + personalTrainerCardUsedCounts +
        ", personalTrainerCardCounts=" + personalTrainerCardCounts +
        ", personalTrainerCardNum=" + personalTrainerCardNum +
        ", onceCardUsedCounts=" + onceCardUsedCounts +
        ", onceCardCounts=" + onceCardCounts +
        ", onceCardNum=" + onceCardNum +
        ", storedValueCardUsedCounts=" + storedValueCardUsedCounts +
        ", storedValueCardCounts=" + storedValueCardCounts +
        ", storedValueCardNum=" + storedValueCardNum +
        ", expireTime=" + expireTime +
        ", expireStatus=" + expireStatus +
        ", labelLevels=" + labelLevels +
        "}";
    }
}
