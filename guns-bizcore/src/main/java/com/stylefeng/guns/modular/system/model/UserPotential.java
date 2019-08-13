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
 * 潜在客户表
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
@TableName("biz_user_potential")
public class UserPotential extends Model<UserPotential> {

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
     * 会员性别（0：女，1：男）
     */
    private Integer gender;
    /**
     * 手机号
     */
    private String phone;
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
     * 备注
     */
    private String remark;
    /**
     * 是否是导入
     */
    @TableField("is_import")
    private Integer isImport;
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
     * 添加时间
     */
    @TableField("insert_time")
    private String insertTime;
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
     * 维护跟进的关注度：0未设置 1普通 2高关注 3不维护
     */
    @TableField("label_levels")
    private Integer labelLevels;
    /**
     * 是否已转化为vip
     */
    @TableField("is_vip_user")
    private Integer isVipUser;


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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsImport() {
        return isImport;
    }

    public void setIsImport(Integer isImport) {
        this.isImport = isImport;
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

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
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

    public Integer getLabelLevels() {
        return labelLevels;
    }

    public void setLabelLevels(Integer labelLevels) {
        this.labelLevels = labelLevels;
    }

    public Integer getIsVipUser() {
        return isVipUser;
    }

    public void setIsVipUser(Integer isVipUser) {
        this.isVipUser = isVipUser;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UserPotential{" +
        "id=" + id +
        ", userId=" + userId +
        ", realname=" + realname +
        ", nickname=" + nickname +
        ", realavatar=" + realavatar +
        ", gender=" + gender +
        ", phone=" + phone +
        ", coachId=" + coachId +
        ", coachName=" + coachName +
        ", membershipId=" + membershipId +
        ", membershipName=" + membershipName +
        ", remark=" + remark +
        ", isImport=" + isImport +
        ", lastMaintainRecord=" + lastMaintainRecord +
        ", lastMaintainTime=" + lastMaintainTime +
        ", insertTime=" + insertTime +
        ", sourceId=" + sourceId +
        ", sourceName=" + sourceName +
        ", labelLevels=" + labelLevels +
        ", isVipUser=" + isVipUser +
        "}";
    }
}
